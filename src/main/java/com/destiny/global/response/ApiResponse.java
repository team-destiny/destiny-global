package com.destiny.global.response;

import java.util.Collections;
import java.util.List;

import org.springframework.validation.BindingResult;

import com.destiny.global.code.ResponseCode;
import com.destiny.global.exception.ErrorResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	private final boolean success;
	private final String code;     // 커스텀 코드 AUTH-200, USER-001 등
	private final String message;
	private final T data;          // 성공 시 응답 데이터
	private final List<ErrorResponse.FieldError> errors; // 실패 시 필드 오류 목록

	public static <T> ApiResponse<T> success(ResponseCode successCode, T data) {
		return ApiResponse.<T>builder()
			.success(true)
			.code(successCode.getCode())
			.message(successCode.getMessage())
			.data(data)
			.build();
	}

	public static <T> ApiResponse<T> success(ResponseCode successCode) {
		return ApiResponse.<T>builder()
			.success(true)
			.code(successCode.getCode())
			.message(successCode.getMessage())
			.build();
	}

	public static ApiResponse<Void> error(ResponseCode errorCode) {
		return ApiResponse.<Void>builder()
			.success(false)
			.code(errorCode.getCode())
			.message(errorCode.getMessage())
			.errors(Collections.emptyList())
			.build();
	}

	public static ApiResponse<Void> error(ResponseCode errorCode, BindingResult bindingResult) {
		return ApiResponse.<Void>builder()
			.success(false)
			.code(errorCode.getCode())
			.message(errorCode.getMessage())
			.errors(ErrorResponse.FieldError.of(bindingResult))
			.build();
	}
}