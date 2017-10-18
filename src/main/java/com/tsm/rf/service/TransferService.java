package com.tsm.rf.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tsm.rf.model.Transfer;
import com.tsm.rf.repository.TransferRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
public class TransferService {

	private static final Logger log = LoggerFactory.getLogger(TransferService.class);

	@Autowired
	private TransferRepository repository;

	@Autowired
	private RateCalculationService rateService;

	@Transactional
	public Transfer save(final Transfer transfer) {
		Assert.notNull(transfer, "The transfer must not be null.");
		log.info("Saving transfer [{}] .", transfer);

		Double rate = rateService.computeRate(transfer.getScheduleDate(), transfer.getTransferValue());
		log.info("Rate calculated [{}].", rate);
		transfer.setTax(rate);

		repository.save(transfer);

		log.info("Saved transfer [{}].", transfer);
		return transfer;
	}

	public Set<Transfer> findAll() {
		log.info("Searching for all products");

		Set<Transfer> transfers = repository.findAll();

		log.info("Transfer found [{}].", transfers.size());

		return transfers;
	}

}
