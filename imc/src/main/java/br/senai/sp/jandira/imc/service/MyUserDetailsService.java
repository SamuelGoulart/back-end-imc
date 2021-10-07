package br.senai.sp.jandira.imc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.senai.sp.jandira.imc.model.Usuario;
import br.senai.sp.jandira.imc.model.UsuarioUserDetails;
import br.senai.sp.jandira.imc.repository.UsuarioRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		
		System.out.printf("---> User: %s", usuario.get().getNome());
		
		return usuario.map(UsuarioUserDetails::new).get();
		
	}
	
	

}
