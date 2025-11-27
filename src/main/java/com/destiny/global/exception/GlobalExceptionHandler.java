package com.destiny.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.destiny.global.code.CommonErrorCode;
import com.destiny.global.response.ApiResponse;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// 비즈니스 로직에서 직접 던진 예외 처리
	@ExceptionHandler(BizException.class)
	public ResponseEntity<ApiResponse<?>> handleBizError(BizException exception) {
		return ResponseEntity
			.status(exception.getResponseCode().getStatus())
			.body(ApiResponse.error(exception.getResponseCode()));
	}

	// @Valid 실패 시 발생 (DTO의 필드 제약 조건 위반 등)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<?>> handleValidationError(MethodArgumentNotValidException exception) {
		return ResponseEntity
			.badRequest()
			.body(ApiResponse.error(CommonErrorCode.INVALID_INPUT_VALUE, exception.getBindingResult()));
	}

	// @RequestParam 등에서 필수 파라미터가 누락된 경우
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiResponse<?>> handleMissingServletRequestParameter(MissingServletRequestParameterException exception) {
		return ResponseEntity
			.badRequest()
			.body(ApiResponse.error(CommonErrorCode.MISSING_PARAMETER));
	}

	// JSON 파싱 실패 시 (예: 잘못된 형식의 요청 본문)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse<?>> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
		return ResponseEntity
			.badRequest()
			.body(ApiResponse.error(CommonErrorCode.INVALID_JSON_FORMAT));
	}

	// 유효성 검사 실패 시 (ex. PathVariable, RequestParam에 대한 제약 조건 위반)
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponse<?>> handleConstraintViolation(ConstraintViolationException exception) {
		return ResponseEntity
			.badRequest()
			.body(ApiResponse.error(CommonErrorCode.INVALID_INPUT_VALUE));
	}

	// 허용되지 않은 HTTP Method 호출 (예: GET만 허용되는데 POST 요청)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse<?>> handleMethodNotSupported(HttpRequestMethodNotSupportedException exception) {
		return ResponseEntity
			.status(CommonErrorCode.METHOD_NOT_ALLOWED.getStatus()) // 4. HTTP Status 코드도 CommonErrorCode에서 가져오도록 수정
			.body(ApiResponse.error(CommonErrorCode.METHOD_NOT_ALLOWED));
	}

	// 지원하지 않는 Content-Type 요청 (예: XML 요청이 들어온 경우)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ApiResponse<?>> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception) {
		return ResponseEntity
			.status(CommonErrorCode.UNSUPPORTED_MEDIA_TYPE.getStatus()) // 4. HTTP Status 코드도 CommonErrorCode에서 가져오도록 수정
			.body(ApiResponse.error(CommonErrorCode.UNSUPPORTED_MEDIA_TYPE));
	}

	// 예상치 못한 모든 예외 (마지막 방어선)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleUnexpectedException(Exception exception) {
		// 로깅 필수
		exception.printStackTrace();
		return ResponseEntity
			.internalServerError()
			.body(ApiResponse.error(CommonErrorCode.INTERNAL_SERVER_ERROR));
	}
}