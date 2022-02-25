package com.demo.vehicleregistrationnumber.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDto<T> {

    private boolean status;
    private T data;
    private String message;

    private ResponseDto(T data, String message, boolean status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public static <T> ResponseDto<T> success(T data, String message) {
        return new ResponseDto<>(data, message, Boolean.TRUE);
    }

    public static <T> ResponseDto<T> failure(T data, String message) {
        return new ResponseDto<>(data, message, Boolean.FALSE);
    }

}
