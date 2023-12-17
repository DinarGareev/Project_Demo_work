package com.example.demo_work.security;

import com.example.demo_work.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class PersonDetails implements UserDetails {
    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // этот метод возвращет роль человека или список действий в виде коллекций, в нашей коллкции который будем
        // возвращать будет только один элемент роль текущего человека, и уже спринг будет понимать, у текущего пользователя такаяаторити такая роль
        // и соответственно на основании этой роли мы сможем разгроничивать доступ
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
        //лист из одного элемента , и сюда мы помещаем Authority текущего человека, его роль(user  or admin)
        // Это просто объект класа SimpleGrantedAuthority которому в качестве аргумента в конструктор мы передали строку
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public Person getPerson(){
        return this.person;
    }
}
