package org.dsinczak.domain.vo;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Created by ceds6578 on 2016-09-28.
 */
public class AmountAndCurrency {

    private BigDecimal amount;
    private Currency currency;

    public AmountAndCurrency() {
    }

    public AmountAndCurrency(BigDecimal amount, Currency currency) {
        this.amount = amount;
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

        AmountAndCurrency that = (AmountAndCurrency) o;

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
        return "AmountAndCurrency{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}
