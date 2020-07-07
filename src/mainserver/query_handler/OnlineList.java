/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.query_handler;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainserver.clientqueries.OnlineUserQuery;
import mainserver.clientqueries.Response;
import mainserver.model.User;
import mainserver.start.Start;

/**
 *
 * @author harshit
 */
public class OnlineList implements Serializable{
    ArrayList<User> onlinelist=new ArrayList<>();
    OnlineUserQuery query;
    public OnlineList(OnlineUserQuery query)
    {
        this.query=query;
    }
    public Response onlineusers()
    {
        String friends="SELECT * FROM Friend WHERE Friend.userID=?;";
        String thisuser="SELECT * FROM User WHERE User.userid=?;";
        try {
            PreparedStatement stm=Start.con.prepareStatement(friends);
            stm.setString(1,query.getUserUID());
            ResultSet rs=stm.executeQuery();
            while(rs.next())
            {   PreparedStatement stmt=Start.con.prepareStatement(thisuser);
                stmt.setString(1, rs.getString(2));
                ResultSet res=stmt.executeQuery();
                User user=new User();
                while(res.next()){
                user.setUserid(res.getString(1));
            user.setName(res.getString(2));
            user.setEmail(res.getString(3));
            user.setPhone(res.getString(4));
            user.setWorkplace(res.getString(5));   
            }
                onlinelist.add(user);
            }
            
            return new Response(onlinelist,"Success");
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return new Response(null,"failed");
    }
}
