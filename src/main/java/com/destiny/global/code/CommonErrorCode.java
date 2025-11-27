package com.destiny.global.code;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ResponseCode {
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERV-000", "서버 내부 오류입니다."),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "SERV-001", "입력값이 올바르지 않습니다."),
	MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "SERV-002", "필수 파라미터가 누락되었습니다."),
	INVALID_JSON_FORMAT(HttpStatus.BAD_REQUEST, "SERV-003", "요청 형식이 올바르지 않습니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "SERV-004", "허용되지 않은 HTTP 메서드입니다."),
	UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "SERV-005", "지원하지 않는 미디어 타입입니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "SERV-006", "인증이 필요합니다."),
	ACCESS_DENIED(HttpStatus.FORBIDDEN, "SERV-007", "접근 권한이 없습니다."),
	DATA_ALREADY_DELETED(HttpStatus.CONFLICT, "SERV-008", "이미 삭제된 유저입니다."),
	DATA_NOT_DELETED(HttpStatus.BAD_REQUEST, "SERV-009", "삭제되지 않은 유저입니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;
}