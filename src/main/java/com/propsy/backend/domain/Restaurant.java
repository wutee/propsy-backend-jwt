package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 250)
    @Column(name = "name_slug", length = 250, nullable = false)
    private String nameSlug;

    @Size(max = 250)
    @Column(name = "address", length = 250)
    private String address;

    @Size(max = 250)
    @Column(name = "city", length = 250)
    private String city;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Lob
    @Column(name = "photo_blob")
    private byte[] photoBlob;

    @Column(name = "photo_blob_content_type")
    private String photoBlobContentType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User worker;

    @OneToMany(mappedBy = "restaurant")
    private Set<FoodOrder> orders = new HashSet<>();
    @OneToMany(mappedBy = "restaurant")
    private Set<Menu> menus = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSlug() {
        return nameSlug;
    }

    public Restaurant nameSlug(String nameSlug) {
        this.nameSlug = nameSlug;
        return this;
    }

    public void setNameSlug(String nameSlug) {
        this.nameSlug = nameSlug;
    }

    public String getAddress() {
        return address;
    }

    public Restaurant address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Restaurant city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Restaurant latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Restaurant longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public byte[] getPhotoBlob() {
        return photoBlob;
    }

    public Restaurant photoBlob(byte[] photoBlob) {
        this.photoBlob = photoBlob;
        return this;
    }

    public void setPhotoBlob(byte[] photoBlob) {
        this.photoBlob = photoBlob;
    }

    public String getPhotoBlobContentType() {
        return photoBlobContentType;
    }

    public Restaurant photoBlobContentType(String photoBlobContentType) {
        this.photoBlobContentType = photoBlobContentType;
        return this;
    }

    public void setPhotoBlobContentType(String photoBlobContentType) {
        this.photoBlobContentType = photoBlobContentType;
    }

    public User getWorker() {
        return worker;
    }

    public Restaurant worker(User user) {
        this.worker = user;
        return this;
    }

    public void setWorker(User user) {
        this.worker = user;
    }

    public Set<FoodOrder> getOrders() {
        return orders;
    }

    public Restaurant orders(Set<FoodOrder> foodOrders) {
        this.orders = foodOrders;
        return this;
    }

    public Restaurant addOrder(FoodOrder foodOrder) {
        this.orders.add(foodOrder);
        foodOrder.setRestaurant(this);
        return this;
    }

    public Restaurant removeOrder(FoodOrder foodOrder) {
        this.orders.remove(foodOrder);
        foodOrder.setRestaurant(null);
        return this;
    }

    public void setOrders(Set<FoodOrder> foodOrders) {
        this.orders = foodOrders;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public Restaurant menus(Set<Menu> menus) {
        this.menus = menus;
        return this;
    }

    public Restaurant addMenu(Menu menu) {
        this.menus.add(menu);
        menu.setRestaurant(this);
        return this;
    }

    public Restaurant removeMenu(Menu menu) {
        this.menus.remove(menu);
        menu.setRestaurant(null);
        return this;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
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
        Restaurant restaurant = (Restaurant) o;
        if (restaurant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restaurant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + getId() +
            ", nameSlug='" + getNameSlug() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", photoBlob='" + getPhotoBlob() + "'" +
            ", photoBlobContentType='" + getPhotoBlobContentType() + "'" +
            "}";
    }
}
