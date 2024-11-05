/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import modelo.ProdutoCategoria;

/**
 *
 * @author Aluno
 */
public class ProdutoCategoriaDao {

    public void inserir(ProdutoCategoria obj) throws Exception {
        String sql = "INSERT INTO produto_categoria (nome) VALUES (?)";
        Connection conn = Conexao.getConexao();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getNomeCategoria());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<ProdutoCategoria> buscar(String nome) throws Exception {
        List<ProdutoCategoria> lista = new ArrayList<>();

        String sql = "select * from produto_categoria";
        sql += (!nome.equals("")) ? " where nome like ?" : "";

        Connection conexao = Conexao.getConexao();

        //try-with-resourses fecha o recurso automaticamente, dispensa o uso de .close()
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            if (!nome.equals("")) {
                ps.setString(1, "%" + nome + "%");
            }

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProdutoCategoria pc = new ProdutoCategoria();
                    pc.setId(rs.getInt("id"));
                    pc.setNomeCategoria(rs.getString("nome"));
                    lista.add(pc);
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return lista;
    }

    public void excluir(Integer id) throws Exception {
        String sql = "DELETE FROM produto_categoria WHERE id = ?";
        Connection conexao = Conexao.getConexao();
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            throw ex;
        }
    }
}
