package ru.otus.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final static String ENCODED_PASSWORD =
            "$2y$10$O34fLJA1AnGe/wFy.jlRYeJkeFGsWhHLGn7zBErRMrcwa4cuWaFcy"; // "pass"

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername: " + username);

        // dummy user
        return org.springframework.security.core.userdetails.User.builder()
                .username("admin")
                .password(ENCODED_PASSWORD)
                .authorities(new SimpleGrantedAuthority("USER"))
                .build();
    }
}