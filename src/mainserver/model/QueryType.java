/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.model;

/**
 *
 * @author harshit
 */
public enum QueryType {
    LOGIN,
    SIGN_UP,
    LOGOUT,
    SEARCH_USER,
    ONLINE_USER,
    GET_STATUS,
    ADD_FRIEND,
    FriendSug,
    MESSAGE_SEND;
     QueryType()
    {
       this.toString();
    }
    
}
