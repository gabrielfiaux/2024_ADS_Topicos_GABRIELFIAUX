/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import modelo.Produto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Douglas Sugano
 */
public class ProdutoDao {

    public void inserir(Produto obj) throws Exception {
        String sql = "INSERT INTO produto(nome,unidadeDeMedida) VALUES (?,?)";
        Connection conn = Conexao.getConexao();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getNomeProduto());
            ps.setString(2, obj.getUnidadeDeMedida());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        }
    } 

    public List<Produto> buscar(String nome) throws Exception {
        List<Produto> lista = new ArrayList<>();

        String sql = "select * from produto";
        sql += (!nome.equals("")) ? " where nome like ?" : "";

        Connection conexao = Conexao.getConexao();

        //try-with-resourses fecha o recurso automaticamente, dispensa o uso de .close()
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            if (!nome.equals("")) {
                ps.setString(1, "%" + nome + "%");
            }

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Produto p = new Produto();
                    p.setId(rs.getInt("id"));
                    p.setNomeProduto(rs.getString("nome"));
                    p.setUnidadeDeMedida(rs.getString("unidadeDeMedida"));
                    p.setDataCadastro(rs.getString("datacadastro"));
                    lista.add(p);
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return lista;
    }
    
    public List<Produto> listar(){
    String sql = "SELECT * FROM produto";
    Connection conexao = null;
        try {
            conexao = Conexao.getConexao();
        } catch (Exception ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    List<Produto> lista = new ArrayList<>();
    
    
        try {
            PreparedStatement pstm = conexao.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
             Produto p = new Produto();
             p.setId(rs.getInt("id"));
             p.setNomeProduto(rs.getString("nome"));
             p.setUnidadeDeMedida(rs.getString("unidadeDeMedida"));
             p.setDataCadastro(rs.getString("datacadastro"));
             lista.add(p);
                
            
            }
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ProdutoDao.listar");
        }
       return lista;
    }

    public void excluir(Integer id) throws Exception {
        String sql = "DELETE FROM produto WHERE id = ?";
        Connection conexao = Conexao.getConexao();
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public boolean alterar(Produto produto) throws SQLException, Exception{
    
        String sql = "UPDATE produto"
                +" SET nome = ?"
                +" unidadeDeMedida"
                +" WHERE id = ?";
      Connection conexao = Conexao.getConexao();
        try(PreparedStatement pstm = conexao.prepareStatement(sql)){
            pstm.setString(1, produto.getNomeProduto());
            pstm.setString(2, produto.getUnidadeDeMedida());
            pstm.setInt(3, produto.getId());
            return pstm.executeUpdate() ==1;
        }
    
    }

}
