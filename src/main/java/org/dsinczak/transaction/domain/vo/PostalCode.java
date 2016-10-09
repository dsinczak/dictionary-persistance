package org.dsinczak.transaction.domain.vo;

import com.google.common.base.Preconditions;


public class PostalCode {
    private String value;

    public PostalCode(String value) {
        Preconditions.checkArgument(value.matches("[0-9]{2}-[0-9]{3}"));
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostalCode)) return false;

        PostalCode that = (PostalCode) o;

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;

    }

    @Override
    public int hashCode() {
        return getValue() != null ? getValue().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PostalCode{" +
                "value='" + value + '\'' +
                '}';
    }
}
