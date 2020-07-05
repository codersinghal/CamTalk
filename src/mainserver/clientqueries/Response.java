/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.clientqueries;

import java.io.Serializable;

/**
 *
 * @author harshit
 */
public class Response implements Serializable{
    private String code;
    private Object responseonj;
    public Response(Object responseobj,String code) {
        this.code = code;
        this.responseonj=responseobj;
    }

    public String getCode() {
        return code;
    }
    public Object getObject()
    {
        return this.responseonj;
    }
    
}
