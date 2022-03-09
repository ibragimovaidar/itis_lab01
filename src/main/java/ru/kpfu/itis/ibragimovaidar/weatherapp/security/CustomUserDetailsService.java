package ru.kpfu.itis.ibragimovaidar.weatherapp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kpfu.itis.ibragimovaidar.weatherapp.repository.UserRepository;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .map(CustomUserDetails::from)
        .orElseThrow(() -> new UsernameNotFoundException(
            "User with username'" + username + "' does not exist"));
  }
}
