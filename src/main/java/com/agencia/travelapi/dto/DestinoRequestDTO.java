package com.agencia.travelapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * DTO utilizado para receber os dados de criacao ou atualizacao de um
 * destino de viagem via requisicao HTTP.
 *
 * Separar o DTO da entidade evita expor detalhes internos do modelo
 * (como a lista de notas de avaliacao) e permite validar os dados de
 * entrada de forma independente da representacao usada internamente.
 */
public class DestinoRequestDTO {

    @NotBlank(message = "O nome do destino e obrigatorio")
    private String nome;

    @NotBlank(message = "A localizacao do destino e obrigatoria")
    private String localizacao;

    private String descricao;

    private String categoria;

    private Boolean disponivel = true;

    @PositiveOrZero(message = "O preco base nao pode ser negativo")
    private Double precoBase;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Double getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(Double precoBase) {
        this.precoBase = precoBase;
    }
}
