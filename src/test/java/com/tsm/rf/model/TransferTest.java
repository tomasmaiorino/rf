package com.tsm.rf.model;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.tsm.rf.model.Transfer;

@FixMethodOrder(MethodSorters.JVM)
public class TransferTest {

    private String destinationAccount;
    private String originAccount;
    private LocalDateTime scheduleDate;
    private Double transferValue;
    private Double tax;

    @Before
    public void setUp() {
        scheduleDate = LocalDateTime.now();
        destinationAccount = random(20, true, true);
        originAccount = random(20, true, true);
        transferValue = RandomUtils.nextDouble(1, 100);
        tax = RandomUtils.nextDouble(1, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_NullDestinationAccountGiven_ShouldThrowException() {

        // Set up
        destinationAccount = null;

        // Do test
        buildTransfer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_EmptyDestinationAccountGiven_ShouldThrowException() {

        // Set up
        destinationAccount = "";

        // Do test
        buildTransfer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_NullOriginAccountGiven_ShouldThrowException() {

        // Set up
        originAccount = null;

        // Do test
        buildTransfer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_EmptyOriginAccountGiven_ShouldThrowException() {

        // Set up
        originAccount = "";

        // Do test
        buildTransfer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_NullScheduleDateGiven_ShouldThrowException() {

        // Set up
        scheduleDate = null;

        // Do test
        buildTransfer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_NullTransferValueGiven_ShouldThrowException() {

        // Set up
        transferValue = null;

        // Do test
        buildTransfer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_ZeroTransferValueGiven_ShouldThrowException() {

        // Set up
        transferValue = 0d;

        // Do test
        buildTransfer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_NegativeTransferValueGiven_ShouldThrowException() {

        // Set up
        transferValue = -1d;

        // Do test
        buildTransfer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_NullTaxGiven_ShouldThrowException() {

        // Set up
        tax = null;

        // Do test
        buildTransfer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_ZeroTaxGiven_ShouldThrowException() {

        // Set up
        tax = 0d;

        // Do test
        buildTransfer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_NegativeTaxGiven_ShouldThrowException() {

        // Set up
        tax = -1d;

        // Do test
        buildTransfer();
    }

    @Test
    public void build_AllValuesGiven_ShouldBuildTransfer() {
        // Set up
        Transfer transfer = buildTransfer();

        // Assertions
        Assert.assertThat(transfer,
            allOf(hasProperty("destinationAccount", is(destinationAccount)), hasProperty("originAccount", is(originAccount)),
                hasProperty("scheduleDate", is(scheduleDate)), hasProperty("transferValue", is(transferValue)),
                hasProperty("tax", is(tax))));
    }

    private Transfer buildTransfer() {
        Transfer transfer = new Transfer();
        transfer.setDestinationAccount(destinationAccount);
        transfer.setOriginAccount(originAccount);
        transfer.setScheduleDate(scheduleDate);
        transfer.setTransferValue(transferValue);
        transfer.setTax(tax);
        return transfer;
    }

}
