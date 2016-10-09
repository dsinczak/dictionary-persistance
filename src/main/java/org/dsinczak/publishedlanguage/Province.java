package org.dsinczak.publishedlanguage;

import com.google.common.base.Preconditions;
import org.dsinczak.publishedlanguage.dict.Dictionary;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Province extends Dictionary {

    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_country_id", updatable = false)
    private Country country;

    public Province() {
    }

    public Province(Country country, String code, String name) {
        super(code);
        Preconditions.checkNotNull(country,"Province cannot exist without country");
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;

        Province currency = (Province) o;

        return getCode() != null ? getCode().equals(currency.getCode()) : currency.getCode() == null;

    }

    @Override
    public int hashCode() {
        return getCode() != null ? getCode().hashCode() : 0;
    }


    @Override
    public String toString() {
        return "Province{" +
                "code='" + getCode() + '\'' +
                "name='" + name + '\'' +
                "country='" + country + '\'' +
                '}';
    }
}
