package testes;

import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.db.ConnHSQLDB;
import com.entidades.Pessoa;
import dao.PessoaDAO;

public class TestsPessoaDAO {

	PessoaDAO p;
	Pessoa a;
	Connection connection;

	@Before
	public void setUp() throws Exception {
		p = new PessoaDAO();
	}

	@Test
	public void testAlterar() {
		fail("Not yet implemented");
	}

	@Test
	public void testExcluir() {
		fail("Not yet implemented");
	}

	@Test
	public void testConectar() {
		connection = new ConnHSQLDB().conectar();
		try {
			assertTrue(connection.isValid(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testListAll() {
		List<Pessoa> listaPessoa = this.p.listAll();
		assertEquals(2, listaPessoa.size());
		assertEquals("pedro", listaPessoa.get(0).getNome());
		assertEquals("joao", listaPessoa.get(1).getNome());
		// assertEquals("jose", listaPessoa.get(2).getNome());
	}

	@Test
	public void testFindByID() {
		Pessoa pessoa = this.p.findByID(13);
		assertEquals(13, pessoa.getId());
		System.out.println(pessoa.getNome());
	}

	
	@Test
	public void testInserir() {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(14);
		pessoa.setNome("Igor");
		pessoa.setDataNasc(null);
		pessoa.setPeso(45);

		System.out.println(pessoa.getId());
		System.out.println(pessoa.getNome());
		System.out.println(pessoa.getDataNasc());
		System.out.println(pessoa.getPeso());

		assertEquals("Sucesso ao add na tabela do banco", 1, p.inserir(pessoa));

	}
	 
}
