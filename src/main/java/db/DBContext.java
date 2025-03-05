/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vo The Vinh - CE567889
 */
public class DBContext {

    public Connection conn;

    // Phương thức khởi tạo 
    public DBContext() {
        try {
            String user = "sa";
            String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=FastFoodStore;encrypt=false";
            String pass = "Qui180204";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Lấy connection
    public Connection getConnection() {
        return conn;
    }

    // Phương thức cho các lệnh SELECT (có params)
    public ResultSet execSelectQuery(String query, Object[] params) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        
        if(params != null){
            for (int i = 0; i < params.length; i++){
                preparedStatement.setObject(i+1, params[i]);
            }
        }
        return preparedStatement.executeQuery();
    }
    
    // Phương thức cho các lệnh SELECT không có params 
    public ResultSet execSelectQuery (String query) throws SQLException {
        return this.execSelectQuery(query, null);
    }

    //Phương thức cho các lệnh INSERT, UPDATE, DELETE 
    public int execQuery(String query, Object[] params) throws SQLException {
               PreparedStatement preparedStatement = conn.prepareStatement(query);

         if(params != null){
            for (int i = 0; i < params.length; i++){
                preparedStatement.setObject(i+1, params[i]);
            }
        }
        return preparedStatement.executeUpdate();
    }
}
