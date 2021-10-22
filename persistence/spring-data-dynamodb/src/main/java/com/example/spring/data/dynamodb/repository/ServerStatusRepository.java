package com.example.spring.data.dynamodb.repository;

import com.example.spring.data.dynamodb.domain.ServerStatus;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface ServerStatusRepository extends CrudRepository<ServerStatus, String> {

}
