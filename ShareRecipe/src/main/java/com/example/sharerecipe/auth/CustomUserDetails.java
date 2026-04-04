package com.example.sharerecipe.auth;

import com.example.sharerecipe.entity.Chef;
import com.example.sharerecipe.entity.Role;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
	private final UUID id;
	private final String email;
	private final String passwordHash;
	private final boolean enabled;
	private final Set<Role> roles;

	public CustomUserDetails(Chef chef) {
		this.id = chef.getId();
		this.email = chef.getEmail();
		this.passwordHash = chef.getPasswordHash();
		this.enabled = chef.isEnabled();
		this.roles = chef.getRoles();
	}

	public UUID getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
				.collect(Collectors.toSet());
	}

	@Override
	public String getPassword() {
		return passwordHash;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
