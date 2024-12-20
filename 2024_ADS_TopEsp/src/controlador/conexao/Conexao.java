package controlador.conexao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    // Pdrão Singleton

    private static Connection conexao;

    //construtor privado de conexao
    private Conexao() throws Exception {
        
        // nome do bd "topicos"
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://127.0.0.1/topicos";
            String usuario = "root";
            String senha = "";

            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);
            conexao.setAutoCommit(true);
        } catch (Exception e) {
            throw e;
        }
    }

    //metodo para instanciar
    public static Connection getConexao() throws Exception {
        if (conexao == null) {
            new Conexao();
        }
        return conexao;

    }
}
