package org.dsinczak.transaction.domain.vo;


import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.dsinczak.publishedlanguage.City;


public class Address {

    private String addressLine1;
    private String addressLine2;
    private PostalCode postalCode;
    private City city;

    public Address(String addressLine1, String addressLine2, PostalCode postalCode, City city) {
        Preconditions.checkArgument(StringUtils.isNoneEmpty(addressLine1) || StringUtils.isNoneEmpty(addressLine2), "Address must contain at lest one line of place name and street");
        Preconditions.checkNotNull(postalCode, "Cannot create address without postal code");
        Preconditions.checkNotNull(city, "Cannot create address without city");

        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public City getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (getAddressLine1() != null ? !getAddressLine1().equals(address.getAddressLine1()) : address.getAddressLine1() != null)
            return false;
        if (getAddressLine2() != null ? !getAddressLine2().equals(address.getAddressLine2()) : address.getAddressLine2() != null)
            return false;
        if (getPostalCode() != null ? !getPostalCode().equals(address.getPostalCode()) : address.getPostalCode() != null)
            return false;
        return getCity() != null ? getCity().equals(address.getCity()) : address.getCity() == null;

    }

    @Override
    public int hashCode() {
        int result = getAddressLine1() != null ? getAddressLine1().hashCode() : 0;
        result = 31 * result + (getAddressLine2() != null ? getAddressLine2().hashCode() : 0);
        result = 31 * result + (getPostalCode() != null ? getPostalCode().hashCode() : 0);
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", postalCode=" + postalCode +
                ", city=" + city +
                '}';
    }
}
