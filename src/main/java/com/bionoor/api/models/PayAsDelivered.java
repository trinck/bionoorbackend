package com.bionoor.api.models;

import java.io.Serializable;

import com.bionoor.api.utils.BionoorEntityListener;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue(value = "pay_as_delivered")
public class PayAsDelivered extends PaymentMethod implements Serializable{

}