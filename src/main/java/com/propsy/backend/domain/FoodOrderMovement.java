package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.propsy.backend.domain.enumeration.FoodOrderParticipant;

/**
 * A FoodOrderMovement.
 */
@Entity
@Table(name = "food_order_movement")
public class FoodOrderMovement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sender", nullable = false)
    private FoodOrderParticipant sender;

    @ManyToOne
    @JsonIgnoreProperties("movements")
    private FoodOrder foodOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public FoodOrderMovement date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public FoodOrderMovement latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public FoodOrderMovement longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public FoodOrderParticipant getSender() {
        return sender;
    }

    public FoodOrderMovement sender(FoodOrderParticipant sender) {
        this.sender = sender;
        return this;
    }

    public void setSender(FoodOrderParticipant sender) {
        this.sender = sender;
    }

    public FoodOrder getFoodOrder() {
        return foodOrder;
    }

    public FoodOrderMovement foodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
        return this;
    }

    public void setFoodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
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
        FoodOrderMovement foodOrderMovement = (FoodOrderMovement) o;
        if (foodOrderMovement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foodOrderMovement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FoodOrderMovement{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", sender='" + getSender() + "'" +
            "}";
    }
}
