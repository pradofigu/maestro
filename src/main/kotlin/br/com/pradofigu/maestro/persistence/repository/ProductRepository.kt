package br.com.pradofigu.maestro.persistence.repository

import br.com.pradofigu.maestro.persistence.entity.ProductEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProductRepository: CrudRepository<ProductEntity, UUID> {

    fun findByCategoryId(categoryId: UUID): List<ProductEntity>
}