package com.tsm.rf.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.util.Assert;

@Entity
@Table(name = "transfer")
public class Transfer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "destination_account", nullable = false)
    private String destinationAccount;

    @Column(name = "origin_account", nullable = false)
    private String originAccount;

    @Column(name = "schedule_date", nullable = false)
    private LocalDateTime scheduleDate;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "tax", nullable = false)
    private Double tax;

    @Column(name = "transfer_value", nullable = false)
    private Double transferValue;

    @PrePersist
    private void setCreateDate() {
        this.createDate = LocalDateTime.now();
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(final String destinationAccount) {
        Assert.hasText(destinationAccount, "The destinationAccount must not be null or empty!");
        this.destinationAccount = destinationAccount;
    }

    public String getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(String originAccount) {
        Assert.hasText(originAccount, "The originAccount must not be null or empty!");
        this.originAccount = originAccount;
    }

    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDateTime scheduleDate) {
        Assert.notNull(scheduleDate, "The scheduleDate must not be null!");
        this.scheduleDate = scheduleDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        Assert.notNull(tax, "The tax must not be null!");
        Assert.isTrue(tax > 0, "The tax must greater that zero!");
        this.tax = tax;
    }

    public Long getId() {
        return id;
    }

    public Double getTransferValue() {
        return transferValue;
    }

    public void setTransferValue(final Double transferValue) {
        Assert.notNull(transferValue, "The transferValue must not be null!");
        Assert.isTrue(transferValue > 0, "The transferValue must greater that zero!");
        this.transferValue = transferValue;
    }

}
