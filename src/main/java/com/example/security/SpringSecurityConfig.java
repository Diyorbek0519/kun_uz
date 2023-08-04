package com.example.security;


import java.util.UUID;

//@EnableWebSecurity
public class SpringSecurityConfig {
   /* @Bean
    public AuthenticationProvider authenticationProvider() {
        // authentication (login,password)
        String password = UUID.randomUUID().toString();
        System.out.println("User Password mazgi: " + password);

        UserDetails user = User.builder()
                .username("user")
                .password("{noop}" + password)
                .roles("USER")
                .build();
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(new InMemoryUserDetailsManager(user));
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // authorization (ROLE)
        http.authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and().formLogin();
        return http.build();
    }*/
}
