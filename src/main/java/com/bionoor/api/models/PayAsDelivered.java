package com.bionoor.api.models;

import java.io.Serializable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue(value = "pay_as_delivered")
public class PayAsDelivered extends PaymentMethod implements Serializable{

}