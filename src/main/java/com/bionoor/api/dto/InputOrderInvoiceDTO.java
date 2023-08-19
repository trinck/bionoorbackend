package com.bionoor.api.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.models.Order.OrderStatus;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputOrderInvoiceDTO{
	 
	@NotNull
	private Long order;
	private Long id;
	
	private OrderStatus status;
	
	private Boolean paid;

	@Nonnull
	private Double transport;

	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dueDate;

	private int vat;
	@DecimalMax(value = "100.0")
	private Double remise;


	    
}
