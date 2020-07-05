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
import mainserver.clientqueries.AddFriendQuery;
import mainserver.clientqueries.Response;
import mainserver.start.Start;
/**
 *
 * @author harshit
 */
public class AddFriendHandler {
  private AddFriendQuery afq;

    public AddFriendHandler(AddFriendQuery afq) {
        this.afq = afq;
    }
  public Response addFriend()
  {
      String q="INSERT INTO Friend VALUES(?,?);";
      try {
          PreparedStatement stm=Start.con.prepareStatement(q);
          stm.setString(1, afq.getMyuserid());
          stm.setString(2, afq.getFrienduserid());
          stm.executeUpdate();
          
          stm=Start.con.prepareStatement(q);
          stm.setString(1, afq.getFrienduserid());
          stm.setString(2, afq.getMyuserid());
          stm.executeUpdate();
          return new Response(null,"Success");
      } catch (SQLException ex) {
      
      }
      return new Response(null,"failed");
  }
    
}
