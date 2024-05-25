package com.Booking.Booking.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Booking.Booking.entity.Roles;
import com.Booking.Booking.entity.UserAccounts;
import com.Booking.Booking.repository.IUserAccountsRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private IUserAccountsRepository userAccountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserAccounts userAccount = userAccountRepository.findByUserName(username);
		if (userAccount == null)
			throw new UsernameNotFoundException("Username " + username + " is not exists");

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		List<Roles> listRole = new ArrayList<Roles>();
		listRole.add(userAccount.getRole());

		for(Roles r : listRole) {
			System.out.println("ROLE: " + r.getRoleName());
		}
		UserContext.setUserId(userAccount.getUser().getId());
		System.out.println("ID: " +UserContext.getUserId());
		for (Roles role : listRole)
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));

		return new User(username, userAccount.getPassword(), authorities);
	}
}
