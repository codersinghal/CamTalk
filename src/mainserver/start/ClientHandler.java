/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.start;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainserver.clientqueries.AddFriendQuery;
import mainserver.clientqueries.BaseQuery;
import mainserver.clientqueries.FriendSuggQuery;
import mainserver.clientqueries.GetStatusQuery;
import mainserver.clientqueries.LoginQuery;
import mainserver.clientqueries.LogoutQuery;
import mainserver.clientqueries.MessageSendQuery;
import mainserver.clientqueries.NotiQuery;
import mainserver.clientqueries.OnlineUserQuery;
import mainserver.clientqueries.Response;
import mainserver.clientqueries.SearchUser;
import mainserver.clientqueries.SignUpQuery;
import mainserver.model.Message;
import mainserver.model.QueryType;
import mainserver.model.User;
import mainserver.query_handler.AddFriendHandler;
import mainserver.query_handler.FileReciever;
import mainserver.query_handler.FileSender;
import mainserver.query_handler.FriendSuggHandler;
import mainserver.query_handler.GetStatusHandler;
import mainserver.query_handler.LoginHandler;
import mainserver.query_handler.LogoutHandler;
import mainserver.query_handler.MessageQuery;
import mainserver.query_handler.NotifyHandler;
import mainserver.query_handler.OnlineList;
import mainserver.query_handler.SignupHandler;
import mainserver.query_handler.UserSearchHandler;

/**
 *
 * @author harshit
 */
public class ClientHandler implements Runnable {

    Socket client;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    ClientHandler(Socket client) {
        this.client = client;
        try {
            ois = new ObjectInputStream(client.getInputStream());
            oos = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object obj = ois.readObject();
                System.out.println(obj.getClass());
                BaseQuery res = (BaseQuery) obj;

                if (res.getquerytype() == QueryType.SIGN_UP) {
                    System.out.println("qwert");
                    SignupHandler sq = new SignupHandler((SignUpQuery) res);
                    String cwd = System.getProperty("user.dir");
                    String loc = cwd + "/profilePics/";
                    FileReciever fileReciever = new FileReciever();
                    fileReciever.readFile(fileReciever.createSocketChannel(Start.getServerSocketChannel()), ((SignUpQuery) res).getUser().getUserid(), loc);
                    System.out.println("Recieving profile pic..!!");
                    oos.writeObject(sq.signup());
                    oos.flush();
                }
                if (res.getquerytype() == QueryType.LOGIN) {
                    LoginHandler lq = new LoginHandler((LoginQuery) res);
                    Response response = lq.login();
                    oos.writeObject(response);
                    oos.flush();
                    String cwd = System.getProperty("user.dir");
                    String loc = cwd + "/profilePics/";
                    if (response.getCode() == "Success") {
                        //     FileSender fileSender = new FileSender();
                        String userId = ((User) response.getObject()).getUserid();
                        //   fileSender.sendFile(fileSender.createSocketChannel(client.getInetAddress().getCanonicalHostName()), loc + userId);
                        System.out.println("Sending Profile Pic..!!");
                    }

                }
                if (res.getquerytype() == QueryType.SEARCH_USER) {
                    UserSearchHandler ush = new UserSearchHandler((SearchUser) res);
                    oos.writeObject(ush.search());
                    System.out.println("sent list");
                    oos.flush();
                }
                if (res.getquerytype() == QueryType.ONLINE_USER) {
                    OnlineList olist = new OnlineList((OnlineUserQuery) res);
                    oos.writeObject(olist.onlineusers());
                    oos.flush();
                }
                if (res.getquerytype() == QueryType.LOGOUT) {
                    LogoutHandler lh = new LogoutHandler((LogoutQuery) res);
                    oos.writeObject(lh.logout());
                    oos.flush();
                }
                if (res.getquerytype() == QueryType.ADD_FRIEND) {
                    AddFriendHandler afh = new AddFriendHandler((AddFriendQuery) res);
                    oos.writeObject(afh.addFriend());
                    oos.flush();
                }
                if (res.getquerytype() == QueryType.GET_STATUS) {
                    GetStatusHandler gsh = new GetStatusHandler((GetStatusQuery) res);
                    oos.writeObject(gsh.getStatus());
                    oos.flush();
                }
                if (res.getquerytype() == QueryType.MESSAGE_SEND) {
                    MessageSendQuery msq = (MessageSendQuery) res;
                    String receiverid = msq.getMessage().getReceiveruid();
                    GetStatusHandler gsh = new GetStatusHandler(new GetStatusQuery(receiverid));
                    Response rs = gsh.getStatus();
                    // if the receiver is offline
                    if (!("online").equals(rs.getObject().toString())) {
                        NotiQuery nq = new NotiQuery(msq.getMessage().getSenderuid(), msq.getMessage().getReceiveruid());
                        NotifyHandler nh = new NotifyHandler(nq);
                        nh.MessageNoti();
                        String cwd = System.getProperty("user.dir");
                        String path = cwd + "/chatdb/" + nq.getSenderuid() + nq.getReceiveruid();
                        File f = new File(path);
                        try {
                            f.createNewFile();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
                        bw.write(msq.getMessage().getMsg() + "\n");
                        bw.flush();
                        bw.close();
                        continue;
                    }
                    Socket sc = new Socket("localhost", 1103);
                    ObjectOutputStream coos = new ObjectOutputStream(sc.getOutputStream());
                    ObjectInputStream cois = new ObjectInputStream(sc.getInputStream());
                    MessageQuery mq = new MessageQuery(msq.getMessage());
                    coos.writeObject(mq.send_messsage());
                    coos.flush();
                    coos.close();
                    sc.close();
                }
                if (res.getquerytype() == QueryType.FriendSug) {
                    FriendSuggQuery fsq = (FriendSuggQuery) res;
                    FriendSuggHandler fgh = new FriendSuggHandler(fsq);
                    oos.writeObject(fgh.FriendSugg());
                    oos.flush();
                }
                if(res.getquerytype()==QueryType.NOTIFICATION)
                {
                    NotiQuery nq=(NotiQuery) res;
                    NotifyHandler nh=new NotifyHandler(nq);
                    ResultSet rs=nh.allnotifs();
                    if(rs==null)
                    {
                        oos.writeObject(new Response(null,"Success"));
                        continue;
                    }
                    ArrayList<String> messageq=new ArrayList<>();
                    ArrayList<String> videocallq=new ArrayList<>();
                    while(rs.next())
                    {
                        if(rs.getString(3).equals("VIDEOCALL"))
                        {
                            videocallq.add(rs.getString(1));
                        }
                        else
                        {
                            messageq.add(rs.getString(1));
                        }
                    }
                    
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                try {
                    oos.writeObject(new Response(null, "Failed"));
                } catch (IOException ex1) {

                }
            }
        }
    }
}
