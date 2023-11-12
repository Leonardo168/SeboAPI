package com.Leonardo168.api.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Leonardo168.api.models.TransactionModel;
import com.Leonardo168.api.repositories.ProductRepository;
import com.Leonardo168.api.repositories.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	ProductRepository productRepository;

	public Object save(TransactionModel transactionModel) {
		return transactionRepository.save(transactionModel);
	}

	public Optional<Page<Object>> findByBuyerId(UUID userId, Pageable pageable) {
		return transactionRepository.findByBuyerId(userId, pageable);
	}

}
