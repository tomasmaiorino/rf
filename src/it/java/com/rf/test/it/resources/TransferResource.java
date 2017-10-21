package com.rf.test.it.resources;

import static com.jayway.restassured.RestAssured.given;

import java.util.Objects;

import com.rf.test.utils.ServicesEndpoints;
import com.tsm.rf.util.TransferTestBuilder;

public class TransferResource {

    public static TransferResource build() {
        return new TransferResource();
    }

    private Long id;

    private String destinationAccount;

    private String originAccount;

    private String scheduleDate;

    private Double transferValue;

    private Double tax;

    private String createdDate;

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

    public static class TransferSupport {

        public TransferResource resource;

        private TransferSupport() {
            resource = new TransferResource();
        }

        public static TransferSupport Support() {
            return new TransferSupport();
        }

        public TransferResource build() {
            return resource;
        }

        public TransferSupport assertFields() {
            if (Objects.isNull(resource.getDestinationAccount())) {
                destinationAccount();
            }
            if (Objects.isNull(resource.getOriginAccount())) {
                originAccount();
            }
            if (Objects.isNull(resource.getScheduleDate())) {
                scheduleDate();
            }
            if (Objects.isNull(resource.getTransferValue())) {
                transferValue();
            }
            return this;
        }

        public TransferSupport scheduleDate(final String scheduleDate) {
            resource.setScheduleDate(scheduleDate);
            return this;
        }

        public TransferSupport scheduleDate() {
            return scheduleDate(TransferTestBuilder.getScheduleDateString());
        }

        public TransferSupport originAccount(final String originAccount) {
            resource.setOriginAccount(originAccount);
            return this;
        }

        public TransferSupport originAccount() {
            return originAccount(TransferTestBuilder.getOriginAccount());
        }

        public TransferSupport destinationAccount(final String destinationAccount) {
            resource.setDestinationAccount(destinationAccount);
            return this;
        }

        public TransferSupport destinationAccount() {
            return destinationAccount(TransferTestBuilder.getDestinationAccount());
        }

        public TransferSupport transferValue(final Double transferValue) {
            resource.setTransferValue(transferValue);
            return this;
        }

        public TransferSupport transferValue() {
            return transferValue(TransferTestBuilder.getTransferValue());
        }

        public TransferResource create() {
            assertFields();
            return given().contentType("application/json").body(resource).when().post(ServicesEndpoints.TRANSFER_POST)
                .as(TransferResource.class);
        }

    }

}
