package com.demo.vehicleregistrationnumber.service;

import com.demo.vehicleregistrationnumber.dto.FilterRegistrationNoDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class VehicleRegistrationNumberService {

    public List<Long> filterLuckyRegistrationNo(FilterRegistrationNoDto filterRegistrationNoDto) {
        Long availableFrom = filterRegistrationNoDto.getAvailableFrom();
        Long availableTo = filterRegistrationNoDto.getAvailableTo();
        if(availableFrom>=availableTo) {
            throw new ValidationException("availableFrom cannot be greater than or equal to availableTo");
        }

        List<Long> divisibleByList = filterRegistrationNoDto.getDivisibleBy();
        Long registrationNo = availableFrom;
        List<Long> resultantRegistrationNos = new ArrayList<>();
        if(Objects.nonNull(divisibleByList) && !divisibleByList.isEmpty()) {
            while(registrationNo <= availableTo) {
                long tempRegistrationNo = registrationNo;
                long finalTempRegistrationNo = tempRegistrationNo;
                boolean divisibilityFilterSuccess = divisibleByList.stream().allMatch(divisibleBy -> finalTempRegistrationNo % divisibleBy == 0);
                if(divisibilityFilterSuccess) {
                    List<Long> mustHaveDigits = filterRegistrationNoDto.getMustHaveDigits();
                    Long matchFactor = filterRegistrationNoDto.getMatchFactor();

                    if(Objects.nonNull(matchFactor) && Objects.nonNull(mustHaveDigits) && matchFactor > mustHaveDigits.size()) {
                        throw new ValidationException("matchFactor cannot be more than the mustHaveDigits");
                    }

                    boolean matchedDigitFilterSuccess = Boolean.FALSE;
                    if(Objects.nonNull(mustHaveDigits) && !mustHaveDigits.isEmpty()) {
                        long matchedDigits = mustHaveDigits.stream()
                                .filter(mustHaveDigit -> Long.toString(finalTempRegistrationNo).contains(mustHaveDigit.toString())).count();
                        log.info("registrationNo: {} & matchedDigits: {}", tempRegistrationNo, matchedDigits);

                        if((Objects.nonNull(matchFactor) && matchedDigits>=matchFactor) || (matchedDigits >= mustHaveDigits.size())) {
                            matchedDigitFilterSuccess = Boolean.TRUE;
                        }
                    } else {
                        matchedDigitFilterSuccess = Boolean.TRUE;
                    }

                    if(matchedDigitFilterSuccess) {
                        resultantRegistrationNos.add(registrationNo);

                        Boolean digitsInRaisingOrder = filterRegistrationNoDto.getDigitsInRaisingOrder();
                        log.info("digitsInRaisingOrder: {}", digitsInRaisingOrder);
                        if(Objects.nonNull(digitsInRaisingOrder) && Boolean.TRUE.equals(digitsInRaisingOrder)) {
                            boolean raisingOrder = true;
                            long currentDigit = tempRegistrationNo % 10;
                            tempRegistrationNo = tempRegistrationNo / 10;
                            while (tempRegistrationNo > 0) {
                                if (currentDigit <= tempRegistrationNo % 10) {
                                    raisingOrder = false;
                                    break;
                                }
                                currentDigit = tempRegistrationNo % 10;
                                tempRegistrationNo = tempRegistrationNo / 10;
                            }

                            log.info("raisingOrder: {}", raisingOrder);
                            if(!raisingOrder) {
                                resultantRegistrationNos.remove(registrationNo);
                            }
                        }
                    }
                }
                registrationNo++;
            }
        }
        return resultantRegistrationNos;
    }
}
