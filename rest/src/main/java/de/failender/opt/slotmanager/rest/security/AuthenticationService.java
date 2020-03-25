package de.failender.opt.slotmanager.rest.security;

import de.failender.opt.slotmanager.persistance.user.UserEntity;
import de.failender.opt.slotmanager.persistance.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	public void authenticate(String authorization) {
		if (authorization == null) {

			return;
		}
		String[] splits = authorization.split(";");
		String username = splits[0];
		String password = splits[1];
		UserEntity user = this.userRepository.findByName(username);
		if (user == null || user.getPassword() != null && !user.getPassword().equals(password)) {
			return;
		}
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(user, null, loadUserRights(user.getId()))
		);
	}



	private List<SimpleGrantedAuthority> loadUserRights(long userId) {
		return userRepository.getUserRights(userId).stream().map(right -> new SimpleGrantedAuthority(right)).collect(Collectors.toList());
	}

}
