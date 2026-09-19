package com.agencia.travelapi.controller;

import com.agencia.travelapi.dto.AvaliacaoRequestDTO;
import com.agencia.travelapi.dto.DestinoRequestDTO;
import com.agencia.travelapi.model.Destino;
import com.agencia.travelapi.service.DestinoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * Controller REST responsavel por expor os endpoints de gerenciamento
 * de destinos de viagem.
 *
 * A camada de controller tem responsabilidade unica de receber a
 * requisicao HTTP, delegar a execucao para a camada de service e
 * devolver a resposta adequada, sem conter regras de negocio.
 */
@RestController
@RequestMapping("/api/destinos")
public class DestinoController {

    private final DestinoService destinoService;

    public DestinoController(DestinoService destinoService) {
        this.destinoService = destinoService;
    }

    // Cadastrar um novo destino
    // POST /api/destinos
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Destino> cadastrar(@Valid @RequestBody DestinoRequestDTO dto) {
        Destino destinoCriado = destinoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(destinoCriado);
    }

    // Listar todos os destinos, ou pesquisar por nome/localizacao via query params
    // GET /api/destinos
    // GET /api/destinos?nome=praia
    // GET /api/destinos?localizacao=bahia
    @GetMapping
    public ResponseEntity<List<Destino>> listarOuPesquisar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String localizacao) {

        if (nome == null && localizacao == null) {
            return ResponseEntity.ok(destinoService.listarTodos());
        }
        return ResponseEntity.ok(destinoService.pesquisar(nome, localizacao));
    }

    // Visualizar detalhes de um destino especifico
    // GET /api/destinos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Destino> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(destinoService.buscarPorId(id));
    }

    // Atualizar informacoes de um destino existente
    // PUT /api/destinos/{id}
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Destino> atualizar(@PathVariable Long id,
                                              @Valid @RequestBody DestinoRequestDTO dto) {
        return ResponseEntity.ok(destinoService.atualizar(id, dto));
    }

    // Registrar uma avaliacao (nota de 0 a 5) e recalcular a media do destino
    // PATCH /api/destinos/{id}/avaliacoes
    @PatchMapping("/{id}/avaliacoes")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Destino> registrarAvaliacao(@PathVariable Long id,
                                                        @Valid @RequestBody AvaliacaoRequestDTO dto) {
        return ResponseEntity.ok(destinoService.registrarAvaliacao(id, dto));
    }

    // Excluir um destino
    // DELETE /api/destinos/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        destinoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
