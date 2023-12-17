package com.example.demo_work.config;

import com.example.demo_work.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }
    //настраиваем какую форму использовать для логина
    //конфигурируем сам spring security, т.е какая страница отвечает за вход, ошибки и т.д конфигурируем авторизацию в будущем в этом методе мы уже будем или не
    // давать доступ к определенным страницам на основании его ролей в этот метод поступает http запрос и мы смотрим что это за запрос - этот запрос поступает от
    // аутентифицированного пользователя или нет, и соответственно с помощью этого мы настраиваем авторизацию покажем spring security что мы хотим использовать
    // свою страницу аутентификации, для этого обращаемся к аргументу и вызываем метод formLogin(), т.е мы хотим у spring security настроить форму для логина
    //пишем loginPage("") и передаем тот адрес который должен вести на страницу с логаном и сюда же должны добавить конфигурацию и вызвать метод loginProsseingUrl("") и
    // в аргументе пишем
    // название того адреса куда мы хотим отправлять с формы, т.е в этом URL spring security будет ждать введенные имя и пароль для аутентификации что бы сравнить
    // в нашем случае с базой данных и дальше мы говорим что после успешной аутентификации spring security перенаправил нас на указанную страницу
    // - defaultSouccessUrl("/..., true") и тут вторым аргументом укажем true, т.е укажем чтоб он нас перенаправлял после успеха всегда на эту страницу
    // И даллее с помощью метода failueUrl("/auth/login?error") и указываем url. В нашем случае мы говорим url  и в параметрах запроса "?" - указываем - error - этот
    // параметр придет в наш контроллер в /auth/login  - параметр также будет передан на представление и на представлении с помощью таймлифа увидим этот
    // параметр этот ключ error и выведем сообщение которое там написали
    //
    //В своей созданной странице надо самим настроить аутентификацию, чтобы ко все страничкам кроме login у
    // у неаутифицированного пользавателя не было доступа. И уже надо начать делать авторизацию чтобы неатуфицированного
    // пользавателя не допускать к страницам. До того как мы сконфигурируем нашу аутентификацию, сделае авторизацию. для этого после http. вызовем authoriesRequest -
    // и теперь все запросы которые приходят со страницы они проходят авторизацию. Дальше мы используем andMatchers() - чтобы смотреть какой запрос
    // пришел к нам в приложение. И здесь мы указываем куда можно впускать без авторизации пользователя andMatchers("/auth/login",/error), т.е мы тут указали,
    // что эти страницы доступны всем. И дальше пишем PermitAll - т.е всех людей мы на эти две страницы впускаем. После этого мы указываем,
    // что все остальные страницы мы не впускаем неаутинфицированных пользователей - пишем anyRequest() - метод обозначающий любой запрос(на все остальные запросы)
    //и далее anyRequest().authenticated - обозначает на все любые запросы пользователь должен быть аутенфицирован. И далее чтоб перейти на другую настройку
    // например аутентификации мы пишем .and
    //Правила сначала идут более специфичные настройки потом уже другие. Т.е все читается сверху вниз.
    //
    //logout().logoutUrl("/login") - при переходе на этот url у человека будет производиться logout, т.е при переходе на эту страницу у человека
    // будет стираться сессия и кукис.прекращается аутентификация, соответственно этот человек будет разлогинин.
    //даллее пишем logoutSuccessUrl("/auth/login"), это тот URL на который будет переходить пользователь при успешном разлогировании
    ////.csrf().disable()
    //добавим токен в форму аутентификации
    //если csrf включен мы можем разлогиниться только с помощью POST запроса
    @Override
    protected void configure(HttpSecurity http) throws Exception {
http.authorizeRequests()
        .antMatchers("/admin","/admin/**").hasRole("ADMIN") // для данного url роль админа
        .antMatchers("/auth/login","/auth/registration","/error").permitAll()
        .anyRequest().hasAnyRole("ADMIN","USER")
       // .anyRequest().authenticated() - это убрали, сделали роли
        .and()
        .formLogin().loginPage("/auth/login").loginProcessingUrl("/process login")
        .defaultSuccessUrl("/hello",true)
        .failureUrl("/auth/login?error")
        .and()
        .logout().logoutUrl("/logout")
        .logoutSuccessUrl("/auth/login");

    }
    //настраиваем аутентификацию
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(personDetailsService)
                 .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
