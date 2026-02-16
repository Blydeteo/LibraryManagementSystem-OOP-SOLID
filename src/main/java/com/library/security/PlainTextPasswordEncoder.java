package com.library.security;

/**
 * Plain text password encoder (for development/testing only)
 * WARNING: Do NOT use in production
 */
public class PlainTextPasswordEncoder implements IPasswordEncoder {
    
    @Override
    public String encode(String rawPassword) {
        return rawPassword;
    }
    
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}
