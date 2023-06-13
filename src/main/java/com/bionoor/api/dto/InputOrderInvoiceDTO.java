package com.bionoor.api.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputOrderInvoiceDTO{
	 
	@NotNull
	private Long order;
	private Long id;
	
	
	private Boolean paid;

	@Nonnull
	private Double transport;

	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dueDate;

	private int vat;
	private Double remise;


	    
}
