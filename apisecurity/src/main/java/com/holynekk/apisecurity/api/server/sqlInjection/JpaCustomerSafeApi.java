package com.holynekk.apisecurity.api.server.sqlInjection;

import com.holynekk.apisecurity.entity.JpaCustomer;
import com.holynekk.apisecurity.repository.JpaCustomerCrudRepository;
import com.holynekk.apisecurity.repository.JpaCustomerDangerDAO;
import com.holynekk.apisecurity.repository.JpaCustomerSafeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@RestController
//@RequestMapping("/api/sqlinejection/danger/v2")
public class JpaCustomerSafeApi {

    @Autowired
    private JpaCustomerCrudRepository repository;

    @Autowired
    private JpaCustomerSafeDAO dao;

    @GetMapping(value = "/customer/{email}")
    public JpaCustomer findCustomerByEmail(@PathVariable(required = true, name="email") String email) {
        List<JpaCustomer> queryResult = repository.findByEmail(email);

        if (queryResult != null && !queryResult.isEmpty()) {
            return queryResult.get(0);
        }

        return null;
    }

    @GetMapping(value = "/customer")
    public List<JpaCustomer> findCustomerByGender(
            @RequestParam(required = true, name = "genderCode") String genderCode
    ) {
        return dao.findByGender(genderCode);
    }
}
