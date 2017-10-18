package com.tsm.rf.resources;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.tsm.rf.util.TransferTestBuilder;

@FixMethodOrder(MethodSorters.JVM)
public class TransferResourceTest {

    protected Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void build_NullDestinationAccountGiven_ShouldThrowException() {
        // Set up
        TransferResource resource = TransferTestBuilder.buildResource();
        resource.setDestinationAccount(null);

        // Do test
        Set<ConstraintViolation<TransferResource>> result = validator.validate(resource, Default.class);

        // Assertions
        assertNotNull(result);
        assertThat(result.size(), is(1));
        assertThat(result.iterator().next().getMessageTemplate(), is("destinationAccountRequired"));
    }

    @Test
    public void build_EmptyDestinationAccountGiven_ShouldThrowException() {
        // Set up
        TransferResource resource = TransferTestBuilder.buildResource();
        resource.setDestinationAccount("");

        // Do test
        Set<ConstraintViolation<TransferResource>> result = validator.validate(resource, Default.class);

        // Assertions
        assertNotNull(result);
        assertThat(result.size(), is(1));
        assertThat(result.iterator().next().getMessageTemplate(), is("destinationAccountInvalid"));
    }

    @Test
    public void build_LargeDestinationAccountGiven_ShouldThrowException() {
        // Set up
        TransferResource resource = TransferTestBuilder.buildResource();
        resource.setDestinationAccount(TransferTestBuilder.LARGE_DESTINATION_ACCOUNT);

        // Do test
        Set<ConstraintViolation<TransferResource>> result = validator.validate(resource, Default.class);

        // Assertions
        assertNotNull(result);
        assertThat(result.size(), is(1));
        assertThat(result.iterator().next().getMessageTemplate(), is("destinationAccountInvalid"));
    }

    //

    @Test
    public void build_NullOriginAccountGiven_ShouldThrowException() {
        // Set up
        TransferResource resource = TransferTestBuilder.buildResource();
        resource.setOriginAccount(null);

        // Do test
        Set<ConstraintViolation<TransferResource>> result = validator.validate(resource, Default.class);

        // Assertions
        assertNotNull(result);
        assertThat(result.size(), is(1));
        assertThat(result.iterator().next().getMessageTemplate(), is("originAccountRequired"));
    }

    @Test
    public void build_EmptyOriginAccountGiven_ShouldThrowException() {
        // Set up
        TransferResource resource = TransferTestBuilder.buildResource();
        resource.setOriginAccount("");

        // Do test
        Set<ConstraintViolation<TransferResource>> result = validator.validate(resource, Default.class);

        // Assertions
        assertNotNull(result);
        assertThat(result.size(), is(1));
        assertThat(result.iterator().next().getMessageTemplate(), is("originAccountInvalid"));
    }

    @Test
    public void build_LargeOriginAccountGiven_ShouldThrowException() {
        // Set up
        TransferResource resource = TransferTestBuilder.buildResource();
        resource.setOriginAccount(TransferTestBuilder.LARGE_ORIGIN_ACCOUNT);

        // Do test
        Set<ConstraintViolation<TransferResource>> result = validator.validate(resource, Default.class);

        // Assertions
        assertNotNull(result);
        assertThat(result.size(), is(1));
        assertThat(result.iterator().next().getMessageTemplate(), is("originAccountInvalid"));
    }

    //

    @Test
    public void build_NullTransferValueGiven_ShouldThrowException() {
        // Set up
        TransferResource resource = TransferTestBuilder.buildResource();
        resource.setTransferValue(null);

        // Do test
        Set<ConstraintViolation<TransferResource>> result = validator.validate(resource, Default.class);

        // Assertions
        assertNotNull(result);
        assertThat(result.size(), is(1));
        assertThat(result.iterator().next().getMessageTemplate(), is("transferValueRequired"));
    }

    @Test
    public void build_InvalidTransferValueGiven_ShouldThrowException() {
        // Set up
        TransferResource resource = TransferTestBuilder.buildResource();
        resource.setTransferValue(0d);

        // Do test
        Set<ConstraintViolation<TransferResource>> result = validator.validate(resource, Default.class);

        // Assertions
        assertNotNull(result);
        assertThat(result.size(), is(1));
        assertThat(result.iterator().next().getMessageTemplate(), is("transferValueInvalid"));
    }

}
