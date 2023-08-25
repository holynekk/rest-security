package com.course.apisecurity.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.course.apisecurity.entity.BasicAuthUser;

@Repository
public interface BasicAuthUserRepository extends CrudRepository<BasicAuthUser, Integer> {

	Optional<BasicAuthUser> findByUsername(String encryptedUsername);

}