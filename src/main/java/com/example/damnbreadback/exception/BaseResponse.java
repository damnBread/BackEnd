package com.example.damnbreadback.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL) // This annotation ensures that null fields are excluded from the JSON output
public class BaseResponse {
    @JsonProperty("status")
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Object data;

    public BaseResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static BaseResponse ofFail(int status, String message, Object data) {
        return new BaseResponse(status, message, data);
    }
}
