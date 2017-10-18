package com.tsm.rf.repository;

import java.util.Set;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tsm.rf.model.Transfer;

@Transactional(propagation = Propagation.MANDATORY)
public interface TransferRepository extends Repository<Transfer, Long> {

	Transfer save(final Transfer transfer);

	Set<Transfer> findAll();

}
