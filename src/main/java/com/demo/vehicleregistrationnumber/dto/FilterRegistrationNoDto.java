package com.demo.vehicleregistrationnumber.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class FilterRegistrationNoDto {

    @NotNull(message = "availableFrom cannot be null")
    @Min(value = 0L, message = "The availableFrom should be a positive number")
    private Long availableFrom;

    @NotNull(message = "availableTo cannot be null")
    @Min(value = 0L, message = "The availableTo should be a positive number")
    private Long availableTo;

    private List<Long> mustHaveDigits;

    private List<@NotNull(message = "divisibleBy cannot be null")
    @Min(value = 1L, message = "The divisibleBy should be a natural number") Long> divisibleBy;

    private Long matchFactor;

    private Boolean digitsInRaisingOrder;

}
