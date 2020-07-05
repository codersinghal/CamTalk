/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.query_handler;

import mainserver.clientqueries.Response;
import mainserver.model.Message;

/**
 *
 * @author harshit
 */
public class MessageQuery {
    Message msg;

    public MessageQuery(Message msg) {
        this.msg = msg;
    }
    public Response send_messsage()
    {
        return new Response(msg,"Success");
    }
}
