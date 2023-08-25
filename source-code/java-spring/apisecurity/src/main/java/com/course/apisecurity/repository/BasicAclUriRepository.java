package com.course.apisecurity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.course.apisecurity.entity.BasicAclUri;

@Repository
public interface BasicAclUriRepository extends CrudRepository<BasicAclUri, Integer> {

}
