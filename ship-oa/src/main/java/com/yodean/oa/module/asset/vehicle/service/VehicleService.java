package com.yodean.oa.module.asset.vehicle.service;

import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.module.asset.vehicle.entity.Vehicle;
import com.yodean.oa.module.asset.vehicle.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by rick on 7/20/18.
 */
@Service
public class VehicleService extends BaseService<Vehicle> {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    protected JpaRepository<Vehicle, Long> autowired() {
        return vehicleRepository;
    }
}
