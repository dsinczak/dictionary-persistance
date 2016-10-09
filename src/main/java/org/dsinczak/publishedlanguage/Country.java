package org.dsinczak.publishedlanguage;

import org.dsinczak.publishedlanguage.dict.Dictionary;

import javax.persistence.Entity;

@Entity
public class Country extends Dictionary {

    private String name;

    public Country() {
    }

    public Country(String code, String name) {
        super(code);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;

        Country currency = (Country) o;

        return getCode() != null ? getCode().equals(currency.getCode()) : currency.getCode() == null;

    }

    @Override
    public int hashCode() {
        return getCode() != null ? getCode().hashCode() : 0;
    }


    @Override
    public String toString() {
        return "Country{" +
                "code='" + getCode() + '\'' +
                "name='" + name + '\'' +
                '}';
    }
}
