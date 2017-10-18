package com.tsm.rf.parser;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.tsm.rf.model.Transfer;
import com.tsm.rf.resources.TransferResource;
import com.tsm.rf.util.TransferTestBuilder;

@FixMethodOrder(MethodSorters.JVM)
public class TransferParserTest {

	private static final Long TRANSFER_ID = null;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private DateTimeFormatter fromLocalDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@InjectMocks
	private TransferParser parse;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = IllegalArgumentException.class)
	public void toModel_NullResourceGiven_ShouldThrowException() {
		// Set up
		TransferResource resource = null;

		// Do test
		parse.toModel(resource);
	}

	@Test
	public void toModel_ValidResourceWithParentGiven_ShouldCreateModel() {
		// Set up
		TransferResource resource = TransferTestBuilder.buildResource();

		// Do test
		Transfer result = parse.toModel(resource);

		// Assertions
		assertNotNull(result);

		Assert.assertThat(result, allOf(hasProperty("destinationAccount", is(resource.getDestinationAccount())),
				hasProperty("originAccount", is(resource.getOriginAccount())),
				hasProperty("scheduleDate", is(LocalDate.parse(resource.getScheduleDate(), formatter).atStartOfDay())),
				hasProperty("transferValue", is(resource.getTransferValue())), hasProperty("tax", nullValue())));
	}

	@Test(expected = IllegalArgumentException.class)
	public void toResource_NullTransferGiven_ShouldThrowException() {
		// Set up
		Transfer transfer = null;

		// Do test
		parse.toResource(transfer);
	}

	@Test(expected = IllegalArgumentException.class)
	public void toResource_NewTransferGiven_ShouldThrowException() {
		// Set up
		Transfer transfer = TransferTestBuilder.buildModel();

		// Do test
		parse.toResource(transfer);
	}

	@Test
	public void toResource_ValidResourceWithParentGiven_ShouldCreateModel() {
		// Set up
		Transfer transfer = TransferTestBuilder.buildModel(TRANSFER_ID);

		// Do test
		TransferResource result = parse.toResource(transfer);

		// Assertions
		assertNotNull(result);

		Assert.assertThat(result,
				allOf(hasProperty("destinationAccount", is(transfer.getDestinationAccount())),
						hasProperty("originAccount", is(transfer.getOriginAccount())),
						hasProperty("scheduleDate", is(transfer.getScheduleDate().format(fromLocalDateTimeFormatter))),
						hasProperty("transferValue", is(transfer.getTransferValue())),
						hasProperty("tax", is(transfer.getTax()))));
	}

	@Test(expected = IllegalArgumentException.class)
	public void toResources_NullTransfersGiven_ShouldThrowException() {
		// Set up
		Set<Transfer> transfers = null;

		// Do test
		parse.toResources(transfers);
	}

	@Test(expected = IllegalArgumentException.class)
	public void toResources_EmptyTransfersGiven_ShouldThrowException() {
		// Set up
		Set<Transfer> transfers = Collections.emptySet();

		// Do test
		parse.toResources(transfers);
	}

	@Test
	public void toResources_ValidTransfersGiven_ShouldReturnContent() {
		// Set up
		Set<Transfer> transfers = Collections.singleton(TransferTestBuilder.buildModel(TRANSFER_ID));

		// Do test
		Set<TransferResource> result = parse.toResources(transfers);

		// Assertions
		assertNotNull(result);
		assertThat(result.isEmpty(), is(false));
	}

}
