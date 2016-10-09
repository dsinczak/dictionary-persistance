package org.dsinczak.transaction.domain;

import com.google.common.base.Preconditions;
import org.dsinczak.transaction.domain.vo.Address;
import org.dsinczak.transaction.domain.vo.AddressType;
import org.dsinczak.transaction.domain.vo.ValueDate;
import org.dsinczak.transaction.domain.vo.Account;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@TypeDefs(@TypeDef(name = "addressType", typeClass = AddressType.class))
public class TransactionParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Account account;

    private ValueDate valueDate;

    @Columns(columns = {
            @Column(name = "address_line_1"),
            @Column(name = "address_line_2"),
            @Column(name = "address_postal_code"),
            @Column(name = "fk_city_id")
    })
    @Type(type = "addressType")
    private Address address;

    public TransactionParticipant() {
    }

    public TransactionParticipant(Account account, ValueDate valueDate) {
        this(account, valueDate, null);
    }

    public TransactionParticipant(Account account, ValueDate valueDate, Address address) {
        Preconditions.checkNotNull(account, "Cannot create participant without account information");
        Preconditions.checkNotNull(account, "Cannot create participant without value date information");
        this.account = account;
        this.valueDate = valueDate;
        this.address = address;
    }

    public Account getAccount() {
        return account;
    }

    public ValueDate getValueDate() {
        return valueDate;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionParticipant that = (TransactionParticipant) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "TransactionParticipant{" +
                "id=" + id +
                ", account=" + account +
                ", valueDate=" + valueDate +
                ", address=" + address +
                '}';
    }
}
