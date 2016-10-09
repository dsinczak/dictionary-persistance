package org.dsinczak.transaction.domain.vo;

import com.google.common.base.Preconditions;
import org.dsinczak.publishedlanguage.Currency;

import java.math.BigDecimal;


public class Money {

    private BigDecimal amount;
    private Currency currency;

    public Money() {
    }

    public Money(BigDecimal amount, Currency currency) {
        Preconditions.checkNotNull(amount, "No money without amount");
        Preconditions.checkNotNull(currency,"No money without currency");
        this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money that = (Money) o;

        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        return currency != null ? currency.equals(that.currency) : that.currency == null;

    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}
