/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Produto;
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

    public List<ProdutoCategoria> listar(){
    String sql = "SELECT * FROM produto_categoria";
    Connection conexao = null;
        try {
            conexao = Conexao.getConexao();
        } catch (Exception ex) {
            Logger.getLogger(ProdutoCategoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    List<ProdutoCategoria> lista = new ArrayList<>();
    
    
        try {
            PreparedStatement pstm = conexao.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                    ProdutoCategoria pc = new ProdutoCategoria();
                    pc.setId(rs.getInt("id"));
                    pc.setNomeCategoria(rs.getString("nome"));
                    lista.add(pc);
                }
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ProdutoCadegoriaDao.listar");
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
