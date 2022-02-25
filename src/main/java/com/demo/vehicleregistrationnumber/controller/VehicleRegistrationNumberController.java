package com.demo.vehicleregistrationnumber.controller;

import com.demo.vehicleregistrationnumber.dto.FilterRegistrationNoDto;
import com.demo.vehicleregistrationnumber.dto.ResponseDto;
import com.demo.vehicleregistrationnumber.service.VehicleRegistrationNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class VehicleRegistrationNumberController {

    @Autowired
    private VehicleRegistrationNumberService vehicleRegistrationNumberService;

    @PostMapping("/filter-lucky-vehicle-no")
    public ResponseDto<List<Long>> filterLuckyRegistrationNo(@Valid @RequestBody FilterRegistrationNoDto filterRegistrationNoDto) {
        List<Long> filteredRegistrationNo = vehicleRegistrationNumberService.filterLuckyRegistrationNo(filterRegistrationNoDto);
        return filteredRegistrationNo.isEmpty()
                ? ResponseDto.failure(null, "No vehicle registration No. found as per your filters. Please go ahead with the No. given by RTO")
                : ResponseDto.success(filteredRegistrationNo, "Successfully fetched vehicle registration numbers as per your filters");
    }

}
