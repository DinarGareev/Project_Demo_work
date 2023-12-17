package com.example.demo_work.controller;

import com.example.demo_work.model.Person;
import com.example.demo_work.repository.PeopleRepository;
import com.example.demo_work.security.PersonDetails;
import com.example.demo_work.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author Neil Alishev
 */
@Controller
public class HelloController {
    private final PeopleRepository peopleRepository;
    private final PersonDetailsService personDetailsService;

    @Autowired
    public HelloController(PeopleRepository peopleRepository, PersonDetailsService personDetailsService) {
        this.peopleRepository = peopleRepository;
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());

        return "hello";
    }

    //Будем отдавать представление где будет находиться наша форма(кастомная аутентификация)

    @GetMapping("/admin")
    public String adminPage(){
        return "admin";
    }

     @GetMapping("/admin/personAll")
    public String findAll(Model model){
        List<Person> persons = personDetailsService.findAll();
        model.addAttribute("persons",persons);
        return "personList";
    }

   /* @GetMapping("/admin/create")
    public String createPerson(@ModelAttribute("person") Person person){
        return "createPersons";
    }

    @PostMapping("/admin/create")
    public String postCreatePerson(@ModelAttribute("person") Person person){
        personDetailsService.savePerson(person);
        return "redirect:/admin/personAll";
    }*/

    @GetMapping("/admin/delete/{id}")
    public String deletePerson(@PathVariable("id") Integer id){
        personDetailsService.deleteById(id);
        return "redirect:/admin/personAll";
    }

    /*@GetMapping("/admin/personUpdate/{id}")
    public String updatePerson(@PathVariable("id") Integer id, Model model){
        Person person = personDetailsService.findById(id); // ищем пользователя
        model.addAttribute("person", person);  // изменяем
        return "personUpdate";
    }

    @PostMapping("/admin/personUpdate")
    public String updatePerson(@ModelAttribute("person") Person person){
        personDetailsService.savePerson(person);
        return "redirect:/admin/personAll";
    }*/
















































}