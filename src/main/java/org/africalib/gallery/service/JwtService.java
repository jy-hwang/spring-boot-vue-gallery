package org.africalib.gallery.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
  String getToken(String key, Object value);

  Claims getClaim(String token);

  boolean isValid(String token);

  int getId(String token);
}
