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

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
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
import javax.imageio.ImageIO;

public class UploadData {
public static byte[] extractBytes (String ImageName) throws IOException {
 // open image
 File imgPath = new File(ImageName);
 BufferedImage bufferedImage = ImageIO.read(imgPath);

 // get DataBufferBytes from Raster
 WritableRaster raster = bufferedImage .getRaster();
 DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

 return ( data.getData() );
}
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
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.43.3:1521:xe", "perm", "ora@123");
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
            ResultSet rset = stmt.executeQuery("select * from a_personalization_qa");
        //    select * from qa_ex where nin =?1
       //     ResultSet rset2 = stmt.createNativeQuery("UPDATE a_personalization_qa SET photograph_original = ?1  WHERE TRACKING_ID = ?2 ").;
String query = "UPDATE a_personalization_qa SET photograph_original = "+extractBytes("C:\\images\\photo1.jpg")+"  WHERE TRACKING_ID = '507253462717895' ";
stmt.execute(query);
//int t = stmt.executeUpdate("UPDATE a_personalization_qa SET photograph_original = 'C:\\images\\photo1.jpg'  WHERE TRACKING_ID = '' ");
//stmt.executeQuery("UPDATE a_personalization_qa SET photograph_original = 'C:\\images\\photo1.jpg'  WHERE NATIONAL_IDENTIFICATION_NUMBER = 76447840094 ");
           
// print out the results
      //      System.out.println("result = "+t);
            int i =0;
            while (rset.next()) {
                i++;
                // System.out.println(rset.getString(1));   

//                File image = new File("C:\\images\\photo"+i+".jpg");
//                FileOutputStream fos = new FileOutputStream(image);
//
//                byte[] buffer = new byte[1];
//                InputStream is = rset.getBinaryStream(7);
//                while (is.read(buffer) > 0) {
//                    fos.write(buffer);
//                }
//                fos.close();
            }
            connection.close();

        } catch (SQLException e) {
            System.out.println("eeeee "+e.getMessage());
        }

    }

}
