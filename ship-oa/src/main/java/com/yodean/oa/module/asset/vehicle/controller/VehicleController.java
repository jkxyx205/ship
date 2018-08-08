package com.yodean.oa.module.asset.vehicle.controller;

import com.yodean.oa.common.core.controller.BaseController;
import com.yodean.oa.module.asset.vehicle.entity.Vehicle;
import com.yodean.oa.module.asset.vehicle.service.VehicleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rick on 7/20/18.
 */
@RestController
@RequestMapping("/vehicles")
public class VehicleController extends BaseController<Vehicle, VehicleService> {
}
