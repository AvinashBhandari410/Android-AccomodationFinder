package c.ab.accommodationfinder;

import java.util.ArrayList;

/**
 * Created by avina on 4/14/2018.
 */

public class UserEntity {

    //used to save app data

    static String firstName = null;
    static String email= null;
    static String lastName= null;
    static String password = null;
    static  boolean IsUserActive=false;
    static ArrayList<String> al;

    public static void createlist(){
        al=new ArrayList<String>();
    }


    public String getFirstName(){
        return UserEntity.firstName;
    }

    public String getLastName(){
        return UserEntity.lastName;
    }
    //setting name
    public void setFirstName(String firstName){

        UserEntity.firstName = firstName;
    }

    public void setLastName(String lastName){

        UserEntity.lastName = lastName;
    }

    public String getEmail(){
        return UserEntity.email;
    }

    public void setUserActive(boolean userActive){

        UserEntity.IsUserActive = userActive;
    }
    public boolean isUserActive(){
        return UserEntity.IsUserActive;
    }

    public void setEmail(String email){
        UserEntity.email = email;
    }

    public String getPassword(){
        return UserEntity.password;

    }

    public void setPassword(String password){
        UserEntity.password = password;
    }
}

