package com.destiny.global.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.destiny.global.code.CommonErrorCode;
import com.destiny.global.exception.BizException;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@CreatedBy
	private UUID createdBy;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@LastModifiedBy
	private UUID updatedBy;

	private LocalDateTime deletedAt;

	private UUID deletedBy;

	public void markDeleted(UUID userId) {
		if (isDeleted()) {
			throw new BizException(CommonErrorCode.DATA_ALREADY_DELETED);
		}
		this.deletedAt = LocalDateTime.now();
		this.deletedBy = userId;
	}

	public void restore(UUID userId) {
		if (!isDeleted()) {
			throw new BizException(CommonErrorCode.DATA_NOT_DELETED);
		}
		this.deletedAt = null;
		this.deletedBy = null;
		this.updatedAt = LocalDateTime.now();
		this.updatedBy = userId;
	}

	public boolean isDeleted() {
		return deletedAt != null;
	}
}
