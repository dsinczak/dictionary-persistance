package org.dsinczak.transaction.domain.vo;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class ValueDate {

    private Date date;

    public ValueDate() {
        date = new Date();
    }

    public ValueDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueDate valueDate = (ValueDate) o;

        return date != null ? date.equals(valueDate.date) : valueDate.date == null;

    }

    @Override
    public int hashCode() {
        return date != null ? date.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ValueDate{" +
                "date=" + date +
                '}';
    }
}
