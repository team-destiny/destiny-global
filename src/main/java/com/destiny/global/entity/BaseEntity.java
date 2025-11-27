package com.destiny.global.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.Where;
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
@Where(clause = "deleted_at IS NULL")
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@CreatedBy
	private Long createdBy;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@LastModifiedBy
	private Long updatedBy;

	private LocalDateTime deletedAt;

	private Long deletedBy;

	public void markDeleted(Long userId) {
		if (isDeleted()) {
			throw new BizException(CommonErrorCode.DATA_ALREADY_DELETED);
		}
		this.deletedAt = LocalDateTime.now();
		this.deletedBy = userId;
	}

	public void restore(Long userId) {
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
