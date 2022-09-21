package com.client.domain

import com.client.listener.BaseResourceListener
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@MappedSuperclass
@EntityListeners(BaseResourceListener::class)
open class BaseResource {

	@JsonIgnore
	@Id
	open var id: String? = null
		set(value) {
			field = value
			identity.id = value
			entity.entityId = value
		}

	@Embedded
	var identity: Identity = Identity()

	@OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id")
	var entity: ResourceEntity = ResourceEntity()
}
