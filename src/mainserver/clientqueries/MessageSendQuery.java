/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.clientqueries;

import java.io.Serializable;
import mainserver.model.Message;
import mainserver.model.QueryType;

/**
 *
 * @author harshit
 */
public class MessageSendQuery extends BaseQuery implements Serializable{

    Message message;
    public MessageSendQuery(Message message) {
        this.message = message;
        
    }

    public Message getMessage() {
        return message;
    }

    
    
    
    @Override
    public QueryType getquerytype() {
        return QueryType.MESSAGE_SEND;
    }
    
}
