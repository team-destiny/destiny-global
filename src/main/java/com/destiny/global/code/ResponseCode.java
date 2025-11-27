package com.destiny.global.code;

import org.springframework.http.HttpStatus;

/**
 * 성공 코드(SuccessCode)와 에러 코드(ErrorCode)의 공통 인터페이스
 */
public interface ResponseCode {
	/** HTTP 응답 상태 코드 (200, 400, 500 등) */
	HttpStatus getStatus();

	/** 커스텀 응답 코드 (AUTH-200, USER-001 등) */
	String getCode();

	/** 응답 메시지 (성공 또는 오류 메시지) */
	String getMessage();
}