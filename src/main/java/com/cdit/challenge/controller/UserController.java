package com.cdit.challenge.controller;

import com.cdit.challenge.externalDto.UserResults;
import com.cdit.challenge.model.User;
import com.cdit.challenge.repository.UserRepository;
import com.cdit.challenge.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public UserResults getUsers() {
        List<User> users = userRepository.findAll(UserSpecification.hasValidSalary());

        UserResults userResults = new UserResults();
        userResults.setResults(users);

        return userResults;
    }
}
