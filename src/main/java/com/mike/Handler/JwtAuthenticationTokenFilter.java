package com.mike.Handler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.mike.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @Author: 23236
 * @createTime: 2022/12/28   17:51
 * @description:
 **/
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

//    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
//    @Value("${jwt.tokenHead}")
    private String tokenHead;

    public JwtAuthenticationTokenFilter() {
        //拦截header中带Authorization的请求
        RequestHeaderRequestMatcher authorization = new RequestHeaderRequestMatcher("Authorization");
    }

    protected String getJwtToken(HttpServletRequest request) {
        String authInfo = request.getHeader("Authorization");
        return StringUtils.removePrefixAfterPrefixToLower(authInfo, 6);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("jwtauth进来了");
        Date date = new Date(System.currentTimeMillis());
        Algorithm algorithm = Algorithm.HMAC256("....");
        // 附带username信息
         String token=JWT.create()
                .withClaim("username", "mike")
                .withExpiresAt(date)
                .sign(algorithm)
                 ;
        System.out.println("token="+token);

        String username = jwtUtil.getUserNameByToken(request);
        System.out.println(username);



        // 从请求头中获取token
//        String authHeader = request.getHeader("token");
        // 截取token
//        if (authHeader != null || authHeader.startsWith("token")) {
            // token前面的"Bearer "需要截取
//            String authToken = authHeader.substring(this.tokenHead.length());
//            System.out.println("authToken:" + authHeader);
            //验证token,获取token中的username
//            Claims claims = jwtUtil.verifyJwt(authHeader);


            // 校验该token是否过期
//            String redisToken = (String) redisService.get(username);
//            if (redisToken == null) {
//                throw new AppException("token已经过期，请重新登录");
//            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
//        }
        filterChain.doFilter(request, response);
    }

//    @Autowired
//    private JwtProvider jwtProvider;
//    @Autowired
//    private JwtProperties jwtProperties;
//    @Autowired
//    private Cache caffeineCache;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain chain) throws ServletException, IOException {


        // 拿到Authorization请求头内的信息
//        String authToken = jwtProvider.getToken(request);
//
//        // 判断一下内容是否为空
//        if (StrUtil.isNotEmpty(authToken) && authToken.startsWith(jwtProperties.getTokenPrefix())) {
//            // 去掉token前缀(Bearer )，拿到真实token
//            authToken = authToken.substring(jwtProperties.getTokenPrefix().length());
//
//            // 拿到token里面的登录账号
//            String loginAccount = jwtProvider.getSubjectFromToken(authToken);
//
//            if (StrUtil.isNotEmpty(loginAccount) && SecurityContextHolder.getContext().getAuthentication() == null) {
//                // 查询用户
//                UserDetail userDetails = caffeineCache.get(CacheName.USER, loginAccount, UserDetail.class);
//
//                // 拿到用户信息后验证用户信息与token
//                if (userDetails != null && jwtProvider.validateToken(authToken, userDetails)) {
//
//                    // 组装authentication对象，构造参数是Principal Credentials 与 Authorities
//                    // 后面的拦截器里面会用到 grantedAuthorities 方法
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
//
//                    // 将authentication信息放入到上下文对象中
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                    log.info("JWT过滤器通过校验请求头token自动登录成功, user : {}", userDetails.getUsername());
//                }
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
}