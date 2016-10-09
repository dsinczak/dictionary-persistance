package org.dsinczak.publishedlanguage;

import org.dsinczak.publishedlanguage.dict.Dictionary;

import javax.persistence.Entity;

@Entity
public class Currency extends Dictionary {

    private String name;

    public Currency() {
    }

    public Currency(String code, String name) {
        super(code);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id='" + getId() + '\'' +
                ", code='" + getCode() + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;

        Currency currency = (Currency) o;

        return getCode() != null ? getCode().equals(currency.getCode()) : currency.getCode() == null;

    }

    @Override
    public int hashCode() {
        return getCode() != null ? getCode().hashCode() : 0;
    }
}
