package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Produto;

public class ProdutoDAO {

	private Connection con;
	
	public ProdutoDAO(Connection con) {
		this.con = con;
	}

    public List<Produto> lista() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "select * from Produto";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.execute();
            transformaResultadoEmProdutos(stmt, produtos);
        }
        return produtos;
    }

	public void salva(Produto mesa) throws SQLException {
		String sql = "insert into Produto (nome, descricao) values(?, ?)";
		
		try(PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, mesa.getNome());
			stmt.setString(2, mesa.getDescricao());
			
			stmt.execute();
			
			try(ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					int id = rs.getInt(1);
					mesa.setId(id);
					System.out.println("O id da mesa inserida é : " + mesa.getId());
				}
			}
		}
	}

    private void transformaResultadoEmProdutos(PreparedStatement stmt, List<Produto> produtos)
            throws SQLException {
        try (ResultSet rs = stmt.getResultSet()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                Produto produto = new Produto(nome, descricao);
                produto.setId(id);
                produtos.add(produto);
            }
        }
    }
	

}
