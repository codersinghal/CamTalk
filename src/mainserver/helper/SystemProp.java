/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
/**
 *
 * @author harshit
 */
public class SystemProp {

    
    private static String propfile="./src/mainserver/config.properties";
    Properties propobj;
    public SystemProp()
    {
        propobj=readPropFile(propfile);
        //System.out.println(propobj.getProperty("MYSQLUserName"));
    }
    public String getMYSQLUser()
    {
        return propobj.getProperty("MYSQLUserName");
    }
    public String getMYSQLPass()
    {
        return propobj.getProperty("MYSQLUserPass");
    }
    public String getMYSQLHost()
    {
        return propobj.getProperty("MYSQLHostName");
    }
    
    static Properties readPropFile(String propfile)  {
        Properties prop=null;
        try{
            FileInputStream fis=new FileInputStream(propfile);
             prop=new Properties();
            prop.load(fis);
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return prop;
    }
}
