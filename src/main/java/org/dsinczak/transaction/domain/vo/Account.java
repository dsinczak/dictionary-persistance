package org.dsinczak.transaction.domain.vo;

import javax.persistence.Embeddable;

@Embeddable
public class Account {
    private String iban;
    private String bic;

    public Account() {
    }

    public Account(String iban, String bic) {
        this.iban = iban;
        this.bic = bic;
    }

    public String getIban() {
        return iban;
    }

    public String getBic() {
        return bic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (iban != null ? !iban.equals(account.iban) : account.iban != null) return false;
        return bic != null ? bic.equals(account.bic) : account.bic == null;

    }

    @Override
    public int hashCode() {
        int result = iban != null ? iban.hashCode() : 0;
        result = 31 * result + (bic != null ? bic.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "iban='" + iban + '\'' +
                ", bic='" + bic + '\'' +
                '}';
    }
}
