/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.clientqueries;

import java.io.Serializable;
import mainserver.model.QueryType;
import mainserver.model.User;

/**
 *
 * @author harshit
 */
public class SignUpQuery extends BaseQuery implements Serializable{
    private String password;
    private User user;

    public SignUpQuery(String password, User user) {
        this.password = password;
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public QueryType getquerytype() {
        return QueryType.SIGN_UP;
    }
    
}
