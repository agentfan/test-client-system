package com.client.listener

import com.client.domain.BaseResource
import org.springframework.data.domain.AuditorAware
import java.util.*
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

class BaseResourceListener : AuditorAware<UUID> {
	// TODO need to get current userId
	override fun getCurrentAuditor(): Optional<UUID> {
		return Optional.empty()
	}

	@PrePersist
	fun <T : BaseResource> touchForCreate(domain: T) {
		val now = Date()
		val auditor = currentAuditor.orElse(null)?.toString()

		domain.id = UUID.randomUUID().toString()
		domain.entity.history.createdBy = auditor
		domain.entity.history.createdAt = now
		domain.entity.history.updatedBy = auditor
		domain.entity.history.updatedAt = now
}

	@PreUpdate
	fun <T : BaseResource> touchForUpdate(domain: T) {
		val now = Date()

		domain.entity.history.updatedBy = currentAuditor.orElse(null)?.toString()
		domain.entity.history.updatedAt = now
	}
}
