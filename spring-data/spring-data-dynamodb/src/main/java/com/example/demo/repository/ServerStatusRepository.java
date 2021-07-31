package com.example.demo.repository;

import com.example.demo.domain.ServerStatus;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface ServerStatusRepository extends CrudRepository<ServerStatus, String> {

}
