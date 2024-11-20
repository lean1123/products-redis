package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dtos.ProductDto;
import com.example.demo.entities.Product;

public interface ProductService {

	void deleteById(Integer id);

	Optional<Product> findById(Integer id);

	List<Product> findAll();

	Page<ProductDto> findAll(Pageable pageable);

	<S extends Product> S save(S entity);

}
