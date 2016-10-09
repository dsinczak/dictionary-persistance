package org.dsinczak.transaction.domain.vo;

import org.dsinczak.publishedlanguage.Currency;
import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.NamedQueryDefinition;
import org.hibernate.engine.spi.NamedQueryDefinitionBuilder;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoneyType implements CompositeUserType {

    private static final NamedQueryDefinition FIND_CURRENCY_BY_ID = new NamedQueryDefinitionBuilder()//
            .setName("MoneyType.Currency.findById")//
            .setQuery("SELECT c FROM Currency c WHERE c.id=:id")//
            .setCacheable(true)//
            .setCacheMode(CacheMode.NORMAL)//
            .createNamedQueryDefinition();

    private static final NamedQueryDefinition FIND_CURRENCY_ID_BY_CODE = new NamedQueryDefinitionBuilder()//
            .setName("MoneyType.Currency.findByCode")//
            .setQuery("SELECT c.id FROM Currency c WHERE c.code=:code")//
            .setCacheable(true)//
            .setCacheMode(CacheMode.NORMAL)//
            .createNamedQueryDefinition();

    @Override
    public Class returnedClass() {
        return Money.class;
    }

    @Override
    public String[] getPropertyNames() {
        return new String[]{"amount", "fk_currency_id"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{BigDecimalType.INSTANCE, LongType.INSTANCE};
    }

    @Override
    public Object getPropertyValue(Object component, int propertyIndex) throws HibernateException {
        if (component == null) {
            return null;
        }

        final Money money = (Money) component;
        switch (propertyIndex) {
            case 0: {
                return money.getAmount();
            }
            case 1: {
                return money.getCurrency();
            }
            default: {
                throw new HibernateException("Invalid property index ["
                        + propertyIndex + "] for dictionary " + component.getClass().getCanonicalName());
            }
        }
    }

    @Override
    public void setPropertyValue(Object component, int i, Object value) throws HibernateException {
        if (component == null) {
            return;
        }
        throw new HibernateException("Dictionary " + component.getClass().getCanonicalName() + " is immutable.");
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return ObjectUtils.nullSafeEquals(o, o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return ObjectUtils.nullSafeHashCode(o);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object owner)
            throws HibernateException, SQLException {

        BigDecimal amount = BigDecimalType.INSTANCE.nullSafeGet(resultSet, names[0], sessionImplementor);
        Long currencyId = LongType.INSTANCE.nullSafeGet(resultSet, names[1], sessionImplementor);

        if (null == currencyId) {
            return null;
        }

        Object currencyObj = sessionImplementor.createQuery(FIND_CURRENCY_BY_ID)//
                .setLong("id", currencyId)//
                .uniqueResult();

        if (null == currencyId) {
            return null;
        }

        if (!(currencyObj instanceof Currency)) {
            throw new HibernateException("Retrieved object is not instance of " + Currency.class.getCanonicalName());
        }

        return new Money(amount, (Currency) currencyObj);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index,
                            SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if (null == value) {
            BigDecimalType.INSTANCE.set(preparedStatement, null, index, sessionImplementor);
            LongType.INSTANCE.set(preparedStatement, null, index + 1, sessionImplementor);
            return;
        }

        Money money = (Money) value;

        Long currencyId = money.getCurrency().getId();
        if (null == currencyId) {
            throw new HibernateException("Cannot persist " + Money.class.getCanonicalName()
                    + " with connection on not persisted dictionary value "
                    + money.getCurrency() + " persist dictionary value first.");
        }

        BigDecimalType.INSTANCE.set(preparedStatement, money.getAmount(), index, sessionImplementor);
        LongType.INSTANCE.set(preparedStatement, currencyId, index + 1, sessionImplementor);

    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if (null == o) {
            return null;
        }
        Money money = (Money) o;
        return new Money(money.getAmount(), money.getCurrency());
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o, SessionImplementor sessionImplementor)
            throws HibernateException {
        return (Serializable) deepCopy(o);
    }

    @Override
    public Object assemble(Serializable serializable, SessionImplementor sessionImplementor, Object o)
            throws HibernateException {
        return serializable;
    }

    @Override
    public Object replace(Object original, Object target, SessionImplementor sessionImplementor, Object owner)
            throws HibernateException {
        return deepCopy(original);
    }
}
