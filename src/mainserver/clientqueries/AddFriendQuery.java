/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.clientqueries;

import java.io.Serializable;
import mainserver.model.QueryType;

/**
 *
 * @author harshit
 */
public class AddFriendQuery extends BaseQuery implements Serializable{
    
    private String myuserid,frienduid;

    public AddFriendQuery(String myuserid, String frienduid) {
        this.myuserid = myuserid;
        this.frienduid = frienduid;
    }
    public String getMyuserid()
    {
        return myuserid;
    }
    public String getFrienduserid()
    {
        return frienduid;
    }
    @Override
    public QueryType getquerytype() {
       return QueryType.ADD_FRIEND;
    }
    
}
