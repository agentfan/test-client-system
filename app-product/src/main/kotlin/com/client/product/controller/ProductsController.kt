package com.client.product.controller

import com.client.product.domain.Product
import com.client.product.service.ProductsService
import com.client.product.controller.api.ProductsApi
import com.client.controller.AbstractController
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@javax.annotation.Generated(value = ["org.openapitools.codegen.CodeCodegen"])

@RestController
class ProductsController(service: ProductsService)
    : AbstractController<Product, ProductsService>(service), ProductsApi {


    override fun productsCreateProduct(
			@PathVariable("productLineId") productLineId: String,
			@RequestBody product: Product): ResponseEntity<Product> {
        return super.create(productLineId, product)
    }

    override fun productsDeleteProduct(
			@PathVariable("productLineId") productLineId: String,
			@PathVariable("productId") productId: String): ResponseEntity<Product> {
        return super.delete(productLineId, productId)
    }

    override fun productsGetProduct(
			@PathVariable("productLineId") productLineId: String,
			@PathVariable("productId") productId: String,
			@RequestHeader(value = "partyId", required = false) partyId: String?,
			@RequestHeader(value = "type", required = false) type: String?,
			@RequestHeader(value = "critical", required = false) critical: Boolean?,
			@RequestHeader(value = "rank", required = false) rank: Int?,
			@RequestHeader(value = "estimation", required = false) estimation: String?): ResponseEntity<Product> {
        return super.getById(productLineId, productId)
    }

    override fun productsGetProductList(
			@PathVariable("productLineId") productLineId: String,
			@RequestParam(value = "search", required = false) search: String?,
			@RequestHeader(value = "partyId", required = false) partyId: String?,
			@RequestHeader(value = "type", required = false) type: String?,
			@RequestHeader(value = "critical", required = false) critical: Boolean?,
			@RequestHeader(value = "rank", required = false) rank: Int?,
			@RequestHeader(value = "estimation", required = false) estimation: String?,
			@PageableDefault(value=0, size = 50, sort=["id"], direction = Sort.Direction.ASC) page: Pageable): ResponseEntity<Page<Product>> {
        return getAll(productLineId, search, page)
    }

    override fun productsModifyProduct(
			@PathVariable("productLineId") productLineId: String,
			@PathVariable("productId") productId: String,
			@RequestBody product: Product,
			@RequestHeader(value = "partyId", required = false) partyId: String?,
			@RequestHeader(value = "rank", required = false) rank: Int?): ResponseEntity<Product> {
        return super.modify(productLineId, productId, product)
    }

    override fun productsUpdateProduct(
			@PathVariable("productLineId") productLineId: String,
			@PathVariable("productId") productId: String,
			@RequestBody product: Product,
			@RequestHeader(value = "partyId", required = false) partyId: String?): ResponseEntity<Product> {
        return super.update(productLineId, productId, product)
    }

}
