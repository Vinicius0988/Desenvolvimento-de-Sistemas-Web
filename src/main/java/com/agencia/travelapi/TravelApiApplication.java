package com.agencia.travelapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal responsavel por inicializar a aplicacao Spring Boot.
 *
 * Esta API tem como objetivo o gerenciamento de destinos de viagem de uma
 * agencia de turismo, permitindo cadastro, consulta, atualizacao, avaliacao
 * e remocao de destinos. Os dados sao mantidos em memoria nesta primeira
 * versao do projeto, sem integracao com banco de dados.
 */
@SpringBootApplication
public class TravelApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelApiApplication.class, args);
    }

}
