package com.alekseyruban.barbershopServer.security;

import com.alekseyruban.barbershopServer.entity.Client;
import com.alekseyruban.barbershopServer.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> client = clientRepository.findByEmail(username);
        if (client.isPresent()) {
            CustomUserDetails customUserDetails = client.map(CustomUserDetails::new).orElse(null);
            System.out.println(customUserDetails.getAuthorities());
        }
        return client.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}
