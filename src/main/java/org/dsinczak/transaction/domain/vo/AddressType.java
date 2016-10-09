package org.dsinczak.transaction.domain.vo;

import org.dsinczak.publishedlanguage.City;
import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.NamedQueryDefinition;
import org.hibernate.engine.spi.NamedQueryDefinitionBuilder;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressType implements CompositeUserType {

    private static final NamedQueryDefinition FIND_CITY_BY_ID = new NamedQueryDefinitionBuilder()//
            .setName("MoneyType.City.findById")//
            .setQuery("SELECT c FROM City c WHERE c.id=:id")//
            .setCacheable(true)//
            .setCacheMode(CacheMode.NORMAL)//
            .createNamedQueryDefinition();

    @Override
    public Class returnedClass() {
        return Address.class;
    }

    @Override
    public String[] getPropertyNames() {
        return new String[]{"address_line_1", "address_line_2", "postal_code", "fk_city_id"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{StringType.INSTANCE, StringType.INSTANCE, StringType.INSTANCE, LongType.INSTANCE};
    }

    @Override
    public Object getPropertyValue(Object component, int propertyIndex) throws HibernateException {
        if (component == null) {
            return null;
        }

        final Address address = (Address) component;
        switch (propertyIndex) {
            case 0: {
                return address.getAddressLine1();
            }
            case 1: {
                return address.getAddressLine2();
            }
            case 2: {
                return address.getPostalCode();
            }
            case 3: {
                return address.getCity();
            }
            default: {
                throw new HibernateException("Invalid property index ["
                        + propertyIndex + "] for dictionary " + component.getClass().getCanonicalName());
            }
        }
    }

    @Override
    public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
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
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        String addressLine1 = StringType.INSTANCE.nullSafeGet(rs, names[0], session);
        String addressLine2 = StringType.INSTANCE.nullSafeGet(rs, names[1], session);
        String postalCode = StringType.INSTANCE.nullSafeGet(rs, names[2], session);
        Long cityId = LongType.INSTANCE.nullSafeGet(rs, names[3], session);

        if(null == cityId) {
            return null;
        }

        Object cityObj = session.createQuery(FIND_CITY_BY_ID)//
                .setLong("id", cityId)//
                .uniqueResult();

        if (null == cityObj) {
            return null;
        }

        if (!(cityObj instanceof City)) {
            throw new HibernateException("Retrieved object is not instance of " + City.class.getCanonicalName());
        }
        return new Address(addressLine1, addressLine2, new PostalCode(postalCode), (City) cityObj);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (null == value) {
            StringType.INSTANCE.set(st, null, index, session);
            StringType.INSTANCE.set(st, null, index + 1, session);
            StringType.INSTANCE.set(st, null, index + 2, session);
            LongType.INSTANCE.set(st, null, index + 3, session);
            return;
        }

        Address address = (Address) value;
        Long cityId = address.getCity().getId();

        if (null == cityId) {
            throw new HibernateException("Cannot persist " + Address.class.getCanonicalName()
                    + " with connection on not persisted dictionary value "
                    + address.getCity() + " persist dictionary value first.");
        }

        StringType.INSTANCE.set(st, address.getAddressLine1(), index, session);
        StringType.INSTANCE.set(st, address.getAddressLine2(), index + 1, session);
        StringType.INSTANCE.set(st, address.getPostalCode().getValue(), index + 2, session);
        LongType.INSTANCE.set(st, cityId, index + 3, session);

    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (null == value) {
            return null;
        }
        Address from = (Address) value;
        return new Address(from.getAddressLine1(), from.getAddressLine2(), from.getPostalCode(), from.getCity());
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
