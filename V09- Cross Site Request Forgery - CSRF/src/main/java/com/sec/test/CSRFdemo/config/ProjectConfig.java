package com.sec.test.CSRFdemo.config;

import com.sec.test.CSRFdemo.sec.CsrfTokenLoggerFilter;
import com.sec.test.CSRFdemo.sec.CustomCsrfRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
//        http.csrf().disable();
        http.csrf(c -> {
            c.ignoringAntMatchers("/csrfdisabled/**");
            c.csrfTokenRepository(new CustomCsrfRepository());
        });
        http.addFilterAfter(new CsrfTokenLoggerFilter(), CsrfFilter.class);

    }
}
