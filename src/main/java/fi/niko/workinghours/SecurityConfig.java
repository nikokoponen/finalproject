package fi.niko.workinghours;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fi.niko.workinghours.web.UserDetailServices;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailServices userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/css/**").permitAll() // Enable
																	// css when
																	// logged
																	// out
				.and().authorizeRequests()
				.antMatchers("/signup", "/saveuser", "/companylist", "/company/**", "/departmentlist", "/department/**",
						"/employeelist", "/employee/**", "/projectlist", "/project/**", "/projectmemberlist",
						"/projectmember/**", "/workhourlist", "/workhours/**","/userlist","/user/**")
				.permitAll().and().authorizeRequests().anyRequest().authenticated().and().formLogin()
				.loginPage("/login").defaultSuccessUrl("/companies").permitAll().and().logout().permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
