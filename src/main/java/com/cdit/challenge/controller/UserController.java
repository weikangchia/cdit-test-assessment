package com.cdit.challenge.controller;

import com.cdit.challenge.externalDto.UserResults;
import com.cdit.challenge.model.User;
import com.cdit.challenge.repository.UserRepository;
import com.cdit.challenge.specification.UserSpecification;
import com.cdit.challenge.util.UserUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity getUsers() {
        try {
            List<User> users = userRepository.findAll(UserSpecification.hasValidSalary());

            UserResults userResults = new UserResults();
            userResults.setResults(users);

            return ResponseEntity.ok(userResults);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/users/upload", method = RequestMethod.POST)
    public ResponseEntity uploadUsers(@RequestParam("file") MultipartFile userUploadFile) {
        if (!userUploadFile.isEmpty()) {
            try {
                CSVParser userParseResults = CSVFormat.EXCEL.withHeader().parse(new InputStreamReader((userUploadFile.getInputStream())));
                List<User> users = UserUtil.parseCsvResults(userParseResults);
                userRepository.saveAll(users);
            } catch (Exception ex) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
