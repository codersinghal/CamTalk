/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.query_handler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainserver.clientqueries.Response;
import mainserver.clientqueries.SignUpQuery;
import mainserver.start.Start;

/**
 *
 * @author harshit
 */
public class SignupHandler {
    private String userid, password;
    private String email, phone;
    private String name;
    private String workplace;
    public SignupHandler(SignUpQuery query)
    {
        this.name=query.getUser().getName();
        this.email=query.getUser().getEmail();
        this.phone=query.getUser().getPhone();
        this.userid=query.getUser().getUserid();
        this.workplace=query.getUser().getWorkplace();
        this.password=query.getPassword();
    }
    public Response signup()
            
    {
        String query="INSERT INTO User VALUES(?,?,?,?,?);";
        String q="INSERT INTO Auth VALUES(?,?);";
        String online="INSERT INTO OnlineUser VALUES(?,?,?);";
        try {
            PreparedStatement stm=Start.con.prepareStatement(query);
            stm.setString(1,this.userid);
            stm.setString(2, this.name);
            stm.setString(3, this.email);
            stm.setString(4, this.phone);
            stm.setString(5, this.workplace);
            stm.executeUpdate();
            stm=Start.con.prepareStatement(q);
            stm.setString(1, this.userid);
            stm.setString(2, this.password);
            stm.executeUpdate();
            stm=Start.con.prepareStatement(online);
            stm.setString(1, this.userid);
            stm.setString(2, "offline");
            stm.setString(3, "localhost");
            stm.executeUpdate();
            return new Response(null,"Success");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new Response(null,"Failed");
    }
}
