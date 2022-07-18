package com.test.jabis.auth.jwt;

import com.test.jabis.user.dao.User;
import com.test.jabis.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("PrincipalDetailService.loadUserByUsername" + userId);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new PrincipalDetails(user);
    }
}
