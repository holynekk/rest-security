package com.course.apisecurity.repository;

import org.springframework.data.repository.CrudRepository;

import com.course.apisecurity.entity.JpaCustomer;

public interface JpaCustomerCrudRepository extends CrudRepository<JpaCustomer, Integer> {

//	List<JpaCustomer> findByEmail(String email);

}
