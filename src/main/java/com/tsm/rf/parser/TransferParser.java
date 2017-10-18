package com.tsm.rf.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.tsm.rf.model.Transfer;
import com.tsm.rf.resources.TransferResource;

@Component
public class TransferParser {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private DateTimeFormatter fromLocalDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public Transfer toModel(final TransferResource resource) {
		Assert.notNull(resource, "The resource must not be null!");
		Transfer transfer = new Transfer();
		transfer.setDestinationAccount(resource.getDestinationAccount());
		transfer.setOriginAccount(resource.getOriginAccount());
		transfer.setScheduleDate(LocalDate.parse(resource.getScheduleDate(), formatter).atStartOfDay());
		transfer.setTransferValue(resource.getTransferValue());
		return transfer;
	}

	public Set<TransferResource> toResources(final Set<Transfer> transfers) {
		Assert.notEmpty(transfers, "The transfers must not be null or empty!");

		Set<TransferResource> resources = new HashSet<>();
		transfers.forEach(i -> resources.add(toResource(i)));
		return resources;
	}

	public TransferResource toResource(final Transfer transfer) {
		Assert.notNull(transfer, "The transfer must not be null!");
		Assert.isTrue(!Objects.isNull(transfer.getId()), "The transfer must not be new!");

		TransferResource resource = new TransferResource();
		resource.setDestinationAccount(transfer.getDestinationAccount());
		resource.setId(transfer.getId());
		resource.setOriginAccount(transfer.getOriginAccount());
		resource.setScheduleDate(transfer.getScheduleDate().format(fromLocalDateTimeFormatter));
		resource.setTransferValue(transfer.getTransferValue());
		resource.setCreatedDate(transfer.getCreateDate().format(fromLocalDateTimeFormatter));
		resource.setTax(transfer.getTax());
		return resource;
	}

}
