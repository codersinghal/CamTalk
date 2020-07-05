/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.clientqueries;

import java.io.Serializable;
import java.util.ArrayList;
import mainserver.model.QueryType;
import mainserver.model.User;

/**
 *
 * @author harshit
 */
public class OnlineUserQuery extends BaseQuery implements Serializable{

    private String userUID;

    public OnlineUserQuery(String userUID) {
        this.userUID=userUID;
    }

    public String getUserUID() {
        return userUID;
    }
    
    @Override
    public QueryType getquerytype() {
                return QueryType.ONLINE_USER;
    }
    
}
