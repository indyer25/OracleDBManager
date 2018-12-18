/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author indyer25
 */
package javaoraclecrud;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleDBManager {

    public static void main(String[] argv) throws FileNotFoundException, IOException {

        System.out.println("-------- Oracle JDBC Connection ------");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver is missing");
            e.printStackTrace();
            return;
        }
        System.out.println("Oracle JDBC Driver Registered!");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "perm", "ora@123");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        if (connection != null) {
            System.out.println("Connection established, take control of your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }

        try {
            Statement stmt = connection.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT owner, table_name FROM all_tables");
            System.out.println("Retreiving database tables...");
            while (rset.next()) {            
                System.out.println(rset.getString(2));
            }
            
            connection.close();

        } catch (SQLException e) {
        }

    }

}
