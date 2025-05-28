package org.africalib.gallery.service;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

  private static final String SECRET_KEY = System.getenv("MY_SECRET_KEY");

  @Override
  public String getToken(String key, Object value) {

    Date expTime = new Date();
    expTime.setTime(expTime.getTime() + 1000 * 60 * 30); // 30m
    byte[] secretByteKey = DatatypeConverter.parseBase64Binary(SECRET_KEY);
    Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());

    Map<String, Object> headerMap = new HashMap<>();
    headerMap.put("typ", "JWT");
    headerMap.put("alg", "HS256");

    Map<String, Object> map = new HashMap<>();
    map.put(key, value);

    JwtBuilder builder = Jwts.builder().setHeader(headerMap)
        .setClaims(map)
        .setExpiration(expTime)
        .signWith(signKey, SignatureAlgorithm.HS256);

    return builder.compact();
  }

  @Override
  public Claims getClaim(String token) {
    if (token != null && !token.isEmpty()) {
      try {
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());

        return Jwts.parserBuilder()
            .setSigningKey(signKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
      } catch (ExpiredJwtException e) {
        e.printStackTrace();
      } catch (JwtException e2) {
        e2.printStackTrace();
      }
    }

    return null;
  }

  @Override
  public int getId(String token) {
    Claims claims = this.getClaim(token);
    if (claims != null) {
      return Integer.parseInt(claims.get("id").toString());
    }
    return 0;
  }

  @Override
  public boolean isValid(String token) {
    return this.getClaim(token) != null;
  }
}
