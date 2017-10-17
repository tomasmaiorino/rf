package com.tst.rf.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.util.Assert;

@Entity
@Table(name = "transfer")
public class Transfer {

	@Id
	@GeneratedValue
	private Long id;

	private String destinationAccount;

	private String originAccount;

	private LocalDateTime scheduleDate;

	private LocalDateTime createDate;

	private Double tax;

	private Double transferValue;

	@PrePersist
	private void setCreateDate() {
		this.createDate = LocalDateTime.now();
	}

	public String getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(final String destinationAccount) {
		Assert.hasText(destinationAccount, "The destinationAccount must not be null or empty!");
		this.destinationAccount = destinationAccount;
	}

	public String getOriginAccount() {
		return originAccount;
	}

	public void setOriginAccount(String originAccount) {
		Assert.hasText(originAccount, "The originAccount must not be null or empty!");
		this.originAccount = originAccount;
	}

	public LocalDateTime getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(LocalDateTime scheduleDate) {
		Assert.notNull(scheduleDate, "The scheduleDate must not be null!");
		this.scheduleDate = scheduleDate;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		Assert.notNull(tax, "The tax must not be null!");
		this.tax = tax;
	}

	public Long getId() {
		return id;
	}

	public Double getTransferValue() {
		return transferValue;
	}

	public void setTransferValue(final Double transferValue) {
		Assert.notNull(transferValue, "The transferValue must not be null!");
		Assert.isTrue(transferValue > 0, "The transferValue must greater that zero!");
		this.transferValue = transferValue;
	}

}
