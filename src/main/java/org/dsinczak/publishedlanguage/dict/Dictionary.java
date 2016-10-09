package org.dsinczak.publishedlanguage.dict;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;


import javax.persistence.*;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(updatable = false, unique = true)
    private String code;

    public Dictionary() {
    }

    public Dictionary(String code) {
        Preconditions.checkArgument(StringUtils.isNoneEmpty(code), "Dictionary code cannot be null or empty");
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public abstract boolean equals(Object o);

    public abstract int hashCode();

}
