package com.yodean.platform.user.repository;

import com.yodean.platform.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 7/16/18.
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
