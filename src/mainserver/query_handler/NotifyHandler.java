/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.query_handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainserver.clientqueries.NotiQuery;
import mainserver.clientqueries.Response;
import mainserver.model.NotifyType;
import mainserver.model.User;
import mainserver.start.Start;

/**
 *
 * @author harshit
 */
public class NotifyHandler {
    NotiQuery nq;
   String receiver,sender;
    public NotifyHandler(NotiQuery nq) {
        this.nq = nq;
    }
    public void MessageNoti()
    {
        String q="INSERT INTO Notifications (?,?,?);";
        try {
            PreparedStatement stm=Start.con.prepareStatement(q);
            stm.setString(1, nq.getSenderuid());
            stm.setString(2, nq.getReceiveruid());
            stm.setString(3, "MESSAGE");
            stm.executeUpdate();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    public ResultSet allnotifs()
    {
        try {
            String q="SELECT * FROM Notifications WHERE Notifications.receiverid=?;";
            PreparedStatement stm=Start.con.prepareStatement(q);
            stm.setString(1, nq.getReceiveruid());
            ResultSet rs=stm.executeQuery();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public Response getvideonotifs(ArrayList<String> videocallq)
    {
        ArrayList<User> res=new ArrayList<>();
        for(String s:videocallq)
        {
            try {
                String q="SELECT * FROM User WHERE User.userid=?;";
                PreparedStatement stm=Start.con.prepareStatement(q);
                stm.setString(1,s);
                ResultSet rs=stm.executeQuery();
                while(rs.next())
                {
                    User user = new User();
            user.setUserid(rs.getString(1));
            user.setName(rs.getString(2));
            user.setEmail(rs.getString(3));
            user.setPhone(rs.getString(4));
            user.setWorkplace(rs.getString(5));
            res.add(user);
                }
            } catch (SQLException ex) {
                
            }
        }
        return new Response(res,"Success");
    }
}
