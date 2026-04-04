package com.example.sharerecipe.auth;

import java.security.SecureRandom;
import java.util.Base64;

public final class TokenGenerator {
	private static final SecureRandom secureRandom = new SecureRandom();
	private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

	private TokenGenerator() {
	}

	public static String generateToken() {
		byte[] bytes = new byte[32];
		secureRandom.nextBytes(bytes);
		return encoder.encodeToString(bytes);
	}
}
