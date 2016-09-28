package org.dsinczak.domain;

import org.dsinczak.domain.dict.Dictionary;

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
}
