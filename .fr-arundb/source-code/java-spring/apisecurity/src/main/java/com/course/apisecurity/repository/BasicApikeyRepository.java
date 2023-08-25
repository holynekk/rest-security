package com.course.apisecurity.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.course.apisecurity.entity.BasicApikey;

@Repository
public interface BasicApikeyRepository extends CrudRepository<BasicApikey, Integer> {

	Optional<BasicApikey> findByApikeyAndExpiredAtAfter(String apikey, LocalDateTime now);

}
