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
public class GetStatusQuery extends BaseQuery implements Serializable{

    private String  userid;

    public GetStatusQuery(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }
    
    @Override
    public QueryType getquerytype() {
        return QueryType.GET_STATUS;
    }
    
}
