package com.tst.rf.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class RateCalculationService {

	private static final String TYPE_A = "A";
	private static final String TYPE_B = "B";
	private static final String TYPE_C = "C";

	@Value("${minimal.rate}")
	private Integer mininalRate;

	@Value("${minimal.rate.percente}")
	private Integer mininalRatePercente;

	@Value("${second.rate}")
	private Integer secondRate;

	public Double computeRate(final LocalDateTime scheduleDate, final Double transferValue) {
		Assert.notNull(scheduleDate, "The scheduleDate must not be null!");
		Assert.isTrue(!scheduleDate.isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)),
				"The scheduleDate must not be before today!");
		Assert.notNull(transferValue, "The transferValue must not be null!");
		Assert.isTrue(transferValue > 0, "The transferValue must greater that zero!");

		LocalDateTime now = LocalDateTime.now();

		Long intervalDays = ChronoUnit.DAYS.between(now.truncatedTo(ChronoUnit.DAYS),
				scheduleDate.truncatedTo(ChronoUnit.DAYS));
		BigDecimal rate = new BigDecimal(0);
		BigDecimal tv = new BigDecimal(0);

		// acima de 10 até 20 dias da data de agendamento 8%
		// acima de 20 até 30 dias da data de agendamento 6%
		// acima de 30 até 40 dias da data de agendamento 4%
		// acima de 40 dias da data de agendamento e valor superior a 100.000 2%

		if (intervalDays == 0) {
			rate = rate.add(new BigDecimal(mininalRate))
					.add(BigDecimal.valueOf(mininalRatePercente / 100d * transferValue));

		} else if (intervalDays > 0 && intervalDays <= 10) {
			rate = new Rate(TYPE_A, tv, mininalRate, mininalRatePercente, intervalDays).compute();
		} else if (intervalDays > 0 && intervalDays <= 20) {
			rate = new Rate(TYPE_B, tv, secondRate, null, intervalDays).compute();
			rate = rate.multiply(calculate(8d, tv));
		} else if (intervalDays > 20 && intervalDays <= 30) {
			rate = rate.multiply(calculate(6d, tv));
		} else if (intervalDays > 30 && intervalDays <= 40) {
			rate = rate.multiply(calculate(4d, tv));
		} else if (intervalDays > 40) {
			rate = rate.multiply(calculate(2d, tv));
		}

		return rate.doubleValue();
	}

	private class Rate {

		private String rateType;

		private BigDecimal transferValue;

		private Integer rateCalculation;

		private Integer ratePercente;

		private Long intervalDays;

		public Rate(String rateType, BigDecimal transferValue, Integer rateCalculation, Integer ratePercente,
				Long intervalDays) {
			this.rateType = rateType;
			this.transferValue = transferValue;
			this.rateCalculation = rateCalculation;
			this.ratePercente = ratePercente;
			this.intervalDays = intervalDays;

		}

		public BigDecimal compute() {
			BigDecimal rate = new BigDecimal(0);
			switch (rateType) {
			case TYPE_A:
				rate.add(new BigDecimal(rateCalculation)).add(calculate(ratePercente, transferValue));
				break;
			case TYPE_B:
				rate.add(new BigDecimal(rateCalculation)).multiply(new BigDecimal(intervalDays));
				break;
			default:

				break;
			}
			return rate;
		}
	}

	private BigDecimal calculate(double percentage, BigDecimal transferValue) {
		return new BigDecimal(percentage / 100d).multiply(transferValue);
	}

}
