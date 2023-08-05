package com.bionoor.api.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bionoor.api.models.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OutputBestSellerListDTO {

	
	private List<OutputProductBestSeller> products = new ArrayList<>() ;
	Integer month;
	private Integer stockAverage;
	private Integer limit;
}
