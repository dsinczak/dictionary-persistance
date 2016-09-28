package org.dsinczak.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_transaction_participant_id_credit")
    private TransactionParticipant credit;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_transaction_participant_id_debit")
    private TransactionParticipant debit;

    private BigDecimal amount;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_currency_id")
    private Currency currency;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_transaction_type_id")
    private TransactionType transactionType;

    public Transaction() {
    }

    public Transaction(TransactionParticipant credit, TransactionParticipant debit, BigDecimal amount, Currency currency, TransactionType transactionType) {
        this.credit = credit;
        this.debit = debit;
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
    }

    public long getId() {
        return id;
    }

    public TransactionParticipant getCredit() {
        return credit;
    }

    public TransactionParticipant getDebit() {
        return debit;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", credit=" + credit +
                ", debit=" + debit +
                ", amount=" + amount +
                ", currency=" + currency +
                ", transactionType=" + transactionType +
                '}';
    }
}
