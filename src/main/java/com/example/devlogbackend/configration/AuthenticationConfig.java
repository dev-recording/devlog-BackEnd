package com.example.devlogbackend.configration;



        import lombok.RequiredArgsConstructor;
        import org.hibernate.jpa.HibernatePersistenceProvider;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.http.HttpMethod;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
        import org.springframework.security.config.http.SessionCreationPolicy;
        import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

        import javax.persistence.EntityManagerFactory;
        import javax.sql.DataSource;


//시큐리티를 만듬으로써 인증을 만들어준거임
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests()
                .antMatchers("/github/login").permitAll()
                .antMatchers(HttpMethod.POST, "login").authenticated()

                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // JwtAuthenticationFilter를 사용하여 인증 처리


        // OAuth2 로그인을 위한 설정
        http
                .oauth2Login()
                .loginPage("/github/login") // GitHub 로그인 페이지 URL
                .defaultSuccessUrl("/github/callback") // 로그인 성공 후 리디렉션될 URL
                .and()
                .logout()
                .logoutSuccessUrl("/"); // 로그아웃 후 리디렉션될 URL
    }



    @Autowired
    private DataSource dataSource; // DataSource 주입

    /*@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.example.devlogbackend.entity");
        emf.setPersistenceProviderClass(HibernatePersistenceProvider.class); // Hibernate 사용 설정
        // Hibernate의 naming_strategy 설정 추가 (선택 사항)
        // emf.getJpaPropertyMap().put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        return emf;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }*/
 /*   @Bean
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
                              *//* .addFilterBefore(new JwtFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)*//*
                .build();
    }*/


}






