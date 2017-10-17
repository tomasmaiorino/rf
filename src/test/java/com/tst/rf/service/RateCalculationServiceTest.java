package com.tst.rf.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.util.ReflectionTestUtils;

@FixMethodOrder(MethodSorters.JVM)
public class RateCalculationServiceTest {

	private RateCalculationService service = null;
	private Integer mininalRate = 3;
	private Integer mininalRatePercente = 3;
	private Integer secondRate = 12;

	@Before
	public void setUp() {
		service = new RateCalculationService();
		ReflectionTestUtils.setField(service, "mininalRate", mininalRate);
		ReflectionTestUtils.setField(service, "mininalRatePercente", mininalRatePercente);
		ReflectionTestUtils.setField(service, "secondRate", secondRate);
	}

	@Test
	public void computeRate_NullScheduleDateGiven_ShouldThrowException() {
		// Set up
		LocalDateTime scheduleDate = null;
		Double transferValue = 1d;

		// Do test
		try {
			service.computeRate(scheduleDate, transferValue);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void computeRate_DayBeforeTodayGiven_ShouldThrowException() {
		// Set up
		LocalDateTime scheduleDate = LocalDateTime.now();
		scheduleDate = scheduleDate.minusDays(1);
		Double transferValue = 1d;

		// Do test
		try {
			service.computeRate(scheduleDate, transferValue);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void computeRate_NullTransferValueGiven_ShouldThrowException() {
		// Set up
		LocalDateTime scheduleDate = LocalDateTime.now();
		Double transferValue = null;

		// Do test
		try {
			service.computeRate(scheduleDate, transferValue);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void computeRate_ZeroDayIntervalGiven_ShouldCalculateRate() {
		// Set up
		LocalDateTime scheduleDate = LocalDateTime.now();
		Double transferValue = 10.0;
		Double expectedResult = ((mininalRatePercente / 100d) * transferValue) + mininalRate;

		// Do test
		Double result = service.computeRate(scheduleDate, transferValue);

		// Assertions
		assertNotNull(result);
		assertThat(result, is(expectedResult));
	}

	@Test
	public void computeRate_ZeroTransferValueGiven_ShouldThrowException() {
		// Set up
		LocalDateTime scheduleDate = LocalDateTime.now();
		Double transferValue = 0d;

		// Do test
		try {
			service.computeRate(scheduleDate, transferValue);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	@Ignore
	public void computeRate_BetweenTwoDaysGiven_ShouldCalculateRate() {
		// Set up
		Long intervalDays = 2l;
		LocalDateTime scheduleDate = LocalDateTime.now();
		scheduleDate = scheduleDate.plusDays(intervalDays);
		Double transferValue = 1d;
		Double expectedResult = (double) intervalDays * secondRate;

		// Do test
		Double result = service.computeRate(scheduleDate, transferValue);

		// Assertions
		assertNotNull(result);
		assertThat(result, is(expectedResult));
	}

	@Test
	@Ignore
	public void computeRate_BetweenTenDaysGiven_ShouldCalculateRate() {
		// Set up
		Long intervalDays = 10l;
		LocalDateTime scheduleDate = LocalDateTime.now();
		scheduleDate = scheduleDate.plusDays(intervalDays);
		Double transferValue = 1d;
		Double expectedResult = (double) intervalDays * secondRate;

		// Do test
		Double result = service.computeRate(scheduleDate, transferValue);

		// Assertions
		assertNotNull(result);
		assertThat(result, is(expectedResult));
	}

}
