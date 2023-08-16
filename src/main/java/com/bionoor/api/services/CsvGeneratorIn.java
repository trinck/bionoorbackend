package com.bionoor.api.services;

import java.io.Serializable;
import java.util.List;

import org.springframework.core.io.Resource;

import com.bionoor.api.models.Product;

public interface CsvGeneratorIn {


	public byte[] generateCsv(List<Object> list);
}
