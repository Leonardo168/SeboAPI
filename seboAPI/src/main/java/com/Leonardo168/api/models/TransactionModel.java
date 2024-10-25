package com.Leonardo168.api.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_TRANSACTION")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID transactionId;
	@Column(nullable = false)
	private UUID productId;
	@Column(nullable = false)
	private UUID vendorId;
	@Column(nullable = false)
	private UUID buyerId;
	@Column(nullable = false)
	private LocalDateTime transactionDate;

}
