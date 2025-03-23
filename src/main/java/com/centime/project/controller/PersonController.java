package com.centime.project.controller;

import com.centime.project.annotation.LogMethodParam;
import com.centime.project.entity.Person;
import com.centime.project.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;


    @GetMapping("/health")
    public ResponseEntity<?> testHealth(){

        return ResponseEntity.ok("Up");
    }

    @PostMapping
    public ResponseEntity<?> createPerson(@RequestBody Person person) {

        Person personCreated = personService.savePerson(person);
        return new ResponseEntity<>(personCreated.getId(), HttpStatus.CREATED);
    }

    @LogMethodParam
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Optional<Person> person = personService.findById(id);
        if(person.get()!=null){
            return new ResponseEntity<>(person.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {

        List<Person> personList = personService.findAllPerson();
        if(!CollectionUtils.isEmpty(personList)){
            return new ResponseEntity<>(personList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @LogMethodParam
    @GetMapping("/nested")
    public ResponseEntity<List<Map<String, Object>>> findAllNestedResponse() {

        List<Map<String, Object>> nestedPerson = personService.getNestedPerson();

        if(!nestedPerson.isEmpty()){
            return new ResponseEntity<>(nestedPerson, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}

