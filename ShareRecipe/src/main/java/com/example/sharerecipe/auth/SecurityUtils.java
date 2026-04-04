package com.example.sharerecipe.auth;

import com.example.sharerecipe.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {
	private SecurityUtils() {
	}

	public static CustomUserDetails currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
			throw new UnauthorizedException("Authentication not found or invalid user details");
		}
		return (CustomUserDetails) authentication.getPrincipal();
	}
}
