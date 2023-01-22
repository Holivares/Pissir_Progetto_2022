package jwtToken;

import jwtToken.jwtlib.JWTfun;

public class Utils {
    public static String getRole(){
        if(JWTfun.getUserType().contains("collaboratori")) return "collaboratori";
        else if(JWTfun.getUserType().contains("agricoltori")) return "agricoltori";
        else return "";
    }
    public static String getUserId(){
        String userIdRaw = JWTfun.getUserId();
        return userIdRaw.substring(1,userIdRaw.length()-1);
    }
}
