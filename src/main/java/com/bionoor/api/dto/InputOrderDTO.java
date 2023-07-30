package com.bionoor.api.dto;

import java.util.List;


import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputOrderDTO{
	 
    
    
    private Long paymentMethod;
    private List<InputOrderItemDTO> orderItems; // list of items in the order

   
}