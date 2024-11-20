package com.example.demo.services.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.ProductDto;
import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.ProductService;


@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	private ProductDto convertToDTO(Product product) {
		ProductDto productDto = modelMapper.map(product, ProductDto.class);
	    return productDto;
	}
	
	private Product convertToEntity(ProductDto productDto) {
		Product product = modelMapper.map(productDto, Product.class);
	    return product;
	}

	@Override
	public <S extends Product> S save(S entity) {
		return productRepository.save(entity);
	}

	@Override
	public Page<ProductDto> findAll(Pageable pageable) {
	    return productRepository.findAll(pageable).map(this::convertToDTO);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> findById(Integer id) {
		return productRepository.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		productRepository.deleteById(id);
	}
	
	
}
