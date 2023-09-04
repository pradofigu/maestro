package br.com.pradofigu.maestro.persistence.repository

import br.com.pradofigu.maestro.persistence.entity.CategoryEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CategoryRepository: CrudRepository<CategoryEntity, UUID>