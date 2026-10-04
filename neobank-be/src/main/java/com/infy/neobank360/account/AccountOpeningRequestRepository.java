package com.infy.neobank360.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOpeningRequestRepository
extends JpaRepository<AccountOpeningRequest, Long> {

List<AccountOpeningRequest> findByStatus(RequestStatus status);
}

