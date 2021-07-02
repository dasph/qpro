package me.amvse.qpro.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserPasswordEncoder implements PasswordEncoder {
  @Value("${me.amvse.qpro.secret}")
  private String secret;

  @Override
  public String encode (CharSequence rawPassword) {
    return UserPasswordEncoder.encode(rawPassword, secret);
  }

  public static String encode (CharSequence rawPassword, String secret) {
    SecretKey secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");

    try {
      Mac hmac = Mac.getInstance("HmacSHA256");
      hmac.init(secretKeySpec);

      byte[] hmacData = hmac.doFinal(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(hmacData);

    } catch (Exception e) { return null; }
  }

  @Override
  public boolean matches (CharSequence rawPassword, String encodedPassword) {
    return encode(rawPassword).equals(encodedPassword);
  }
}
