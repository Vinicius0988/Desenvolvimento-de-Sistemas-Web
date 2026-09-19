package com.agencia.travelapi.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "destinos")
public class Destino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String localizacao;
    @Column(length = 2000)
    private String descricao;
    private String categoria;
    @Column(nullable = false)
    private Boolean disponivel = true;
    private Double precoBase;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "destino_avaliacoes", joinColumns = @JoinColumn(name = "destino_id"))
    @Column(name = "nota", nullable = false)
    private List<Integer> notasAvaliacoes = new ArrayList<>();

    @Column(nullable = false)
    private Double mediaAvaliacoes = 0.0;
    @Column(nullable = false)
    private Integer totalAvaliacoes = 0;

    public Destino() {
    }

    public void registrarAvaliacao(int nota) {
        notasAvaliacoes.add(nota);
        totalAvaliacoes = notasAvaliacoes.size();
        mediaAvaliacoes = notasAvaliacoes.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String v) {
        nome = v;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String v) {
        localizacao = v;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String v) {
        descricao = v;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String v) {
        categoria = v;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean v) {
        disponivel = v;
    }

    public Double getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(Double v) {
        precoBase = v;
    }

    public List<Integer> getNotasAvaliacoes() {
        return notasAvaliacoes;
    }

    public void setNotasAvaliacoes(List<Integer> v) {
        notasAvaliacoes = v;
    }

    public Double getMediaAvaliacoes() {
        return mediaAvaliacoes;
    }

    public void setMediaAvaliacoes(Double v) {
        mediaAvaliacoes = v;
    }

    public Integer getTotalAvaliacoes() {
        return totalAvaliacoes;
    }

    public void setTotalAvaliacoes(Integer v) {
        totalAvaliacoes = v;
    }
}
