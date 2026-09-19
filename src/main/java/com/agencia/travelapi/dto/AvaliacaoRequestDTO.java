package com.agencia.travelapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO utilizado para registrar a avaliacao (nota) de um destino.
 * A nota deve estar entre 0 e 5.
 */
public class AvaliacaoRequestDTO {

    @NotNull(message = "A nota e obrigatoria")
    @Min(value = 0, message = "A nota minima e 0")
    @Max(value = 5, message = "A nota maxima e 5")
    private Integer nota;

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }
}
