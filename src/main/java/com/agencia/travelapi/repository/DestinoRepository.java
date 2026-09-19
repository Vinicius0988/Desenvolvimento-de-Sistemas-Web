package com.agencia.travelapi.repository;
import com.agencia.travelapi.model.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface DestinoRepository extends JpaRepository<Destino, Long> {
    @Query("select d from Destino d where (:nome is null or lower(d.nome) like lower(concat('%', :nome, '%'))) and (:localizacao is null or lower(d.localizacao) like lower(concat('%', :localizacao, '%')))")
    List<Destino> pesquisar(@Param("nome") String nome, @Param("localizacao") String localizacao);
}
