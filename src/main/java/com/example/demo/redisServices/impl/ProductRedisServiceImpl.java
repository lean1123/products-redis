package com.example.demo.redisServices.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.ProductDto;
import com.example.demo.redisServices.ProductRedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductRedisServiceImpl implements ProductRedisService {
	
	
	private final RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private final ObjectMapper redisObjectMapper;
	
	private String getKeyFrom(String keyword, PageRequest pageRequest) {
		int pageNumber = pageRequest.getPageNumber();
		int pageSize = pageRequest.getPageSize();
		Sort sort = pageRequest.getSort();
		String sortDirection = sort.getOrderFor("id").getDirection() == Sort.Direction.ASC ? "asc" : "desc";
		String key = String.format("all_products:%d:%d:%s", pageNumber, pageSize, sortDirection);
		return key;
	}

	@Override
	public void clear() {
		redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
	}
	
	@Override
	public List<ProductDto> getAllProducts(String keyword, PageRequest pageRequest) {
		String key = this.getKeyFrom(keyword, pageRequest);
		String json = (String) redisTemplate.opsForValue().get(key);
		List<ProductDto> productResponses = null;
		try {
			productResponses = json != null ? redisObjectMapper.readValue(json, new TypeReference<List<ProductDto>>() {}) : null;
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return productResponses;
	}

	@Override
	public void saveAllProducts(List<ProductDto> productResponses, String keyword, 
			PageRequest pageRequest) throws JsonProcessingException {
		String key = this.getKeyFrom(keyword, pageRequest);
		String json = redisObjectMapper.writeValueAsString(productResponses);
		redisTemplate.opsForValue().set(key, json);
	}

}
