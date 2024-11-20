package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entities.Product;
import com.example.demo.redisServices.ProductRedisService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductListener {
	private final static Logger LOGGER = LoggerFactory.getLogger(ProductListener.class);
	
	
	private final ProductRedisService productRedisService;
	
	@PrePersist
	public void prePersist(Product product) {
		LOGGER.info("prePersist");
	}
	
	@PostPersist
	public void postPersist(Product product) {
		LOGGER.info("postPersist");
		try {
			productRedisService.clear();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	@PreUpdate
	public void preUpdate(Product product) {
		LOGGER.info("preUpdate");
	}
	
	@PostUpdate
	public void postUpdate(Product product) {
		LOGGER.info("postUpdate");
		try {
			productRedisService.clear();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	@PreRemove
	public void preRemove(Product product) {
		LOGGER.info("preRemove");
	}
	
	@PostRemove
	public void postRemove(Product product) {
		LOGGER.info("postRemove");
		try {
			productRedisService.clear();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
}
