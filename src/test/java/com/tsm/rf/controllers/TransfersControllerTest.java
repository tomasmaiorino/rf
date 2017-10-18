package com.tsm.rf.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tsm.rf.model.Transfer;
import com.tsm.rf.parser.TransferParser;
import com.tsm.rf.resources.TransferResource;
import com.tsm.rf.service.TransferService;
import com.tsm.rf.util.TransferTestBuilder;

@FixMethodOrder(MethodSorters.JVM)
public class TransfersControllerTest {

	@Mock
	private TransferService service;

	@Mock
	private TransferParser parser;

	@InjectMocks
	private TransfersController controller;

	@Mock
	private Validator validator;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	@Test
	public void save_InvalidTransferResourceGiven_ShouldThrowException() {
		// Set up
		TransferResource resource = TransferTestBuilder.buildResource();

		// Expectations
		when(validator.validate(resource, Default.class)).thenThrow(new ValidationException());

		// Do test
		try {
			controller.save(resource);
			fail();
		} catch (ValidationException e) {
		}

		// Assertions
		verify(validator).validate(resource, Default.class);
		verifyZeroInteractions(service, parser);
	}

	@Test
	public void save_ValidTransferResourceGiven_ShouldSaveTransfer() {
		// Set up
		TransferResource resource = TransferTestBuilder.buildResource();
		Transfer transfer = TransferTestBuilder.buildModel();
		TransferResource expectedResource = TransferTestBuilder.buildResource();

		// Expectations
		when(parser.toModel(resource)).thenReturn(transfer);
		when(service.save(transfer)).thenReturn(transfer);
		when(parser.toResource(transfer)).thenReturn(expectedResource);

		// Do test
		TransferResource result = controller.save(resource);

		// Assertions
		verify(validator).validate(resource, Default.class);
		verify(parser).toModel(resource);
		verify(service).save(transfer);
		verify(parser).toResource(transfer);

		assertNotNull(result);
		assertThat(result, is(expectedResource));
	}

	@Test
	public void findAll_NotfoundTransferGiven_ShouldReturnEmptyContent() {
		// Expectations
		when(service.findAll()).thenReturn(Collections.emptySet());

		// Do test
		Set<TransferResource> result = controller.find();

		// Assertions
		verify(service).findAll();
		verifyZeroInteractions(parser);

		assertNotNull(result);
		assertThat(result.isEmpty(), is(true));
	}

	@Test
	public void findAll_FoundTransferGiven_ShouldReturnContent() {
		// Set up
		Set<TransferResource> resources = new HashSet<>();
		resources.add(TransferTestBuilder.buildResource());
		Set<Transfer> transfers = new HashSet<>();
		transfers.add(TransferTestBuilder.buildModel());

		// Expectations
		when(service.findAll()).thenReturn(transfers);
		when(parser.toResources(transfers)).thenReturn(resources);

		// Do test
		Set<TransferResource> result = controller.find();

		// Assertions
		verify(service).findAll();
		verify(parser).toResources(transfers);

		assertNotNull(result);
		assertThat(result.isEmpty(), is(false));
	}

}
