package com.holynekk.apisecurity.repository;

import com.holynekk.apisecurity.entity.BasicAclUri;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicAclUriRepository extends CrudRepository<BasicAclUri, Integer> {
}
