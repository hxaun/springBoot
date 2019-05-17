package com.huajiance.springboot.config.springSecurity;

import com.huajiance.springboot.entity.TMenu;
import com.huajiance.springboot.service.MenuService;
import com.huajiance.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.*;

/*
 * spring security配置类
 * */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    protected SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    protected SimpleUrlAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomUserDetailsService userDetailsService;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterAfter(dynamicallyUrlInterceptor(), FilterSecurityInterceptor.class)
            .authorizeRequests()
            .antMatchers( "/login.html").permitAll()
            .anyRequest().authenticated().and()
            .formLogin().loginPage("/login.html") // 设置登录页面
            .loginProcessingUrl("/login") // 自定义的登录接口
//            .defaultSuccessUrl("/index.html")
            .failureUrl("/error")
            .permitAll() //登录页面用户任意访问
            .successHandler(authenticationSuccessHandler) // 认证成功回调
            .failureHandler(authenticationFailureHandler) // 认证失败回调
            .and().userDetailsService(userDetailsService)
            .csrf().disable() // 关闭csrf防护
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/assets/**","/css/**","/images/**");
    }

    @Bean
    public DynamicallyUrlInterceptor dynamicallyUrlInterceptor(){
        DynamicallyUrlInterceptor interceptor = new DynamicallyUrlInterceptor();
        interceptor.setSecurityMetadataSource(new MyFilterSecurityMetadataSource());

        //配置RoleVoter决策
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();
        decisionVoters.add(new RoleVoter());
        //设置认证决策管理器
        interceptor.setAccessDecisionManager(new DynamicallyUrlAccessDecisionManager(decisionVoters));
        return interceptor;
    }


}