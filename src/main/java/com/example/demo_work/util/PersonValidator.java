package com.example.demo_work.util;

import com.example.demo_work.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final UserDetailsService userDetailsService;

    @Autowired
    public PersonValidator(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {  // класс который показывает к какому классу относится валидатор
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        try {
            userDetailsService.loadUserByUsername(person.getUsername()); // смотрит пришел ли имя с объекта
        }catch (UsernameNotFoundException ignored){
            return; // выброшена ошибка, т.е объекта с таким именем не найдено, т.е все хорошо. (можно проверить через optional)
        }
        errors.rejectValue("username", "","Пользователь с таким именем уже существует");
    }
}
