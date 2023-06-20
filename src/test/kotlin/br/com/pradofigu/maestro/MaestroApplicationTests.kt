package br.com.pradofigu.maestro

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext


@SpringBootTest
class MaestroApplicationTests(@Autowired val applicationContext: ApplicationContext) {

    @Test
    fun contextLoads() {
        assertThat(applicationContext).isNotNull();
    }

}
