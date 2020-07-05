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
public class FriendSuggQuery extends BaseQuery implements Serializable{

    String userid;

    public FriendSuggQuery(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }
    
    @Override
    public QueryType getquerytype() {
        return QueryType.FriendSug;
    }
    
}
