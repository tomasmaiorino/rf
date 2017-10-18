package com.tsm.rf.controllers;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashSet;
import java.util.Set;

import javax.validation.groups.Default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tsm.rf.model.Transfer;
import com.tsm.rf.parser.TransferParser;
import com.tsm.rf.resources.TransferResource;
import com.tsm.rf.service.TransferService;

@RestController
@RequestMapping(value = "/api/v1/transfers")
public class TransfersController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(TransfersController.class);

	@Autowired
	private TransferParser parser;

	@Autowired
	private TransferService service;

	@RequestMapping(method = POST, consumes = JSON_VALUE, produces = JSON_VALUE)
	@ResponseStatus(CREATED)
	public TransferResource save(@RequestBody final TransferResource resource) {
		log.debug("Recieved a request to create a transfer [{}].", resource);

		validate(resource, Default.class);

		Transfer transfer = parser.toModel(resource);

		service.save(transfer);

		TransferResource result = parser.toResource(transfer);

		log.debug("returning resource [{}].", result);

		return result;
	}

	@RequestMapping(method = GET, produces = JSON_VALUE)
	@ResponseStatus(OK)
	public Set<TransferResource> find() {
		log.info("Recieved a request to search for all transfers");

		Set<Transfer> transfers = service.findAll();
	
		Set<TransferResource> resources = new HashSet<>();

		if (!transfers.isEmpty()) {
			resources = parser.toResources(transfers);
		}

		log.debug("returning resources [{}].", resources.size());

		return resources;
	}

}
