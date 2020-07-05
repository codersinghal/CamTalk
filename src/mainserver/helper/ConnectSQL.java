/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.helper;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import mainserver.start.Start;

/**
 *
 * @author harshit
 */
public class ConnectSQL {
    public static Connection connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=(Connection) DriverManager.getConnection(Start.host, Start.user, Start.password);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
