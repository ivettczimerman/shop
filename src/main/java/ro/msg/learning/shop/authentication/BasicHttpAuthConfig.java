package ro.msg.learning.shop.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ro.msg.learning.shop.model.UserType;
import ro.msg.learning.shop.service.UserService;

@Configuration
@Order(2)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class BasicHttpAuthConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    @Autowired
    public BasicHttpAuthConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().hasAuthority(UserType.CUSTOMER.name())
                .and()
                .httpBasic()
                .and()
                .headers().frameOptions().sameOrigin();
    }
}
