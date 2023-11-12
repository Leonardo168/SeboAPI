package com.Leonardo168.api.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Leonardo168.api.models.TransactionModel;

public interface TransactionRepository extends JpaRepository<TransactionModel, UUID>{

	Optional<Page<Object>> findByBuyerId(UUID userId, Pageable pageable);

	Optional<Page<Object>> findByBuyerIdOrVendorId(UUID buyerId, UUID vendorId, Pageable pageable);

}
