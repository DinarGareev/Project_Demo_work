package com.example.demo_work.services;

import com.example.demo_work.model.Person;
import com.example.demo_work.repository.PeopleRepository;
import com.example.demo_work.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(s);
        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return new PersonDetails(person.get());
    }

       public Person findById(Integer id){
        return peopleRepository.getOne(id);
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person savePerson(Person person){
        return peopleRepository.save(person);
    }

    public void deleteById(Integer id){
        peopleRepository.deleteById(id);
    }

}
