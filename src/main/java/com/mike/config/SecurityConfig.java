package com.mike.config;

import com.mike.Handler.JwtAuthenticationTokenFilter;
import com.mike.service.LoginUserDetailsService;
import com.mike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @Author: 23236
 * @createTime: 2022/12/15   21:56
 * @description: 安全认证和授权的学习
 **/

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private LoginUserDetailsService loginUserDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


//    第一种方式是在properties文档里配置security

//    第二种方式
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        auth.inMemoryAuthentication().withUser("mm").password(bCryptPasswordEncoder.encode("....")).roles("admin");
//    }

//    第三种方式
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("Authenticationconfig进来了");
        auth.userDetailsService(loginUserDetailsService).passwordEncoder(passwordEncoder());
//        auth.userDetailsService(userService);
//        auth.inMemoryAuthentication();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
        web.ignoring().antMatchers("toLogin","/login","/logout","/resources/**",
                "/css/**","/fonts/**","/images/**","/js/**","/**/img/**")
                ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//                登录跳转指定的画面.formLogin()
        http.formLogin()
                .loginPage("/toLogin")
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
//                .httpBasic().and()
//        formLogin对应着你form表单认证方式，即UsernamePasswordAuthenticationFilter。
//        httpBasic对应着Basic认证方式，即BasicAuthenticationFilter。
                .rememberMe()
                .rememberMeParameter("remember-me") // 修改请求参数名。 默认是remember-me
                .tokenValiditySeconds(14*24*60*60) // 设置记住我有效时间。单位是秒。默认是14天
                .rememberMeCookieName("remember-me") // 修改remember me的cookie名称。默认是remember-me
                .tokenRepository(persistentTokenRepository) // 配置用户登录标记的持久化工具对象。
                .userDetailsService(loginUserDetailsService); // 配置自定义的UserDetailsService接口实现类对象
        ;

        http.logout()
                .logoutUrl("/toLogout").permitAll();

//        http.addFilterBefore(jwtAuthenticationTokenFilter(),
//                UsernamePasswordAuthenticationFilter.class);  //JwtAuthenticationTokenFilter 放在 UsernamePasswordAuthenticationFilter 之前
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/resources/**","/**/*.css","/**/*.js","/imgs/**","/**/*.jpg").permitAll() //放行静态资源
                .regexMatchers(".*[.]css").permitAll() // 授予所有目录下的所有.css文件可访问权限
                .antMatchers("/user/getEmployeesList")
                .hasAnyRole("ADMIN","manager","role")
                .anyRequest().authenticated()
                ;

        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable();  //禁用跨站csrf攻击防御//关闭防火墙：为了保证完整流程可用关闭CSRF安全协议
    }


//    // 1 创建UsernamePasswordAuthenticationToken
//    UsernamePasswordAuthenticationToken usernameAuthentication = new UsernamePasswordAuthenticationToken(loginAccount, password);
//    // 2 认证
//    Authentication authentication = this.authenticationManager.authenticate(usernameAuthentication);
//    // 3 保存认证信息
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    // 4 生成自定义token
//    UserDetail userDetail = (UserDetail) authentication.getPrincipal();
//    AccessToken accessToken = jwtProvider.createToken((UserDetails) authentication.getPrincipal());
//
//    // 5 放入缓存
//        caffeineCache.put(CacheName.USER, userDetail.getUsername(), userDetail);
//        return ApiResult.ok(accessToken);
//



    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }


}
