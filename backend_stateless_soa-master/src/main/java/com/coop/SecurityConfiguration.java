package com.coop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.coop.business.IAuthTokenBusiness;
import com.coop.model.persistence.UsuarioRepository;
import com.coop.web.Constantes;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	//(1)// curl -X POST http://localhost:8080/dologin -H 'content-type: multipart/form-data' -F username=admin -F password=123 -c cookies.txt -v 

	//(2)//	curl -X GET http://localhost:8080/api/v1/productos -b cookies.txt -v
	
	//(3)// curl -X POST http://localhost:8080/logout -c cookies.txt -v
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		String[] resources = new String[] { "/", "/index.html", "/favicon.png", "/app.js", "/config/**",
				"/directives/**", "/controllers/**", "/img/**", "/lib/**", "/services/**",
				"/views/**", Constantes.URL_DENY, "/webjars/**", "/swagger-resources/**", "/swagger-ui.html",
				Constantes.URL_CORE + "/version","/v2/**" };
		http.authorizeRequests().antMatchers(resources).permitAll();
		
		
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/dologin*").permitAll()
		.anyRequest().authenticated();
		http.formLogin()
			.loginPage(Constantes.URL_DENY).loginProcessingUrl("/dologin")
			.successForwardUrl(Constantes.URL_LOGINOK).failureForwardUrl(Constantes.URL_DENY);
		http.logout().deleteCookies("JSESSIONID", "rm", "SESSION");

		//http.rememberMe().alwaysRemember(true)
		//.rememberMeParameter("rm").tokenValiditySeconds(3600);

		http.httpBasic();

		
		http.addFilterAfter(new CustomTokenAuthenticationFilter(authTokenService, usuariosDAO),
				UsernamePasswordAuthenticationFilter.class);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Autowired
	private IAuthTokenBusiness authTokenService;
	@Autowired
	private UsuarioRepository usuariosDAO;


	@Bean
	public static PasswordEncoder passwordEncoder() {
		//return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		//auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("123")).roles("ADMINS").and()
		//		.withUser("user").password(passwordEncoder().encode("123")).roles("USERS");

	}

}
