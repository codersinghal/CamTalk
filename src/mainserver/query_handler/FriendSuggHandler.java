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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainserver.clientqueries.FriendSuggQuery;
import mainserver.clientqueries.Response;
import mainserver.model.User;
import mainserver.start.Start;

/**
 *
 * @author harshit
 */
public class FriendSuggHandler {
    FriendSuggQuery fsq;
    Map<String,Integer> vis;
    ArrayList<String> result;
    public FriendSuggHandler(FriendSuggQuery fsq) {
        this.fsq = fsq;
    }
    void dfs(Map<String,ArrayList<String> > graph,String node)
    {   System.out.println(graph);
        vis.put(node,1);
        for( String al:graph.get(node))
        {
         if(vis.containsKey(al))
             continue;
         if(!graph.get(fsq.getUserid()).contains(al))
         {
             result.add(al);
         }
         dfs(graph,al);
        }
    }
    public Response FriendSugg()
    {
        Map<String,ArrayList<String> > graph=new HashMap<>();
        vis=new HashMap<>();
        result=new ArrayList<>();
        String query="SELECT * FROM Friend;";
        try {
            PreparedStatement stm=Start.con.prepareStatement(query);
            ResultSet rs=stm.executeQuery();
            while(rs.next())
            {   ArrayList<String> list;
                if(graph.containsKey(rs.getString(1)))
            {
                list=graph.get(rs.getString(1));
            }
                else
                { list=new ArrayList<>();
                graph.put(rs.getString(1), list);
                }
                graph.get(rs.getString(1)).add(rs.getString(2));
            }
            System.out.println(graph);
            dfs(graph,fsq.getUserid());
            ArrayList<User> friendsug=new ArrayList<>();
            String q="SELECT * FROM User WHERE userid=?;";
            for(String s:result)
            {
                PreparedStatement stmt=Start.con.prepareStatement(q);
                stmt.setString(1, s);
                ResultSet res=stmt.executeQuery();
                User user = null;
                while(res.next())
                {
                 user=new User();
                user.setUserid(res.getString(1));
            user.setName(res.getString(2));
            user.setEmail(res.getString(3));
            user.setPhone(res.getString(4));
            user.setWorkplace(res.getString(5));
            }
                friendsug.add(user);
            }
            return new Response(friendsug,"Success");
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        
        return new Response(null,"failed");
    }
    
}
