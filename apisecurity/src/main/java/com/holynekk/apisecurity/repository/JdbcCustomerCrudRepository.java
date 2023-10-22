package com.holynekk.apisecurity.repository;

import com.holynekk.apisecurity.entity.JdbcCustomer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@Repository
public interface JdbcCustomerCrudRepository extends CrudRepository<JdbcCustomer, Integer> {
    List<JdbcCustomer> findByEmail(String email);

    List<JdbcCustomer> findByGender(String gender);

    @Query("CALL update_jdbc_customer(:customerId, :newFullName)")
    void updateCustomerFullName(@Param("customer_id") int customerId, @Param("newFullName") String newFullName);
}
