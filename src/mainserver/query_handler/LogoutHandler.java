/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.query_handler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainserver.clientqueries.LogoutQuery;
import mainserver.clientqueries.Response;
import mainserver.start.Start;

/**
 *
 * @author harshit
 */
public class LogoutHandler {
    LogoutQuery query;
    public LogoutHandler(LogoutQuery query)
    {
        this.query=query;
    }
    public Response logout()
    {
        String q="UPDATE OnlineUser SET OnlineUser.status=? WHERE OnlineUser.userid=?;";
        try {
            PreparedStatement stm=Start.con.prepareStatement(q);
            Date date=java.util.Calendar.getInstance().getTime(); 
            stm.setString(1, " last seen at "+date.toString());
            stm.setString(2, query.getUserid());
            stm.executeUpdate();
            return new Response(null,"Success");
        } catch (SQLException ex) {
        
        }
        return new Response(null,"failed");
    }
}
