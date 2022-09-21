package com.client.service

import com.client.domain.BaseResource
import com.client.repository.CommonRepository
import com.github.ichanzhar.rsql.JpaRsqlVisitor
import cz.jirutka.rsql.parser.ast.Node
import com.github.ichanzhar.rsql.utils.RsqlParserFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import java.util.*

abstract class AbstractService<E: BaseResource, R: CommonRepository<E>>(protected val repository: R): CommonService<E> {

	private fun BaseResource.setDeleted() {
		this.entity.state = "deleted"
	}

	private fun BaseResource?.isDeleted(): Boolean {
		return this != null && this.entity.state == "deleted"
	}

	override fun save(domain: E): E {
		return repository.save(domain)
	}

	override fun delete(domain: E) {
		domain.setDeleted()
		save(domain)
	}

	override fun getById(id: String): E? {
		val domain: E? = repository.findByIdOrNull(id)
		return if (!domain.isDeleted()) domain else null
	}

	override fun update(domain: E): E {
		return repository.save(domain)
	}

	override fun getAll(pageable: Pageable, query: String?): Page<E> {
		return if (query.isNullOrBlank()) {
			getAll(pageable)
		} else {
			doGetAll(pageable, query)
		}
	}

	override fun saveAll(domains: List<E>): List<E> {
		return repository.saveAll(domains)
	}

	override fun deleteAll(domains: List<E>) {
		domains.forEach { it.setDeleted() }
		saveAll(domains)
	}

	override fun getByIds(ids: List<String>): List<E> {
		return repository.findAllById(ids).filter{ !it.isDeleted() }
	}

	private fun getAll(pageable: Pageable): Page<E> {
		val rootNode: Node = RsqlParserFactory.instance().parse("entity.state=isNull=true,entity.state!=deleted")
		val spec: Specification<E> = rootNode.accept(JpaRsqlVisitor())
		return repository.findAll(spec, pageable)
	}

	private fun doGetAll(pageable: Pageable, query: String): Page<E> {
		val rootNode: Node = RsqlParserFactory.instance().parse("(entity.state=isNull=true,entity.state!=deleted);$query")
		val spec: Specification<E> = rootNode.accept(JpaRsqlVisitor())
		return repository.findAll(spec, pageable)
	}
}
