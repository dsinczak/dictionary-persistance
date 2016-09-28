package org.dsinczak.domain.dict;

import javax.persistence.*;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Dictionary {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String code;

    public Dictionary() {
    }

    public Dictionary(String code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dictionary that = (Dictionary) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}
