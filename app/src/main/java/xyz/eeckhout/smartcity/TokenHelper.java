package xyz.eeckhout.smartcity;

import com.auth0.android.jwt.DecodeException;
import com.auth0.android.jwt.JWT;

public class TokenHelper {
    public static boolean isTokenValid(String token){
        JWT jwt;
        if(token == null || token.isEmpty())
            return false;
        try {
            jwt = new JWT(token);
            return !jwt.isExpired(10);
        }
        catch(DecodeException exception){
            return false;
        }
    }
}
