package br.com.pradofigu.maestro.persistence.adapter

import br.com.pradofigu.maestro.persistence.repository.CategoryRepository
import br.com.pradofigu.maestro.usecase.model.Category
import br.com.pradofigu.maestro.usecase.persistence.CategoryDataAccessPort
import kotlinx.coroutines.runBlocking
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CategoryAdapterImpl(
    private val categoryRepository: CategoryRepository
): CategoryDataAccessPort {

    override suspend fun findAll(): List<Category> = runBlocking {
        categoryRepository.findAll()
    }.map { it.toModel() }

    override suspend fun findBy(id: UUID): Category? = runBlocking {
        categoryRepository.findByIdOrNull(id)?.toModel()
    }

    override suspend fun save(category: Category): Category = runBlocking {
        categoryRepository.save(category.toEntity())
    }.toModel()

    override suspend fun delete(id: UUID) = runBlocking { categoryRepository.deleteById(id) }
}