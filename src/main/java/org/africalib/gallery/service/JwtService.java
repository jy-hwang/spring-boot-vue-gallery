package org.africalib.gallery.service;

public interface JwtService {
  String getToken(String key, Object value);
}
