package br.com.casadocodigo.loja.conf;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//import br.com.casadocodigo.loja.daos.UsuarioDAO;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
//    @Autowired
//    private UsuarioDAO usuarioDao;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//        .antMatchers("/produtos/form").hasRole("ADMIN")
//        .antMatchers("/carrinho/**").permitAll()
//        .antMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
//        .antMatchers(HttpMethod.GET, "/produtos").hasRole("ADMIN")
//        .antMatchers("/produtos/**").permitAll()
//        .antMatchers("/").permitAll()
//        .anyRequest().authenticated()
//        .and().formLogin();
//    }
//    
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(usuarioDao)
//        .passwordEncoder(new BCryptPasswordEncoder());
//    }

}
