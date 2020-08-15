package com.streamhardwarestatistics.devicemanager.deviceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.streamhardwarestatistics.devicemanager.deviceManager.services.UserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Transactional
	@Override
	public void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	    	.authorizeRequests()
	    	.antMatchers("/register/**").permitAll()
	    	.antMatchers("/user/isAuthenticated/**").permitAll()
	    	.antMatchers("/code/**").permitAll()
			.antMatchers("/admin/**").hasAuthority("ADMIN")
			.antMatchers("/user/**").hasAuthority("USER")
			.anyRequest().authenticated()
			.and().logout().logoutUrl("/user/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID").clearAuthentication(true)
			.and().httpBasic();
	    http.cors();
	}

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService((UserDetailsService) userService);
        return authenticationProvider;
    }

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowCredentials(true).allowedHeaders("*");
			}
		};
	}
}
