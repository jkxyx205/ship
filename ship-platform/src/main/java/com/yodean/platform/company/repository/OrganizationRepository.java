package com.yodean.platform.company.repository;

import com.yodean.platform.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 7/16/18.
 */
public interface OrganizationRepository extends JpaRepository<Department, Long> {
}
