package com.agencia.travelapi.service;

import com.agencia.travelapi.dto.AvaliacaoRequestDTO;
import com.agencia.travelapi.dto.DestinoRequestDTO;
import com.agencia.travelapi.exception.DestinoNaoEncontradoException;
import com.agencia.travelapi.model.Destino;
import com.agencia.travelapi.repository.DestinoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DestinoService {
    private final DestinoRepository destinoRepository;
    public DestinoService(DestinoRepository destinoRepository){this.destinoRepository=destinoRepository;}

    @Transactional
    public Destino cadastrar(DestinoRequestDTO dto){Destino d=new Destino(); aplicarDadosDto(d,dto); return destinoRepository.save(d);}
    @Transactional(readOnly=true)
    public List<Destino> listarTodos(){return destinoRepository.findAll();}
    @Transactional(readOnly=true)
    public List<Destino> pesquisar(String nome,String localizacao){
        return destinoRepository.pesquisar(blankToNull(nome),blankToNull(localizacao));
    }
    @Transactional(readOnly=true)
    public Destino buscarPorId(Long id){return destinoRepository.findById(id).orElseThrow(()->new DestinoNaoEncontradoException(id));}
    @Transactional
    public Destino atualizar(Long id,DestinoRequestDTO dto){Destino d=buscarPorId(id); aplicarDadosDto(d,dto); return destinoRepository.save(d);}
    @Transactional
    public Destino registrarAvaliacao(Long id,AvaliacaoRequestDTO dto){Destino d=buscarPorId(id); d.registrarAvaliacao(dto.getNota()); return destinoRepository.save(d);}
    @Transactional
    public void excluir(Long id){if(!destinoRepository.existsById(id)) throw new DestinoNaoEncontradoException(id); destinoRepository.deleteById(id);}
    private void aplicarDadosDto(Destino d,DestinoRequestDTO dto){
        d.setNome(dto.getNome()); d.setLocalizacao(dto.getLocalizacao()); d.setDescricao(dto.getDescricao());
        d.setCategoria(dto.getCategoria()); d.setDisponivel(dto.getDisponivel()==null?true:dto.getDisponivel()); d.setPrecoBase(dto.getPrecoBase());
    }
    private String blankToNull(String s){return s==null||s.isBlank()?null:s;}
}
