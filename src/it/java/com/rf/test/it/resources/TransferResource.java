package com.rf.test.it.resources;

import static com.jayway.restassured.RestAssured.given;

import java.util.Objects;

import com.rf.test.utils.ServicesEndpoints;
import com.tsm.rf.util.TransferTestBuilder;

public class TransferResource {

	public static TransferResource build() {
		return new TransferResource();
	}

	public TransferResource assertFields() {
		if (Objects.isNull(destinationAccount)) {
			destinationAccount();
		}
		if (Objects.isNull(originAccount)) {
			originAccount();
		}
		if (Objects.isNull(scheduleDate)) {
			scheduleDate();
		}
		if (Objects.isNull(transferValue)) {
			transferValue();
		}
		return this;
	}

	public TransferResource scheduleDate(final String scheduleDate) {
		this.setScheduleDate(scheduleDate);
		return this;
	}

	public TransferResource scheduleDate() {
		return scheduleDate(TransferTestBuilder.getScheduleDateString());
	}

	public TransferResource originAccount(final String originAccount) {
		this.setOriginAccount(originAccount);
		return this;
	}

	public TransferResource originAccount() {
		return originAccount(TransferTestBuilder.getOriginAccount());
	}

	public TransferResource destinationAccount(final String destinationAccount) {
		this.setDestinationAccount(destinationAccount);
		return this;
	}

	public TransferResource destinationAccount() {
		return destinationAccount(TransferTestBuilder.getDestinationAccount());
	}

	public TransferResource transferValue(final Double transferValue) {
		this.setTransferValue(transferValue);
		return this;
	}

	public TransferResource transferValue() {
		return transferValue(TransferTestBuilder.getTransferValue());
	}

	public TransferResource create() {
		assertFields();
		return given().contentType("application/json").body(this).when().post(ServicesEndpoints.TRANSFER_POST)
				.as(TransferResource.class);
	}

	private Long id;

	private String destinationAccount;

	private String originAccount;

	private String scheduleDate;

	private Double transferValue;

	private Double tax;

	private String createdDate;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(final String destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public String getOriginAccount() {
		return originAccount;
	}

	public void setOriginAccount(final String originAccount) {
		this.originAccount = originAccount;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(final String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public Double getTransferValue() {
		return transferValue;
	}

	public void setTransferValue(final Double transferValue) {
		this.transferValue = transferValue;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(final Double tax) {
		this.tax = tax;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(final String createdDate) {
		this.createdDate = createdDate;
	}

}
