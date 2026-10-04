package com.infy.neobank360.systemhealth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/system-health")
@CrossOrigin(origins = "http://localhost:4200")
public class SystemHealthController {

    @Autowired
    private SystemHealthService service;

    @GetMapping
    public SystemHealthDTO getHealth() {

        return service.getSystemHealth();
    }
}
