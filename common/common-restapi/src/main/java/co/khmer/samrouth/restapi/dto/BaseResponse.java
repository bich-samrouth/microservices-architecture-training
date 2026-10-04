package co.khmer.samrouth.restapi.dto;

import java.time.Instant;

public record BaseResponse<T>(
        boolean success,
        String code,
        String message,
        T payload,
        Instant timestamp) {

    public static <T> BaseResponse<T> ok() {
        return new BaseResponse<>(true, "SUCCESS", null, null, Instant.now());
    }

    public static <T> BaseResponse<T> error(String code, String message) {
        return new BaseResponse<>(false, code, message, null, Instant.now());
    }

    public BaseResponse<T> payload(T payload) {
        return new BaseResponse<>(success, code, message, payload, timestamp);
    }

    public BaseResponse<T> message(String message) {
        return new BaseResponse<>(success, code, message, payload, timestamp);
    }

    public BaseResponse<T> code(String code) {
        return new BaseResponse<>(success, code, message, payload, timestamp);
    }
}
