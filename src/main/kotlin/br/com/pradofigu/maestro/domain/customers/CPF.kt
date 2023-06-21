package br.com.pradofigu.maestro.domain.customers

import br.com.caelum.stella.validation.CPFValidator

data class CPF(val number: String) {

    init {
        CPFValidator().assertValid(number)
    }

}