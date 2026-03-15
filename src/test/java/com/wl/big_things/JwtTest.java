package com.wl.big_things;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    //@Test
    public void testGen(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","张三");
        String token =JWT.create().withClaim("user",claims).withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))
                .sign(Algorithm.HMAC256("adminwl"));
        System.out.println(token);
    }
    //@Test
    public void testParrse()
    {
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3NzMwODIwNzZ9" +
                ".J_bDb_vacZbUJj-TEim2ghjmdHdJLvAW4K7GHTZaStE";
        DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256("adminwl")).build().verify(token);
        Map<String, Claim>claim = decodedjwt.getClaims();
        System.out.println(claim.get("user"));
    }
}
