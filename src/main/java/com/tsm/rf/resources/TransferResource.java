package com.tsm.rf.resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tsm.rf.exception.ErrorCodes;
import com.tsm.rf.validator.CustomDate;
import com.tsm.rf.validator.CustomDate.Type;

public class TransferResource {

    public static final String SCHEDULE_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_REGEX = "20\\d\\d-(0[1-9]|1[0-2])-([1-2][0-9]|0[1-9]|3[0-1])";

    private Long id;

    @NotNull(message = ErrorCodes.DESTINATION_ACCOUNT_REQUIRED)
    @Size(min = 3, max = 20, message = ErrorCodes.DESTINATION_ACCOUNT_INVALID)
    private String destinationAccount;

    @NotNull(message = ErrorCodes.ORIGIN_ACCOUNT_REQUIRED)
    @Size(min = 3, max = 20, message = ErrorCodes.ORIGIN_ACCOUNT_INVALID)
    private String originAccount;

    @NotNull(message = ErrorCodes.SCHEDULE_DATE_REQUIRED)
    @CustomDate(message = ErrorCodes.SCHEDULE_DATE_INVALID, type = Type.FUTURE_TODAY)
    private String scheduleDate;

    @NotNull(message = ErrorCodes.TRANSFER_VALUE_REQUIRED)
    @Min(value = 1, message = ErrorCodes.TRANSFER_VALUE_INVALID)
    private Double transferValue;

    private String createdDate;

    private Double tax;

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

    @JsonIgnore
    @Future
    public Date getScheduleDateAsDate() {
        if (StringUtils.isBlank(scheduleDate)) {
            return null;
        }
        try {
            Date a = DateFormat.getDateInstance().parse(getScheduleDate());
            return a;
        } catch (ParseException e) {
        }
        return null;
    }

}
