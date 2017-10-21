package com.tsm.rf.util;

import static org.apache.commons.lang3.RandomStringUtils.random;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.test.util.ReflectionTestUtils;

import com.tsm.rf.model.Transfer;
import com.tsm.rf.resources.TransferResource;

public class TransferTestBuilder {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String LARGE_DESTINATION_ACCOUNT = random(21, true, true);
    public static final String LARGE_ORIGIN_ACCOUNT = random(21, true, true);
    public static final String INVALID_DATE = "2017-31-12";
    public static final String PAST_SCHEDULE_DATE = "2016-11-02";

    public static String getDestinationAccount() {
        return random(20, true, true);
    }

    public static String getOriginAccount() {
        return random(20, true, true);
    }

    public static LocalDateTime getScheduleDate() {
        LocalDateTime now = LocalDateTime.now();
        now.plusDays(RandomUtils.nextLong(1, 5));
        return now;
    }

    public static String getTodayScheduleDateString() {
        return LocalDateTime.now().format(formatter);
    }

    public static String getScheduleDateString() {
        return getScheduleDate().format(formatter);
    }

    public static String getScheduleDateString(int intervalDays) {
        LocalDateTime now = LocalDateTime.now();
        if (intervalDays >= 0) {
            now = now.plusDays(intervalDays);
        } else {
            now = now.minusDays(intervalDays);
        }
        return now.format(formatter);
    }

    public static Double getTax() {
        BigDecimal bd = new BigDecimal(RandomUtils.nextDouble(1, 1000));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public static Double getTransferValue() {
        BigDecimal bd = new BigDecimal(RandomUtils.nextDouble(1, 1000));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public static TransferResource buildResource() {
        return buildResource(getDestinationAccount(), getOriginAccount(), getScheduleDateString(), getTransferValue());
    }

    public static TransferResource buildResource(final String destitationAccount, final String originAccount,
        final String scheduleDateString, final Double transferValue) {
        TransferResource resource = new TransferResource();
        resource.setDestinationAccount(destitationAccount);
        resource.setOriginAccount(originAccount);
        resource.setScheduleDate(scheduleDateString);
        resource.setTransferValue(transferValue);
        return resource;
    }

    public static Transfer buildModel() {
        return buildModel(getDestinationAccount(), getOriginAccount(), getScheduleDate(), getTransferValue(), getTax());
    }

    public static Transfer buildModel(Long transferId) {
        Transfer transfer = buildModel(getDestinationAccount(), getOriginAccount(), getScheduleDate(), getTransferValue(),
            getTax());
        ReflectionTestUtils.setField(transfer, "id", RandomUtils.nextLong(1, 100));
        ReflectionTestUtils.setField(transfer, "createDate", LocalDateTime.now());
        return transfer;
    }

    public static Transfer buildModel(final String destinationAccount, final String originAccount,
        final LocalDateTime scheduleDate, final Double transferValue, final Double tax) {
        return Transfer.TransferBuilder.Transfer(destinationAccount, originAccount,
            scheduleDate, transferValue).tax(tax).build();
    }

}
