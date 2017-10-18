package com.tsm.rf.service;

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
        BigDecimal tv = new BigDecimal(transferValue);

        if (intervalDays == 0) {
            rate = new Rate(TYPE_A, tv, mininalRate, mininalRatePercente, intervalDays).compute();
        } else if (intervalDays > 0 && intervalDays <= 10) {
            rate = new Rate(TYPE_B, tv, secondRate, null, intervalDays).compute();
        } else {
            rate = new Rate(TYPE_C, tv, null, null, intervalDays).compute();
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
                    rate = rate.add(new BigDecimal(rateCalculation)).add(calculate(ratePercente.doubleValue(), transferValue));
                    break;
                case TYPE_B:
                    rate = rate.add(new BigDecimal(rateCalculation)).multiply(new BigDecimal(intervalDays));
                    break;
                default:
                    rate = computeTypeC();
                    break;
            }
            return rate;
        }

        private BigDecimal computeTypeC() {
            BigDecimal rate = null;
            if (intervalDays > 10 && intervalDays <= 20) {
                rate = calculate(8d, transferValue);
            } else if (intervalDays > 20 && intervalDays <= 30) {
                rate = calculate(6d, transferValue);
            } else if (intervalDays > 30 && intervalDays <= 40) {
                rate = calculate(4d, transferValue);
            } else if (intervalDays > 40) {
                rate = calculate(2d, transferValue);
            }
            return rate;
        }
    }

    private BigDecimal calculate(double percentage, BigDecimal transferValue) {
        return new BigDecimal(percentage / 100d).multiply(transferValue);
    }

}
