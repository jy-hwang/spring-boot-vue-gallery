package org.africalib.gallery.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtServiceImpl implements JwtService {

  private static String SECRET_KEY = System.getenv("MY_SECRET_KEY");

  @Override
  public String getToken(String key, Object value) {

    Date expTime = new Date();
    expTime.setTime(expTime.getTime() + 1000 * 60 * 30);
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
}
