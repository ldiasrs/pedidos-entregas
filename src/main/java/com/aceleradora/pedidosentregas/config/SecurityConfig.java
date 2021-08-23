package com.aceleradora.pedidosentregas.config;

import com.aceleradora.pedidosentregas.config.filters.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

import static java.util.Collections.singletonList;

/**
 *
 * -- FILTERS
 * Filters que podemo ser configurados antes e depois de cada request
 * addFilterBefore
 * addFilterAfter
 * addFilterAt
 *
 * Veja classes GenericFilterBean and OncePerRequestFilter
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        defineCorsConfig(http);
        defineAthorizationConfig(http);
        disableCsrf(http);
        //defineJWTAccessTokenConfiguration(http);
        //enableCsrfCookieToken(http);
    }


    /**
     * User Authentication
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return defineInMemoryUserDetailsConfig();
    }


    @Bean
    public PasswordEncoder definePasswordEncoder(){
        //define um encoder que ira criar uma hash da senha para comparacao e store
        //na teoria essa hash nao pode mais recriar a senha original
        return new BCryptPasswordEncoder();
    }

    private UserDetailsService defineInMemoryUserDetailsConfig() {
        /**
         * Usuando usuarios é possivel configurar
         * 1) Quais usuarios sao permitidos com suas determinadas senhas
         * 2) Autority que define o que cada usuário pode fazer org.springframework.security.core.authority.SimpleGrantedAuthority
         * 3) Roles é um conjunto de Autority exemplo: READ, WRITE, DELETE
         */

        //apos autenticacao Spring gera um token para a sessao do usuário
        UserDetails user =
                User.builder()
                        .username("myuser")
                        //senhalocal usando https://bcrypt-generator.com/ 12
                        .password("$2y$12$XiDW0q0NYMuyw2on9SuEOONIhKMTu9osQbG2YEKXPxR7QghqNNBI6")
                        .roles("USER")
                        .build();
        //apos autenticacao Spring gera um token para a sessao do usuário
        UserDetails admin =
                User.builder()
                        .username("admin")
                        //senhalocal usando https://bcrypt-generator.com/ 12
                        .password("$2y$12$XiDW0q0NYMuyw2on9SuEOONIhKMTu9osQbG2YEKXPxR7QghqNNBI6")
                        .roles("USER", "ADMIN")
                        .build();
        //Spring Security prove algumas classes de JdbcUserDetailsManager que ja tem o BD schema de usuarios e grupos
        return new InMemoryUserDetailsManager(user, admin);
    }

    private void defineAthorizationConfig(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //permite acesso ao /info sem autenticacao
                .antMatchers("/api/pedidoentrega/info").permitAll()
                .antMatchers("/api/pedidoentrega/admin").hasAnyRole( "ADMIN")
                //o resto do acesso dos pedidosentrega precisam de autenticacao
                .antMatchers("/api/pedidoentrega/**").authenticated()
                .and().formLogin()
                .and().httpBasic();

        //o restante de todos os acesso sao negados
        http.authorizeRequests().antMatchers("/**").denyAll();
    }

    private HttpSecurity enableCsrfCookieToken(HttpSecurity http) throws Exception {
        /**
         * Toda a vez que uma requisicao nova for recebida
         * Um token será gerado e passado através do Cookie: XSRF-TOKEN
         * withHttpOnlyFalse - garante que o Token possa ser lido pela aplicacao
         * Note que a applicao cliente agora tem que passar para toda a requisicao esse token de volta
         *      Exemplo: o cliente precisa mandar de volta: X-XSRF-TOKEN:<TOKEN VALUE>
         * ignoringAntMatchers - ignora a verificacao do token para esses padroes de path
         */
        http.csrf()
                .ignoringAntMatchers("/api/pedidoentrega/info")
                .csrfTokenRepository(
                        CookieCsrfTokenRepository.withHttpOnlyFalse());
        return http;
    }


    private void defineJWTAccessTokenConfiguration(HttpSecurity http) {
        http
                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class);
    }

    private HttpSecurity disableCsrf(HttpSecurity http) throws Exception {
        /**
         * https://en.wikipedia.org/wiki/Cross-site_request_forgery
         * In a CSRF attack, the attacker's goal is to cause an innocent victim to unknowingly
         * submit a maliciously crafted web request to a website that the victim has privileged
         * access to. This web request can be crafted to include URL parameters,
         * cookies and other data that appear normal to the web server processing the request.
         * At risk are web applications that perform actions based on input from trusted and authenticated
         * users without requiring the user to authorize the specific action. A user who is authenticated
         * by a cookie saved in the user's web browser could unknowingly send an HTTP request to a site that
         * trusts the user and thereby causes an unwanted action.
         */
        return http.csrf().disable();
    }

    /**
     * https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS
     *
     * Cross-Origin Resource Sharing (CORS) is an HTTP-header based mechanism that allows a server
     * to indicate any origins (domain, scheme, or port) other than its own from which a browser should
     * permit loading of resources. CORS also relies on a mechanism by which browsers make a "preflight"
     * request to the server hosting the cross-origin resource, in order to check that the server will
     * permit the actual request. In that preflight, the browser sends headers that indicate the HTTP
     * method and headers that will be used in the actual request.
     *
     * An example of a cross-origin request: the front-end JavaScript code served from
     * https://domain-a.com uses XMLHttpRequest to make a request for https://domain-b.com/data.json.
     *
     * For security reasons, browsers restrict cross-origin HTTP requests initiated from scripts.
     * For example, XMLHttpRequest and the Fetch API follow the same-origin policy.
     * This means that a web application using those APIs can only request resources from the same
     * origin the application was loaded from unless the response from other origins includes the right
     * CORS headers.
     */
    private HttpSecurity defineCorsConfig(HttpSecurity http) throws Exception {
        http.cors().configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                var configuration = new CorsConfiguration();
                //Apenas requisicoes vinda dessa URL serao aceitas
                configuration.setAllowedOrigins(singletonList("http://localhost:8080"));
                //Todos os metodos http POST, GET, DELETE etc serao aceitos
                configuration.setAllowedMethods((singletonList("*")));
                //Todos os cabecalhos do http serao aceitos
                configuration.setAllowedHeaders((singletonList("*")));
                //O cliente vai cachear essa config de cors por 3600 segundos = 1h até verificar novamente
                configuration.setMaxAge(3600L);
                //Aceita credinciais de segurança
                configuration.setAllowCredentials(true);
                return configuration;
            }
        });
        return http;
    }


}