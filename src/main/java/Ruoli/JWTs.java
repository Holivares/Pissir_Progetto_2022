package Ruoli;

import com.auth0.jwt.JWT;
import java.util.Base64;

public class JWTs {
    private static String token = "";
    public static String getUtenteTipo() {
        String utenteTipo = getPayload().split("\"roles\":")[1].split("]")[0];

        return utenteTipo;
    }
    public static String getUtenteId() {
        return getPayload().split("\"sub\":")[1].split(",\"typ\"")[0];
    }
    public static String getPayload() {
        return new String(Base64.getUrlDecoder().decode(JWT.decode(token).getPayload().getBytes()));
    }

}
