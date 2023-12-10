package com.example.devlogbackend.configration;


/*import com.example.devlogbackend.github.PrinciplalOauthUserService;*/
import com.example.devlogbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//시큐리티를 만듬으로써 인증을 만들어준거임
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig {
    private final UserService userService;
    @Value("${jwt.secret}")
    private String secretKey;
    @Autowired
    /*extends DefaultOAuth2UserService {


    private PrinciplalOauthUserService principlalOauthUserService;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeHttpRequests(auth->{
                    auth.antMatchers("/").permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login()
                .defaultSuccessUrl("/success")
                .userInfoEndpoint()
                .userService(principlalOauthUserService);

        return http.build();
    }
*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity

                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests()
                .antMatchers("/login", "/join").permitAll()
                .antMatchers(HttpMethod.POST, "login").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
 /*               .addFilterBefore(new JwtFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)*/
                .build();
    }
}






