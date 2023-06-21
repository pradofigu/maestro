package br.com.pradofigu.maestro.customers.domain

import br.com.caelum.stella.validation.InvalidStateException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.function.Executable

class CPFTest {

    @Test
    fun `A valid cpf should creates an CPF`() {
        val result = CPF("18251178371")
        assertEquals("18251178371", result.number)
    }

    @Test
    fun `An invalid CPF should throws an exception`() {
        assertAll(
            Executable { assertThrows(InvalidStateException::class.java) { CPF("12345678910") } },
            Executable { assertThrows(InvalidStateException::class.java) { CPF("182.511.783-71") } },
            Executable { assertThrows(InvalidStateException::class.java) { CPF(" ") } },
            Executable { assertThrows(InvalidStateException::class.java) { CPF("") } },
            Executable { assertThrows(InvalidStateException::class.java) { CPF("182511783711") } },
        )
    }
}