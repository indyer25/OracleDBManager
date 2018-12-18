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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

    public static void main(String[] argv) throws FileNotFoundException, IOException {

        System.out.println("-------- Oracle JDBC Connection Testing ------");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }
        System.out.println("Oracle JDBC Driver Registered!");
        Connection connection = null;
        Connection connection2 = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.8.102:1521:xe", "p", "ora@123");
            connection2 = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.8.102:1521:xe", "perm", "ora@123");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }

        try {
            Statement stmt = connection.createStatement();
            //  ResultSet rset = stmt.executeQuery("select * from a_personalization_qa");

            //UPDATE a_personalization_qa SET photograph_original = "+extractBytes("C:\\images\\photo1.jpg")+"  WHERE TRACKING_ID = '507253462717895'            
           // PreparedStatement pstmt = connection.prepareStatement("UPDATE a_personalization_qa SET photograph_original = ?1 WHERE TRACKING_ID = '507253462717895'");
            ResultSet rset = connection.prepareStatement("select * from a_personalization_qa").executeQuery();
           while(rset.next()){
               byte[] photo = rset.getBytes("photograph_original");
             //  System.out.println("Photo = "+photo);
               if(photo.length!=0){
                   PreparedStatement pstmt = connection2.prepareStatement("UPDATE a_personalization_qa SET photograph_original = ?1 WHERE photograph_original = ''");  
             pstmt.setBytes(1, photo);
               System.out.println("Updated = "+pstmt.executeUpdate()); 
               }
            
             
           }
//            File fBlob = new File("C:\\images\\photo7.jpg");
//            FileInputStream is = new FileInputStream(fBlob);
//            pstmt.setBinaryStream(1, is, (int) fBlob.length());
//            System.out.println("EXxxx " + pstmt.execute());
//pstmt.execute();

        } catch (SQLException e) {
            System.out.println("Exception " + e.getMessage());
        }

    }

}
