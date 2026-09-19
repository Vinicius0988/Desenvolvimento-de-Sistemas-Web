package com.agencia.travelapi.config;

import com.agencia.travelapi.dto.AvaliacaoRequestDTO;
import com.agencia.travelapi.dto.DestinoRequestDTO;
import com.agencia.travelapi.model.*;
import com.agencia.travelapi.repository.UsuarioRepository;
import com.agencia.travelapi.service.DestinoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final DestinoService destinoService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    public DataLoader(DestinoService destinoService, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder){
        this.destinoService=destinoService; this.usuarioRepository=usuarioRepository; this.passwordEncoder=passwordEncoder;
    }
    @Override public void run(String... args){
        criarUsuario("admin","admin123",Role.ADMIN);
        criarUsuario("usuario","user123",Role.USER);
        if(destinoService.listarTodos().isEmpty()){
            Destino praia=destinoService.cadastrar(criarDto("Praia do Rosa","Imbituba, Santa Catarina, Brasil","Praia conhecida por suas paisagens naturais e por ser ponto de observacao de baleias.","Praia",890.0));
            Destino chapada=destinoService.cadastrar(criarDto("Chapada Diamantina","Bahia, Brasil","Parque nacional com cachoeiras, grutas e trilhas de ecoturismo.","Ecoturismo",1450.0));
            Destino paris=destinoService.cadastrar(criarDto("Paris","Franca","Cidade conhecida por pontos turisticos historicos, museus e gastronomia.","Internacional",6200.0));
            destinoService.registrarAvaliacao(praia.getId(),criarAvaliacao(5));
            destinoService.registrarAvaliacao(praia.getId(),criarAvaliacao(4));
            destinoService.registrarAvaliacao(chapada.getId(),criarAvaliacao(5));
            destinoService.registrarAvaliacao(paris.getId(),criarAvaliacao(4));
        }
    }
    private void criarUsuario(String username,String password,Role role){
        if(!usuarioRepository.existsByUsername(username)){
            usuarioRepository.save(new Usuario(username,passwordEncoder.encode(password),role));
        }
    }
    private DestinoRequestDTO criarDto(String nome,String localizacao,String descricao,String categoria,Double precoBase){
        DestinoRequestDTO d=new DestinoRequestDTO(); d.setNome(nome);d.setLocalizacao(localizacao);d.setDescricao(descricao);d.setCategoria(categoria);d.setDisponivel(true);d.setPrecoBase(precoBase);return d;
    }
    private AvaliacaoRequestDTO criarAvaliacao(int nota){AvaliacaoRequestDTO d=new AvaliacaoRequestDTO();d.setNota(nota);return d;}
}
