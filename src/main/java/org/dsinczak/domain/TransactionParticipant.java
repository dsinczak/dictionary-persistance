package org.dsinczak.domain;

import org.dsinczak.domain.vo.Account;
import org.dsinczak.domain.vo.ValueDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TransactionParticipant {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private Account account;

    private ValueDate valueDate;

    public TransactionParticipant() {
    }

    public TransactionParticipant(Account account, ValueDate valueDate) {
        this.account = account;
        this.valueDate = valueDate;
    }

    public Account getAccount() {
        return account;
    }

    public ValueDate getValueDate() {
        return valueDate;
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
                '}';
    }
}
