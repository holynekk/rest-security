package com.holynekk.apisecurity.repository;

import com.holynekk.apisecurity.entity.JpaCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

//@Component
public class JpaCustomerDangerDAO {

    @Autowired
    private EntityManager entityManager;

    public List<JpaCustomer> findByGender(String gender) {
        String jpql = "FROM JpaCustomer WHERE gender = '" + gender + "'";
        Query query = entityManager.createQuery(jpql);

        return query.getResultList();
    }
}
