package com.example.demo.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductDto {
	private String name;
	private double price;
	private int quantity;
	private String imageUrl;
	private String description;
}
