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
public class LoginQuery extends BaseQuery implements Serializable{

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    private String email,password;
    public LoginQuery(String email,String password)
    {
        this.email=email;
        this.password=password;
    }
    
   @Override
    public QueryType getquerytype() {
       return QueryType.LOGIN;
    } 
}
