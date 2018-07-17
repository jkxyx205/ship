package com.yodean.module.app.repository;

import com.yodean.platform.domain.App;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 7/13/18.
 */
public interface AppRepository extends JpaRepository<App, Long>{
}
