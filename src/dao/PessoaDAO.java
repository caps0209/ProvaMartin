package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.db.ConnHSQLDB;
import com.entidades.Pessoa;

public class PessoaDAO extends ConnHSQLDB {

	final String SQL_INSERT_PESSOA = "INSERT INTO \"PESSOAS\" (\"NOME\", \"DATANASC\", \"PESO\")"
			+ " VALUES ( ?, ?, ?)";
	final String SQL_UPDATE_PESSOA = "UPDATE PESSOAS SET NOME = ?, DATANASC = ?, PESO = ? WHERE ID = ?";
	final String SQL_DELETE_PESSOA = "DELETE FROM PESSOAS WHERE ID = ?";
	final String SQL_SELECT_PESSOAS = "SELECT * FROM PESSOAS";
	final String SQL_SELECT_PESSOAS_ID = "SELECT * FROM \"PESSOAS\" WHERE ID = ?";

	// Este trabalho foi feito usando o banco, portanto
	// deve ser alterado as informações dele em SRC/com.db/ConnHSQLDB

	public int inserir(Pessoa pessoa) {
		int quantidade = 0;

		// inserir dados
		try (Connection connection = this.conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_INSERT_PESSOA);) {
			pst.setString(1, pessoa.getNome());
			pst.setDate(2, pessoa.getDataNasc());
			pst.setFloat(3, pessoa.getPeso());
			quantidade = pst.executeUpdate();
			System.out.println("Qtde inserido: " + quantidade);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quantidade;
	}

	public boolean alterar(Pessoa pessoa) {
		boolean alterou = false;
		try (Connection connection = this.conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_PESSOA);) {
			pst.setString(1, pessoa.getNome());
			pst.setDate(2, pessoa.getDataNasc());
			pst.setFloat(3, pessoa.getPeso());
			pst.setInt(4, pessoa.getId());
			int qnt = pst.executeUpdate();

			if (qnt > 0) {
				alterou = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return alterou;
	}

	public boolean excluir(Pessoa pessoa) {
		boolean alterou = false;
		try (Connection connection = this.conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_DELETE_PESSOA);) {
			pst.setInt(1, pessoa.getId());
			int qnt = pst.executeUpdate();

			if (qnt > 0) {
				alterou = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return alterou;
	}

	public List<Pessoa> listAll() {

		List<Pessoa> listaPessoa = new ArrayList<Pessoa>();

		try (Connection connection = this.conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_SELECT_PESSOAS);) {

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Pessoa pessoa = new Pessoa();

				pessoa.setId(rs.getInt("ID"));
				pessoa.setNome(rs.getString("NOME"));
				pessoa.setDataNasc(rs.getDate("DATANASC"));
				pessoa.setPeso(rs.getFloat("PESO"));
				listaPessoa.add(pessoa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaPessoa;
	}

	public Pessoa findByID(int id) {
		Pessoa pessoa = null;
		try (Connection connection = this.conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_SELECT_PESSOAS_ID);) {

			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				pessoa = new Pessoa();

				pessoa.setId(rs.getInt("ID"));
				pessoa.setNome(rs.getString("NOME"));
				pessoa.setDataNasc(rs.getDate("DATANASC"));
				pessoa.setPeso(rs.getFloat("PESO"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pessoa;
	}

}
