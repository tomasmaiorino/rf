package com.tsm.rf.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
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

    private Transfer() {
    }

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

    public void setOriginAccount(final String originAccount) {
        Assert.hasText(originAccount, "The originAccount must not be null or empty!");
        this.originAccount = originAccount;
    }

    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(final LocalDateTime scheduleDate) {
        Assert.notNull(scheduleDate, "The scheduleDate must not be null!");
        this.scheduleDate = scheduleDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(final Double tax) {
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

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Transfer other = (Transfer) obj;
        if (getId() == null || other.getId() == null) {
            return false;
        }
        return getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public static class TransferBuilder {

        private final Transfer transfer;

        private TransferBuilder(final String destinationAccount, final String originAccount, final LocalDateTime scheduleDate,
            final Double transferValue) {
            transfer = new Transfer();
            transfer.setDestinationAccount(destinationAccount);
            transfer.setOriginAccount(originAccount);
            transfer.setScheduleDate(scheduleDate);
            transfer.setTransferValue(transferValue);
        }

        public TransferBuilder tax(final Double tax) {
            transfer.setTax(tax);
            return this;
        }

        public static TransferBuilder Transfer(final String destinationAccount, final String originAccount,
            final LocalDateTime scheduleDate,
            final Double transferValue) {
            return new TransferBuilder(destinationAccount, originAccount, scheduleDate, transferValue);
        }

        public Transfer build() {
            return transfer;
        }
    }
}
