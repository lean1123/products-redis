package com.example.demo.redisServices;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.example.demo.dtos.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ProductRedisService {

	void saveAllProducts(List<ProductDto> productResponses, String keyword, PageRequest pageRequest) throws JsonProcessingException;

	void clear();

	List<ProductDto> getAllProducts(String keyword, PageRequest pageRequest) throws JsonMappingException, JsonProcessingException;

}
