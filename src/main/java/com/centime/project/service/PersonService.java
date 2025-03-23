package com.centime.project.service;

import com.centime.project.entity.Person;
import com.centime.project.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person savePerson(Person person) {

        return personRepository.save(person);
    }

    public Optional<Person> findById(Long id){

        return personRepository.findById(id);
    }

    public List<Person> findAllPerson(){

        return personRepository.findAll();
    }

    public List<Map<String, Object>> getNestedPerson() {

        List<Person> personList = personRepository.findAll();

        Map<Long, List<Person>> parentChildMap = personList.stream()
                .collect(Collectors.groupingBy(Person::getParentId));

        return personList.stream()
                .filter(person -> person.getParentId() == 0)  // Root elements
                .map(person -> buildHierarchy(person, parentChildMap))
                .collect(Collectors.toList());
    }

    private Map<String, Object> buildHierarchy(Person person, Map<Long, List<Person>> parentChildMap) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("Name", person.getName());

        List<Person> children = parentChildMap.get(person.getId());
        if (children != null && !children.isEmpty()) {
            List<Map<String, Object>> subClasses = children.stream()
                    .map(child -> buildHierarchy(child, parentChildMap))
                    .collect(Collectors.toList());
            result.put("Sub Classes", subClasses);
        }
        return result;
    }




}

