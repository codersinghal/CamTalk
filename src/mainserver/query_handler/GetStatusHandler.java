/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.query_handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainserver.clientqueries.GetStatusQuery;
import mainserver.clientqueries.Response;
import mainserver.start.Start;

/**
 *
 * @author harshit
 */
public class GetStatusHandler {
    private GetStatusQuery gsq;

    public GetStatusHandler(GetStatusQuery gsq) {
        this.gsq = gsq;
    }
    public Response getStatus()
    {
        String q="SELECT * FROM OnlineUser WHERE OnlineUser.userID=?;";
        
        try {
            PreparedStatement stm=Start.con.prepareStatement(q);
            stm.setString(1, gsq.getUserid());
            ResultSet rs=stm.executeQuery();
            String status = null;
            while(rs.next())
            {
                status=rs.getString(2);
                System.out.println(status);
            }
            return new Response(status,"Success");
        } catch (SQLException ex) {
        
        }
        return new Response(null,"Failed");
    }
}
