package org.dsinczak.transaction.domain.vo;

import org.dsinczak.publishedlanguage.dict.Dictionary;

import javax.persistence.Entity;

/**
 * Banking account transaction types:
 * ATM: Deposit or withdraw funds using an ATM.
 * Charge: Record a purchase on a credit card or withdraw funds using a debit card.
 * Check: Withdraw funds by writing a paper check. Choosing this type will automatically insert a number in the '#' field (the next number in sequence from the last check recorded).
 * Deposit: Add funds to an account by any method.
 * Online: Withdraw funds through a web-based store or online banking service.
 * POS: Withdraw funds through a point-of-sale transaction (typically a cash or debit card purchase).
 * Transfer: Move funds from one account to another (for more information, see Account Transfers).
 * Withdrawal: Deduct funds from an account by any method.
 */
@Entity
public class TransactionType extends Dictionary {

    private String description;

    public TransactionType() {
    }

    public TransactionType(String code, String description) {
        super(code);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionType)) return false;

        TransactionType that = (TransactionType) o;

        return getCode() != null ? getCode().equals(that.getCode()) : that.getCode() == null;

    }

    @Override
    public int hashCode() {
        return getCode() != null ? getCode().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TransactionType{" +
                "code='" + getCode() + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
