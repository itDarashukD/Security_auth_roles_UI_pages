package com.example.rest.config;

import com.example.rest.model.Permission;
import com.example.rest.service.CustomUserDetailService;
import com.example.rest.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;


    public static final String ROLE_VIEW_INFO = "VIEW_INFO";
    public static final String ROLE_VIEW_ADMIN = "VIEW_ADMIN";
    public static final String URL_LOGIN_PAGE = "/login";
    public static final String URL_RANDOM_INFO_PAGE = "/security/info";
    public static final String URL_ADD_USER_ENDPOINT = "/*/addUser";
    public static final String URL_ABOUT_ENDPOINT = "/security/about";
    public static final String URL_ADMIN_ENDPOINT = "/security/admin";
    private static final String URL_GET_BLOCKED_USER_ENDPOINT = "/*/getBlockedUsers";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
	       .disable()
	       .authorizeRequests()
		      .antMatchers(URL_LOGIN_PAGE).permitAll()
		      .antMatchers(URL_ABOUT_ENDPOINT).permitAll()
		      .antMatchers(URL_ADD_USER_ENDPOINT).permitAll()
		      .antMatchers(URL_GET_BLOCKED_USER_ENDPOINT).permitAll()

		      .antMatchers(URL_ADMIN_ENDPOINT).hasAnyRole(ROLE_VIEW_ADMIN)
		      .antMatchers(URL_RANDOM_INFO_PAGE).hasAnyRole(ROLE_VIEW_INFO,ROLE_VIEW_ADMIN)

	       .anyRequest()
	       .authenticated()
	       .and()
		      .formLogin()
		      .loginPage(URL_LOGIN_PAGE)
		      .usernameParameter("email") 					 //for enter with Email instead Login
		      .permitAll()   	        					 // free access for any request
	       .and()
		      .logout()
		      .permitAll();
    }


    @Bean
    public GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
        SimpleAuthorityMapper mapper = new SimpleAuthorityMapper();
        mapper.setConvertToUpperCase(true);

        return mapper;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(customPasswordEncoder);
        provider.setUserDetailsService(customUserDetailService);
        provider.setAuthoritiesMapper(grantedAuthoritiesMapper());

        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }



}
