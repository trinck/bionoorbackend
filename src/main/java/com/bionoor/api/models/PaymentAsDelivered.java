package com.bionoor.api.models;

import java.io.Serializable;

import com.bionoor.api.utils.BionoorEntityListener;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@DiscriminatorValue(value = "Pay as delivered")
public class PaymentAsDelivered extends Payment implements Serializable{

}