package com.bionoor.api.dto;

import java.util.Date;

public interface InputDiscountIn {

	
	public String getCode();
	public Double getDiscount();
	public Date getEndDate();
	public Boolean getActif();
}
