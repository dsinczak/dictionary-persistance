package org.dsinczak.publishedlanguage;

import com.google.common.base.Preconditions;
import org.dsinczak.publishedlanguage.dict.Dictionary;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class City extends Dictionary {

    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_community_id", updatable = false)
    private Community community;

    public City() {
    }

    public City(Community community, String code, String name) {
        super(code);
        Preconditions.checkNotNull(community,"City cannot exist without community");
        this.name = name;
        this.community = community;
    }

    public String getName() {
        return name;
    }

    public Community getCommunity() {
        return community;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;

        City currency = (City) o;

        return getCode() != null ? getCode().equals(currency.getCode()) : currency.getCode() == null;

    }

    @Override
    public int hashCode() {
        return getCode() != null ? getCode().hashCode() : 0;
    }


    @Override
    public String toString() {
        return "City{" +
                "code='" + getCode() + '\'' +
                "name='" + name + '\'' +
                "community='" + community + '\'' +
                '}';
    }
}
