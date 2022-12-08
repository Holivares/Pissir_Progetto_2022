package Ruoli;

public class Utils {
    public static String getRole(){
        if(JWTs.getUserType().contains("collaboratore")) return "collaboratore";
        else if(JWTs.getUserType().contains("agricoltore")) return "agricoltore";
        else return "";
    }
    public static String getUserId(){
        String userIdRaw = JWTs.getUserId();
        String userId = userIdRaw.substring(1,userIdRaw.length()-1);
        return userId;
    }
}
