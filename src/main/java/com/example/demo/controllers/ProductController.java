package com.example.demo.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.ProductDto;
import com.example.demo.entities.Product;
import com.example.demo.redisServices.ProductRedisService;
import com.example.demo.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRedisService productRedisService;
	
	@GetMapping
	public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "") String keyword, @RequestParam int page, @RequestParam int size) {
		Map<String, Object> response = new LinkedHashMap<>();
		
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
		
		List<ProductDto> productResponses = null;
		try {
			productResponses = productRedisService.getAllProducts(keyword, pageRequest);
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		if(productResponses == null) {
            List<ProductDto> products = productService.findAll(pageRequest).getContent();
            try {
				productRedisService.saveAllProducts(products, keyword, pageRequest);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
				response.put("message", e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
            response.put("status", HttpStatus.OK.value());
    		response.put("data", products);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
		
		
		response.put("status", HttpStatus.OK.value());
		response.put("data", productResponses);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/getAllNoRedis")
	public ResponseEntity<?> getAllProductsNoRedis(@RequestParam(defaultValue = "") String keyword,
			@RequestParam int page, @RequestParam int size) {
		Map<String, Object> response = new LinkedHashMap<>();

		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());

		List<ProductDto> products = productService.findAll(pageRequest).getContent();

		response.put("status", HttpStatus.OK.value());
		response.put("data", products);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/saveFlush")
	public void saveFlush() {
		for (int i=0; i<=1000; i++) {
			Product p = new Product();
			p.setDescription("Description " + i);
			p.setImageUrl("Image URL " + i);
			p.setName("Name " + i);
			p.setPrice(i*100);
			p.setQuantity(i+10);
			
			productService.save(p);
		}
		
		System.out.println("Done");
	}
	
	@GetMapping("/update")
	public void update() {
		Optional<Product> p = productService.findById(1);
		if (p.isPresent()) {
			Product product = p.get();
			product.setDescription("Description Updated");
			product.setImageUrl("Image URL Updated");
			product.setName("Name Updated");
			product.setPrice(1000);
			productService.save(product);
			System.out.println("Done");
		}
		else {
			System.out.println("Product not found");
		}
	}
}
