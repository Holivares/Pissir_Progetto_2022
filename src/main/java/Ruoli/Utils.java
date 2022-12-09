package Ruoli;

public class Utils {
    public static String getRuolo(){
        if(JWTs.getUtenteTipo().contains("collaboratore")) return "collaboratore";
        else if(JWTs.getUtenteTipo().contains("agricoltore")) return "agricoltore";
        else return "";
    }
    public static String getUtenteId(){
        String utenteIdRaw = JWTs.getUtenteId();
        String utenteId = utenteIdRaw.substring(1,utenteIdRaw.length()-1);
        return utenteId;
    }
}
