package com.yodean.oa.module.asset.vehicle.repository;

import com.yodean.oa.module.asset.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 7/20/18.
 */
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
