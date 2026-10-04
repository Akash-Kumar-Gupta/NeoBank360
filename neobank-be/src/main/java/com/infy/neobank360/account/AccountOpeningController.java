package com.infy.neobank360.account;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.neobank360.user.User;

@RestController
@RequestMapping("/api/account-opening")
public class AccountOpeningController {

    private final AccountOpeningRequestRepository repo;

    public AccountOpeningController(AccountOpeningRequestRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public void submitRequest(
            @RequestBody AccountOpeningRequest request,
            @AuthenticationPrincipal User user
    ) {
        request.setUser(user);
        request.setStatus(RequestStatus.PENDING);
        repo.save(request);
    }
}