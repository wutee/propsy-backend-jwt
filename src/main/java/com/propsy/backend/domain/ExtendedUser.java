package com.propsy.backend.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ExtendedUser.
 */
@Entity
@Table(name = "extended_user")
public class ExtendedUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 250)
    @Column(name = "name", length = 250)
    private String name;

    @Size(max = 250)
    @Column(name = "address", length = 250)
    private String address;

    @Size(max = 250)
    @Column(name = "city", length = 250)
    private String city;

    @Size(max = 250)
    @Column(name = "phone", length = 250)
    private String phone;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @OneToOne    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ExtendedUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public ExtendedUser address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public ExtendedUser city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public ExtendedUser phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public ExtendedUser loyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
        return this;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public User getUser() {
        return user;
    }

    public ExtendedUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExtendedUser extendedUser = (ExtendedUser) o;
        if (extendedUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), extendedUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExtendedUser{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", phone='" + getPhone() + "'" +
            ", loyaltyPoints=" + getLoyaltyPoints() +
            "}";
    }
}
