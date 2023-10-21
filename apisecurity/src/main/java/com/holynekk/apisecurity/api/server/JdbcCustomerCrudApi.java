package com.holynekk.apisecurity.api.server;

import com.holynekk.apisecurity.api.request.sqlinjection.JdbcCustomerPatchRequest;
import com.holynekk.apisecurity.entity.JdbcCustomer;
import com.holynekk.apisecurity.repository.JdbcCustomerCrudRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sqlinjection/crud/v1")
@Validated
public class JdbcCustomerCrudApi {

    @Autowired
    private JdbcCustomerCrudRepository repository;

    @GetMapping(value = "/customer/{email}")
    public JdbcCustomer findCustomerByEmail(@PathVariable(required = true, name = "email") String email) {
        List<JdbcCustomer> queryResult = repository.findByEmail(email);

        if (queryResult != null && !queryResult.isEmpty()) {
            return queryResult.get(0);
        }
        return null;
    }

    @GetMapping(value = "/customer")
    public List<JdbcCustomer> findCustomersByGender(@RequestParam(required = true, name = "genderCode") String genderCode) {
        return repository.findByGender(genderCode);
    }

    @PostMapping(value = "/customer")
    public void createCustomer(@RequestBody(required = true) @Valid JdbcCustomer newCustomer) {
        repository.save(newCustomer);
    }

    @PatchMapping(value = "/customer")
    public void updateCustomerFullName(@PathVariable(required = true, name = "customerId") int customerId,
                                       @RequestBody(required = true)JdbcCustomerPatchRequest request) {
        repository.updateCustomerFullName(customerId, request.getNewFullName());
    }
}
