package com.readingTracker.web;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.data.entity.Role;
import com.readingTracker.data.entity.factory.AppUserProvider;
import com.readingTracker.data.repository.AppUserRepository;
import com.readingTracker.security.jwt.JwtUtils;
import com.readingTracker.security.services.UserDetailsImpl;
import com.readingTracker.web.dto.AppUserLoginDto;
import com.readingTracker.web.dto.AppUserRegistrationDto;
import com.readingTracker.web.dto.JwtResponse;
import com.readingTracker.web.dto.MessageResponse;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AppUserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody AppUserLoginDto loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		return ResponseEntity.ok(new JwtResponse(jwt, "Bearer", userDetails.getId().toString(),
				userDetails.getUsername(), roles.toString()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody AppUserRegistrationDto signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		// Create new user's account
		AppUser user = AppUserProvider.getFactory().create(signUpRequest.getName(), signUpRequest.getUsername(),
				passwordEncoder.encode(signUpRequest.getPassword()), LocalDate.parse(signUpRequest.getDateOfBirth()),
				Role.ROLE_USER);

		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
