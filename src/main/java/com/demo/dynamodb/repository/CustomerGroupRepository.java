package com.demo.dynamodb.repository;


import com.demo.dynamodb.model.CustomerGroup;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.repository.PagingAndSortingRepository;

@EnableScan
@EnableScanCount
public interface CustomerGroupRepository extends PagingAndSortingRepository<CustomerGroup, String> {
}