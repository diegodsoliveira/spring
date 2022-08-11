package projeto.spring.data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.checkerframework.checker.units.qual.C;
import org.hibernate.internal.build.AllowSysOut;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import projeto.spring.data.dao.InterfaceSpringDataUser;
import projeto.spring.data.dao.InterfaceTelefone;
import projeto.spring.data.model.Telefone;
import projeto.spring.data.model.UsuarioSpringData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring-config.xml"})
public class AppSpringDataTest {
	
	@Autowired
	private InterfaceSpringDataUser interfaceSpringDataUser;
	
	@Autowired
	private InterfaceTelefone interfaceTelefone;
	
	@Test
	public void testeInsert() {

		UsuarioSpringData usuarioSpringData = new UsuarioSpringData();
		
		usuarioSpringData.setEmail("antonio@gmail.com");
		usuarioSpringData.setIdade(26);
		usuarioSpringData.setLogin("teste 123");
		usuarioSpringData.setSenha("123");
		usuarioSpringData.setNome("Antonio");
		
		interfaceSpringDataUser.save(usuarioSpringData);
		
		System.out.println("Usuarios cadastrados -> " + interfaceSpringDataUser.count());
		
	}
	
	@Test
	public void testeInsertTelefone() {
		Telefone telefone = new Telefone();
		Optional<UsuarioSpringData> usuarioSpringData = interfaceSpringDataUser.findById(4L);
		
		telefone.setNumero("31231242");
		telefone.setTipo("casa");
		telefone.setUsuarioSpringData(usuarioSpringData.get());
		
		interfaceTelefone.save(telefone);
	}
	
	@Test
	public void testeConsultaPorId() {

		Optional<UsuarioSpringData> usuarioSpringData = interfaceSpringDataUser.findById(4L);
		
		System.out.println("Cadastro encontrado -> " + usuarioSpringData.get().getNome());
		
	}

	@Test
	public void testeConsultaTodos() {

		Iterable<UsuarioSpringData> usuarioSpringData = interfaceSpringDataUser.findAll();
		
		usuarioSpringData.forEach(c -> System.out.println("Cadastro encontrado -> "
				+ c.getNome() + ", " + c.getEmail() + ", " + c.getIdade() + ", " + c.getLogin()));
		
	}
	
	@Test
	public void testeUpdtateBanco() {

		Optional<UsuarioSpringData> usuarioSpringData = interfaceSpringDataUser.findById(3L);
		
		UsuarioSpringData data = usuarioSpringData.get();
		
		data.setNome("Update Spring data");
		
		interfaceSpringDataUser.save(data);
		
	}
	
	@Test
	public void testeDelete() {
		interfaceSpringDataUser.deleteById(3L);
	}
	
	@Test
	public void testeConsultaPorNome() {

		List<UsuarioSpringData> lista = interfaceSpringDataUser.buscaPorNome("a");
		
		lista.forEach(c -> System.out.println("Cadastro encontrado -> "
				+ c.getNome() + ", " + c.getEmail() + ", " + c.getIdade() + ", " + c.getLogin()));
	}
	
	@Test
	public void testeConsultaPorNomeIgual() {

		UsuarioSpringData usuarioSpringData = interfaceSpringDataUser.buscaPorNomeIgual("Camila");
		
		System.out.println("Cadastro encontrado -> "
				+ usuarioSpringData.getNome() + ", " + usuarioSpringData.getEmail() + ", " 
				+ usuarioSpringData.getIdade() + ", " + usuarioSpringData.getLogin());
	}
	
	@Test
	public void testeDeletePorNome() {

		interfaceSpringDataUser.deletePorNome("Camila");
		
	}
	
	@Test
	public void testeUpdateEmailPorNome() {

		interfaceSpringDataUser.updateEmailPorNome("heitor@gmail.com", "Heitor");
		
	}

}
