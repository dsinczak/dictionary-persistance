package org.dsinczak.transaction.domain;

import com.google.common.base.Preconditions;
import org.dsinczak.publishedlanguage.Currency;
import org.dsinczak.transaction.domain.vo.Money;
import org.dsinczak.transaction.domain.vo.MoneyType;
import org.dsinczak.transaction.domain.vo.TransactionType;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@TypeDefs(@TypeDef(name = "moneyTypeClass",typeClass = MoneyType.class))
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_transaction_participant_id_credit")
    private TransactionParticipant credit;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_transaction_participant_id_debit")
    private TransactionParticipant debit;

    @Type(type = "moneyTypeClass")
    @Columns(columns = {
            @Column(name = "amount"),
            @Column(name = "fk_currency_id")
    })
    private Money money;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "fk_transaction_type_id")
    private TransactionType transactionType;

    public Transaction() {
    }

    public Transaction(TransactionParticipant credit, TransactionParticipant debit,
                       BigDecimal amount, Currency currency, TransactionType transactionType) {
        this(credit,debit,new Money(amount,currency),transactionType);
    }

    public Transaction(TransactionParticipant credit, TransactionParticipant debit, Money money, TransactionType transactionType) {
        Preconditions.checkNotNull(credit, "Credit participant cannot be null");
        Preconditions.checkNotNull(debit, "Debit participant cannot be null");
        this.credit = credit;
        this.debit = debit;
        this.money = money;
        this.transactionType = transactionType;
    }

    public Long getId() {
        return id;
    }

    public TransactionParticipant getCredit() {
        return credit;
    }

    public TransactionParticipant getDebit() {
        return debit;
    }

    public BigDecimal getAmount() {
        return money.getAmount();
    }

    public Currency getCurrency() {
        return money.getCurrency();
    }

    public Money getMoney() {
        return money;
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
                ", money=" + money +
                ", transactionType=" + transactionType +
                '}';
    }
}
