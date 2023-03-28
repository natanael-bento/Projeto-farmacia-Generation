package com.generation.games.security;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.games.model.Cliente;
import com.generation.games.repository.ClienteRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Optional<Cliente> cliente = clienteRepository.findByCliente(userName);

		if (cliente.isPresent())
			return new UserDetailsImpl(cliente.get());
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		
	
	}
}
