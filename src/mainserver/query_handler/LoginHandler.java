/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.query_handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mainserver.clientqueries.LoginQuery;
import mainserver.clientqueries.Response;
import mainserver.model.User;
import mainserver.start.Start;

/**
 *
 * @author harshit
 */
public class LoginHandler {
    private LoginQuery loginquery;
    public LoginHandler(LoginQuery query){
        this.loginquery = query;
    }
    public Response login() {
        User user ;

        String query = "SELECT * FROM User INNER JOIN Auth ON User.userid = Auth.userid WHERE User.email=? AND Auth.password=?;";
        
        try {
            PreparedStatement stm = Start.con.prepareStatement(query);
            stm.setString(1, loginquery.getEmail());
            stm.setString(2, loginquery.getPassword());
            System.out.println(loginquery.getEmail()+loginquery.getPassword());
            ResultSet rs = stm.executeQuery();
            if(!rs.next()){
                System.out.println("sendind fail");
                return new Response(null,"Failed");
            }
            
            user = new User();
            user.setUserid(rs.getString(1));
            user.setName(rs.getString(2));
            user.setEmail(rs.getString(3));
            user.setPhone(rs.getString(4));
            user.setWorkplace(rs.getString(5));
            String online ="UPDATE OnlineUser SET OnlineUser.status=? WHERE OnlineUser.userid=?;";
            stm=Start.con.prepareStatement(online);
            stm.setString(1, "online");
            stm.setString(2, user.getUserid());
            stm.executeUpdate();
            return new Response(user,"Success");

        }catch (SQLException e){
            e.printStackTrace();
        }
        return new Response(null,"Failed");

    }
}
