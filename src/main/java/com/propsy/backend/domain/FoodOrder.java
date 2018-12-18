package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.propsy.backend.domain.enumeration.OrderStatus;

/**
 * A FoodOrder.
 */
@Entity
@Table(name = "food_order")
public class FoodOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_rating")
    private Integer timeRating;

    @Column(name = "price_rating")
    private Integer priceRating;

    @Column(name = "quality_rating")
    private Integer qualityRating;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @Column(name = "address_rating")
    private Integer addressRating;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", nullable = false)
    private Float price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Size(max = 250)
    @Column(name = "purchaser_opinion", length = 250)
    private String purchaserOpinion;

    @Size(max = 250)
    @Column(name = "purchaser_comment", length = 250)
    private String purchaserComment;

    @Size(min = 2, max = 250)
    @Column(name = "city", length = 250)
    private String city;

    @Size(min = 2, max = 250)
    @Column(name = "phone", length = 250)
    private String phone;

    @Size(min = 2, max = 250)
    @Column(name = "address", length = 250)
    private String address;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Restaurant restaurant;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User deliveryman;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User purchaser;

    @ManyToMany
    @JoinTable(name = "food_order_food_items",
               joinColumns = @JoinColumn(name = "food_orders_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "food_items_id", referencedColumnName = "id"))
    private Set<Food> foodItems = new HashSet<>();

    @OneToMany(mappedBy = "foodOrder")
    private Set<FoodOrderMovement> movements = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTimeRating() {
        return timeRating;
    }

    public FoodOrder timeRating(Integer timeRating) {
        this.timeRating = timeRating;
        return this;
    }

    public void setTimeRating(Integer timeRating) {
        this.timeRating = timeRating;
    }

    public Integer getPriceRating() {
        return priceRating;
    }

    public FoodOrder priceRating(Integer priceRating) {
        this.priceRating = priceRating;
        return this;
    }

    public void setPriceRating(Integer priceRating) {
        this.priceRating = priceRating;
    }

    public Integer getQualityRating() {
        return qualityRating;
    }

    public FoodOrder qualityRating(Integer qualityRating) {
        this.qualityRating = qualityRating;
        return this;
    }

    public void setQualityRating(Integer qualityRating) {
        this.qualityRating = qualityRating;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public FoodOrder loyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
        return this;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Integer getAddressRating() {
        return addressRating;
    }

    public FoodOrder addressRating(Integer addressRating) {
        this.addressRating = addressRating;
        return this;
    }

    public void setAddressRating(Integer addressRating) {
        this.addressRating = addressRating;
    }

    public LocalDate getDate() {
        return date;
    }

    public FoodOrder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getPrice() {
        return price;
    }

    public FoodOrder price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public FoodOrder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getPurchaserOpinion() {
        return purchaserOpinion;
    }

    public FoodOrder purchaserOpinion(String purchaserOpinion) {
        this.purchaserOpinion = purchaserOpinion;
        return this;
    }

    public void setPurchaserOpinion(String purchaserOpinion) {
        this.purchaserOpinion = purchaserOpinion;
    }

    public String getPurchaserComment() {
        return purchaserComment;
    }

    public FoodOrder purchaserComment(String purchaserComment) {
        this.purchaserComment = purchaserComment;
        return this;
    }

    public void setPurchaserComment(String purchaserComment) {
        this.purchaserComment = purchaserComment;
    }

    public String getCity() {
        return city;
    }

    public FoodOrder city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public FoodOrder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public FoodOrder address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public FoodOrder restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getDeliveryman() {
        return deliveryman;
    }

    public FoodOrder deliveryman(User user) {
        this.deliveryman = user;
        return this;
    }

    public void setDeliveryman(User user) {
        this.deliveryman = user;
    }

    public User getPurchaser() {
        return purchaser;
    }

    public FoodOrder purchaser(User user) {
        this.purchaser = user;
        return this;
    }

    public void setPurchaser(User user) {
        this.purchaser = user;
    }

    public Set<Food> getFoodItems() {
        return foodItems;
    }

    public FoodOrder foodItems(Set<Food> foods) {
        this.foodItems = foods;
        return this;
    }

    public FoodOrder addFoodItems(Food food) {
        this.foodItems.add(food);
        food.getOrders().add(this);
        return this;
    }

    public FoodOrder removeFoodItems(Food food) {
        this.foodItems.remove(food);
        food.getOrders().remove(this);
        return this;
    }

    public void setFoodItems(Set<Food> foods) {
        this.foodItems = foods;
    }

    public Set<FoodOrderMovement> getMovements() {
        return movements;
    }

    public FoodOrder movements(Set<FoodOrderMovement> foodOrderMovements) {
        this.movements = foodOrderMovements;
        return this;
    }

    public FoodOrder addMovement(FoodOrderMovement foodOrderMovement) {
        this.movements.add(foodOrderMovement);
        foodOrderMovement.setFoodOrder(this);
        return this;
    }

    public FoodOrder removeMovement(FoodOrderMovement foodOrderMovement) {
        this.movements.remove(foodOrderMovement);
        foodOrderMovement.setFoodOrder(null);
        return this;
    }

    public void setMovements(Set<FoodOrderMovement> foodOrderMovements) {
        this.movements = foodOrderMovements;
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
        FoodOrder foodOrder = (FoodOrder) o;
        if (foodOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foodOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FoodOrder{" +
            "id=" + getId() +
            ", timeRating=" + getTimeRating() +
            ", priceRating=" + getPriceRating() +
            ", qualityRating=" + getQualityRating() +
            ", loyaltyPoints=" + getLoyaltyPoints() +
            ", addressRating=" + getAddressRating() +
            ", date='" + getDate() + "'" +
            ", price=" + getPrice() +
            ", status='" + getStatus() + "'" +
            ", purchaserOpinion='" + getPurchaserOpinion() + "'" +
            ", purchaserComment='" + getPurchaserComment() + "'" +
            ", city='" + getCity() + "'" +
            ", phone='" + getPhone() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
