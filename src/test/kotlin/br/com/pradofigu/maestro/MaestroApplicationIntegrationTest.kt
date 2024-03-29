package br.com.pradofigu.maestro

import br.com.pradofigu.maestro.application.MaestroApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext


@SpringBootTest(classes = [MaestroApplication::class])
class MaestroApplicationIntegrationTest(@Autowired val applicationContext: ApplicationContext) {

    @Test
    fun `When load application should load contexts`() {
        assertThat(applicationContext).isNotNull()
    }

}
