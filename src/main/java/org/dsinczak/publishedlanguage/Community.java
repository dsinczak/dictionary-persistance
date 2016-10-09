package org.dsinczak.publishedlanguage;

import com.google.common.base.Preconditions;
import org.dsinczak.publishedlanguage.dict.Dictionary;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Community extends Dictionary {

    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_province_id", updatable = false)
    private Province province;

    public Community() {
    }

    public Community(Province province, String code, String name) {
        super(code);
        Preconditions.checkNotNull(province, "Community cannot exist without province");
        this.name = name;
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public Province getProvince() {
        return province;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;

        Community currency = (Community) o;

        return getCode() != null ? getCode().equals(currency.getCode()) : currency.getCode() == null;

    }

    @Override
    public int hashCode() {
        return getCode() != null ? getCode().hashCode() : 0;
    }


    @Override
    public String toString() {
        return "Community{" +
                "code='" + getCode() + '\'' +
                "name='" + name + '\'' +
                "province='" + province + '\'' +
                '}';
    }
}
