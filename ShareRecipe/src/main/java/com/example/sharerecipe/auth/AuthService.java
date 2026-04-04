package com.example.sharerecipe.auth;

import com.example.sharerecipe.dto.AuthRequest;
import com.example.sharerecipe.dto.AuthResponse;
import com.example.sharerecipe.dto.RefreshRequest;
import com.example.sharerecipe.dto.SignupRequest;
import com.example.sharerecipe.entity.Chef;
import com.example.sharerecipe.entity.RefreshToken;
import com.example.sharerecipe.entity.Role;
import com.example.sharerecipe.exception.BadRequestException;
import com.example.sharerecipe.exception.ConflictException;
import com.example.sharerecipe.exception.NotFoundException;
import com.example.sharerecipe.exception.UnauthorizedException;
import com.example.sharerecipe.repository.ChefRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
	private final ChefRepository chefRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final RefreshTokenService refreshTokenService;
	private final VerificationTokenService verificationTokenService;
	private final boolean refreshEnabled;
	private final boolean emailVerificationEnabled;

	public AuthService(ChefRepository chefRepository,
			PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager,
			JwtService jwtService,
			RefreshTokenService refreshTokenService,
			VerificationTokenService verificationTokenService,
			@Value("${app.auth.refresh-enabled:true}") boolean refreshEnabled,
			@Value("${app.auth.email-verification-enabled:false}") boolean emailVerificationEnabled) {
		this.chefRepository = chefRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.refreshTokenService = refreshTokenService;
		this.verificationTokenService = verificationTokenService;
		this.refreshEnabled = refreshEnabled;
		this.emailVerificationEnabled = emailVerificationEnabled;
	}

	@Transactional
	public AuthResponse signup(SignupRequest request) {
		if (chefRepository.existsByEmail(request.getEmail())) {
			throw new ConflictException("Email already registered");
		}
		if (chefRepository.existsByHandle(request.getHandle())) {
			throw new ConflictException("Handle already taken");
		}

		Chef chef = new Chef();
		chef.setEmail(request.getEmail());
		chef.setHandle(request.getHandle());
		chef.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		chef.getRoles().add(Role.CHEF);
		if (emailVerificationEnabled) {
			chef.setEnabled(false);
		}
		chefRepository.save(chef);

		if (emailVerificationEnabled) {
			AuthResponse response = new AuthResponse();
			response.setChefId(chef.getId());
			response.setHandle(chef.getHandle());
			response.setRoles(chef.getRoles().stream().map(Role::name).collect(Collectors.toSet()));
			response.setVerificationToken(verificationTokenService.createToken(chef).getToken());
			return response;
		}

		return buildAuthResponse(chef, null);
	}

	public AuthResponse login(AuthRequest request) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
		Chef chef = chefRepository.findById(principal.getId())
				.orElseThrow(() -> new NotFoundException("Chef not found"));
		return buildAuthResponse(chef, null);
	}

	public AuthResponse refresh(RefreshRequest request) {
		if (!refreshEnabled) {
			throw new BadRequestException("Refresh tokens disabled");
		}
		RefreshToken refreshToken = refreshTokenService.findValidToken(request.getRefreshToken())
				.orElseThrow(() -> new UnauthorizedException("Refresh token invalid"));
		Chef chef = refreshToken.getChef();
		refreshTokenService.revoke(refreshToken);
		return buildAuthResponse(chef, null);
	}

	@Transactional
	public void verifyEmail(String token) {
		var verificationToken = verificationTokenService.findValidToken(token)
				.orElseThrow(() -> new BadRequestException("Verification token invalid"));
		Chef chef = verificationToken.getChef();
		if (!chef.isEnabled()) {
			chef.setEnabled(true);
			chefRepository.save(chef);
		}
		verificationTokenService.markUsed(verificationToken);
	}

	private AuthResponse buildAuthResponse(Chef chef, String verificationToken) {
		Set<String> roles = chef.getRoles().stream().map(Role::name).collect(Collectors.toSet());
		String accessToken = jwtService.generateAccessToken(chef.getEmail(), roles);
		AuthResponse response = new AuthResponse();
		response.setChefId(chef.getId());
		response.setHandle(chef.getHandle());
		response.setRoles(roles);
		response.setAccessToken(accessToken);
		response.setAccessTokenExpiresAt(jwtService.getAccessTokenExpiry());
		response.setVerificationToken(verificationToken);
		if (refreshEnabled) {
			RefreshToken refreshToken = refreshTokenService.createToken(chef);
			response.setRefreshToken(refreshToken.getToken());
			response.setRefreshTokenExpiresAt(refreshToken.getExpiresAt());
		}
		return response;
	}
}
