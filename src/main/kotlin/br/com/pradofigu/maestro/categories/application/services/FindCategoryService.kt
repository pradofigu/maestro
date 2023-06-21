package br.com.pradofigu.maestro.categories.application.services

import br.com.pradofigu.maestro.categories.application.ports.`in`.FindCategoryInPort
import br.com.pradofigu.maestro.categories.application.ports.out.CreateCategoryOutPort
import br.com.pradofigu.maestro.categories.application.ports.out.FindCategoryOutPort
import br.com.pradofigu.maestro.categories.domain.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class FindCategoryService(@Autowired private val repository: FindCategoryOutPort) : FindCategoryInPort {

    override fun findBy(id: UUID): Category? {
        return repository.findBy(id)
    }


}
