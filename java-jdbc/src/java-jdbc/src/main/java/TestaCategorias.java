import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import dao.CategoriasDAO;
import models.Categoria;
import models.Produto;

public class TestaCategorias {


    public static void main(String[] args) throws SQLException {
    	try(Connection con = getConnection()) {
            List<Categoria> categorias = new CategoriasDAO(con).listaComProdutos();
            for(Categoria categoria : categorias) {
                System.out.println(categoria.getNome());

                for(Produto produto : categoria.getProdutos()) {
                    System.out.println(categoria.getNome() + " - " + produto.getNome());
                }

            }
        }
    }
	private static Connection getConnection() throws SQLException {
		String path = "jdbc:mysql://localhost/controle_compras?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
		String username = "root";
		String pass = "Ac42success!";
		return DriverManager.getConnection(path, username, pass);
	}
}
