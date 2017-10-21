package com.tsm.rf.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tsm.rf.exception.BadRequestException;
import com.tsm.rf.model.Transfer;
import com.tsm.rf.repository.TransferRepository;
import com.tsm.rf.util.TransferTestBuilder;

@FixMethodOrder(MethodSorters.JVM)
public class TransferServiceTest {

    private static final Double FAKE_RATE_CALCULATED = 3d;

    @InjectMocks
    private TransferService service;

    @Mock
    private TransferRepository repository;

    @Mock
    private RateCalculationService rateService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void save_NullTransferGiven_ShouldThrowException() {
        // Set up
        Transfer transfer = null;

        // Do test
        try {
            service.save(transfer);
            fail();
        } catch (IllegalArgumentException e) {
        }

        // Assertions
        verifyZeroInteractions(repository, rateService);
    }

    @Test
    public void save_InapplicableTaxGiven_ShouldThrowException() {
        // Set up
        Transfer transfer = TransferTestBuilder.buildModel();

        // Expectations
        when(rateService.computeRate(transfer.getScheduleDate(), transfer.getTransferValue()))
            .thenReturn(null);

        // Do test
        try {
            service.save(transfer);
            fail();
        } catch (BadRequestException e) {
        }

        // Assertions
        verify(rateService).computeRate(transfer.getScheduleDate(), transfer.getTransferValue());
        verifyZeroInteractions(repository);
    }

    @Test
    public void save_ValidTransferGiven_ShouldSaveTransfer() {
        // Set up
        Transfer transfer = TransferTestBuilder.buildModel();

        // Expectations
        when(rateService.computeRate(transfer.getScheduleDate(), transfer.getTransferValue()))
            .thenReturn(FAKE_RATE_CALCULATED);
        when(repository.save(transfer)).thenReturn(transfer);

        // Do test
        Transfer result = service.save(transfer);

        // Assertions
        verify(rateService).computeRate(transfer.getScheduleDate(), transfer.getTransferValue());
        verify(repository).save(transfer);

        assertNotNull(result);
        assertThat(result, is(transfer));
    }

    @Test
    public void findAll_NotFoundTransferGiven_ShouldReturnEmptyContent() {
        // Expectations
        when(repository.findAll()).thenReturn(Collections.emptySet());

        // Do test
        Set<Transfer> result = service.findAll();

        // Assertions
        verify(repository).findAll();

        assertNotNull(result);
        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void findAll_FoundTransferGiven_ShouldReturnContent() {
        // Set up
        Transfer transfer = TransferTestBuilder.buildModel();
        Set<Transfer> transfers = new HashSet<>();
        transfers.add(transfer);

        // Expectations
        when(repository.findAll()).thenReturn(transfers);

        // Do test
        Set<Transfer> result = service.findAll();

        // Assertions
        verify(repository).findAll();

        assertNotNull(result);
        assertThat(result.isEmpty(), is(false));
    }

}
