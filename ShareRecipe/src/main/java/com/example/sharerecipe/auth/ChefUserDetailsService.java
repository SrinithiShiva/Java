package com.example.sharerecipe.auth;

import com.example.sharerecipe.repository.ChefRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ChefUserDetailsService implements UserDetailsService {
	private final ChefRepository chefRepository;

	public ChefUserDetailsService(ChefRepository chefRepository) {
		this.chefRepository = chefRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return chefRepository.findByEmail(email)
				.map(CustomUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("Chef not found"));
	}
}
