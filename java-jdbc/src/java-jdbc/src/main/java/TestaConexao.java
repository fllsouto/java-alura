import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.ProdutoDAO;
import models.Produto;

public class TestaConexao {

	public static void main(String[] args) throws SQLException {
		showRecords();
	}
	
	private static void showRecords() throws SQLException {
		try(Connection con = getConnection()) {
			ProdutoDAO pDAO = new ProdutoDAO(con);
			pDAO.lista().forEach(System.out::println);;
		}	
	}
	
	
	private static void testConnection1() throws SQLException {
		Produto mesa = new Produto("Mesa Azul", "Mesa com 4 pés");
		try(Connection con = getConnection()) {
			ProdutoDAO pDAO = new ProdutoDAO(con);
			pDAO.salva(mesa);
			
		}
		
	}
	private static void testConnection2() throws SQLException {
		Connection connection = getConnection();
		
		String sql = "insert into Produto (nome, descricao) values(?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, "Macbook Pro");
		stmt.setString(2, "I5, 16 gb RAM, 240 SSD");
		stmt.execute();
		
		
		showRecords(connection);
		
		stmt.close();
	}
	
	private static void showRecords(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		stmt.execute("select * from Produto");
		ResultSet resultSet = stmt.getResultSet();
		while(resultSet.next()) {
			int id = resultSet.getInt("id");
			String nome = resultSet.getString("nome");
			String descricao = resultSet.getString("descricao");
			
			System.out.println("Id: " + id);
			System.out.println("Nome: " + nome);
			System.out.println("Descricao: " + descricao + "\n\n");
		}
		resultSet.close();
		stmt.close();
	}

	private static Connection getConnection() throws SQLException {
		String path = "jdbc:mysql://localhost/controle_compras?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
		String username = "root";
		String pass = "Ac42success!";
		return DriverManager.getConnection(path, username, pass);
	}
}
