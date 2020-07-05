/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.query_handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainserver.clientqueries.Response;
import mainserver.clientqueries.SearchUser;
import mainserver.model.User;
import mainserver.start.Start;

/**
 *
 * @author harshit
 */
public class UserSearchHandler {
    String name;
    ArrayList<User> users=new ArrayList<>();
    public  UserSearchHandler(SearchUser query)
    {
        this.name=query.getName();
    }
    public Response search()
    {
        String q="SELECT * FROM User WHERE User.name=?;";
        try {
            PreparedStatement stm=Start.con.prepareStatement(q);
            stm.setString(1, this.name);
            ResultSet rs=stm.executeQuery();
            while(rs.next())
            {
                User user=new User();
                user.setUserid(rs.getString(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setPhone(rs.getString(4));
                user.setWorkplace(rs.getString(5));
                users.add(user);
                System.out.println(user);
            }
            return new Response(users,"Success");
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return new Response(null,"Failed");
    }
}
//CREATE TABLE Auth(
//     userid VARCHAR(255) NOT NULL,
//     password VARCHAR(255) NOT NULL
//     );
