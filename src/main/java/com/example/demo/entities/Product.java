package com.example.demo.entities;

import com.example.demo.utils.ProductListener;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "products")
@EntityListeners(ProductListener.class)
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private double price;
	private int quantity;
	private String imageUrl;
	private String description;

	public Product(String name, double price, int quantity, String imageUrl, String description) {
		super();
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.imageUrl = imageUrl;
		this.description = description;
	}

}
