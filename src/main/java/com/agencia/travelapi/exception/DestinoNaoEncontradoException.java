package com.agencia.travelapi.exception;

/**
 * Excecao lancada quando um destino solicitado nao e encontrado
 * no repositorio em memoria.
 */
public class DestinoNaoEncontradoException extends RuntimeException {

    public DestinoNaoEncontradoException(Long id) {
        super("Destino nao encontrado com o id: " + id);
    }
}
