package com.readingTracker.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.readingTracker.data.entity.AppUser;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.util.Arrays.stream;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {	
	@Value("${jwt-secret}")
	private String secret;
	
    public String generateAccessToken(User user) {
		return JWT.create()
                .withIssuer(SecurityConstants.TRACKERS_APPLICATION)
                .withAudience(SecurityConstants.TRACKERS_ADMINISTRATION)
                .withIssuedAt(new Date())
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .withClaim(SecurityConstants.AUTHORITIES, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(HMAC512(secret.getBytes()));
    }
    
	public String generateAccessToken(AppUser appUser) {
		return JWT.create().withIssuer(SecurityConstants.TRACKERS_APPLICATION)
				.withAudience(SecurityConstants.TRACKERS_ADMINISTRATION).withIssuedAt(new Date())
				.withSubject(appUser.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.withArrayClaim(SecurityConstants.AUTHORITIES, getClaimsFromAppUser(appUser)).sign(HMAC512(secret.getBytes()));
	}
	
    private String[] getClaimsFromAppUser(AppUser appUser) {
    	return appUser.getUserRole().getAuthorities();
    }

	public String generateRefreshToken(User user) {
    	return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME * 2))
                .withIssuer(SecurityConstants.TRACKERS_APPLICATION)
                .sign(HMAC512(secret.getBytes()));
    }
    
    public Map<String, String> generateTokenResponse(User user) {
    	Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", generateAccessToken(user));
        tokens.put("refresh_token", generateRefreshToken(user));
		return tokens;
    }

	public String getSubject(String token) {
		return getJWTVerifier().verify(token).getSubject();
	}

	private JWTVerifier getJWTVerifier() throws JWTVerificationException {
		return JWT.require(HMAC512(secret.getBytes())).withIssuer(SecurityConstants.TRACKERS_APPLICATION).build();
	}

	public String[] getClaims(String token) {
		DecodedJWT decodedJWT = getJWTVerifier().verify(token);
		return decodedJWT.getClaim(SecurityConstants.AUTHORITIES).asArray(String.class);
	}    

	public Collection<SimpleGrantedAuthority> getAuthorities(String[] claims) {
		return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toCollection(HashSet::new));
	}

	public Authentication getAuthentication(String username, String password,
			Collection<SimpleGrantedAuthority> authorities) {
		return new UsernamePasswordAuthenticationToken(username, password, authorities);
	}
}