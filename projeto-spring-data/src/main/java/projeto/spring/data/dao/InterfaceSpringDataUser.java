package projeto.spring.data.dao;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import projeto.spring.data.model.UsuarioSpringData;

@Repository
public interface InterfaceSpringDataUser extends CrudRepository<UsuarioSpringData, Long>{
	
	@Transactional(readOnly = true)
	@Query(value = "select p from UsuarioSpringData p where p.nome like %?1%")
	public List<UsuarioSpringData> buscaPorNome(String nome);
	
	@Lock(LockModeType.READ)
	@Transactional(readOnly = true)
	@Query(value = "select p from UsuarioSpringData p where p.nome = :paramNome")
	public UsuarioSpringData buscaPorNomeIgual(@Param("paramNome") String paramNome);
	
	default <S extends UsuarioSpringData> S saveAtual(S entity) {
		// processa qualquer coisa
		return save(entity);
	}
	
	@Modifying // prepara para uma modificação no banco
	@Transactional // prepara para uma transação no banco
	@Query(value = "delete from UsuarioSpringData p where p.nome = ?1") // executa a query no banco
	public void deletePorNome(String nome);
	
	@Modifying
	@Transactional
	@Query(value = "update UsuarioSpringData p set p.email = ?1 where p.nome = ?2")
	public void updateEmailPorNome(String email, String nome);
}
