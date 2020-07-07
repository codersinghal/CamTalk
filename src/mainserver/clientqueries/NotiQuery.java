/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.clientqueries;

import java.io.Serializable;
import mainserver.model.NotifyType;
import mainserver.model.QueryType;

/**
 *
 * @author harshit
 */
public class NotiQuery extends BaseQuery implements Serializable{

    String senderuid,receiveruid;
    
    public NotiQuery(String receiveruid)
    {
        this.receiveruid=receiveruid;
    }
    public NotiQuery(String senderuid, String receiveruid) {
        this.senderuid = senderuid;
        this.receiveruid = receiveruid;
        
    }

    public String getSenderuid() {
        return senderuid;
    }

    public String getReceiveruid() {
        return receiveruid;
    }

    
    
    @Override
    public QueryType getquerytype() {
        return QueryType.NOTIFICATION;
    }
    
}
