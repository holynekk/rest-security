package com.holynekk.apisecurity.api.server;

import com.holynekk.apisecurity.api.request.sqlinjection.JdbcCustomerPatchRequest;
import com.holynekk.apisecurity.entity.JdbcCustomer;
import com.holynekk.apisecurity.repository.JdbcCustomerDangerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/api/sqlinjection/danger/v1")
//@Validated
public class jdbcCustomerDangerApi {

    @Autowired
    private JdbcCustomerDangerRepository repository;

    @GetMapping(value = "/customer/{email}")
    public JdbcCustomer findCustomerByEmail(@PathVariable(required = true, name = "email") String email) {
        return repository.findCustomerByEmail(email);
    }

    @GetMapping(value = "/customer")
    public List<JdbcCustomer> findCustomersByGender(@RequestParam(required = true, name = "genderCode") String genderCode) {
        return repository.findCustomersByGender(genderCode);
    }

    @PostMapping(value = "/customer")
    public void createCustomer(@RequestBody(required = true) JdbcCustomer newCustomer) {
        repository.createCustomer(newCustomer);
    }

    @PatchMapping(value = "/customer")
    public void updateCustomerFullName(@PathVariable(required = true, name = "customerId") int id,
                                       @RequestBody(required = true) JdbcCustomerPatchRequest request) {
        repository.updateCustomerFullName(id, request.getNewFullName());
    }
}
