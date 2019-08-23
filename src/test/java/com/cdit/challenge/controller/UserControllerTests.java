package com.cdit.challenge.controller;

import com.cdit.challenge.externalDto.UserResults;
import com.cdit.challenge.model.User;
import com.cdit.challenge.repository.UserRepository;
import com.cdit.challenge.specification.UserSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void shouldReturnHttpOk_WhenUserRepoReturnEmpty() throws Exception {
        List<User> emptyUsers = new ArrayList<>();

        when(userRepository.findAll(UserSpecification.hasValidSalary())).thenReturn(emptyUsers);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnEmptyJson_WhenUserRepoReturnEmpty() throws Exception {
        List<User> emptyUsers = new ArrayList<>();

        UserResults expectedEmptyUserResults = new UserResults();
        expectedEmptyUserResults.setResults(emptyUsers);

        when(userRepository.findAll(UserSpecification.hasValidSalary())).thenReturn(emptyUsers);

        mockMvc.perform(get("/users"))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedEmptyUserResults)));
    }

    @Test
    public void shouldReturnHttpOk_WhenUserRepoReturn1User() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("John", 100000));

        when(userRepository.findAll(UserSpecification.hasValidSalary())).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn1UserResult_WhenUserRepoReturn1User() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("John", 100000));

        UserResults expectedUserResults = new UserResults();
        expectedUserResults.setResults(users);

        when(userRepository.findAll(UserSpecification.hasValidSalary())).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedUserResults)));
    }

    @Test
    public void shouldReturnHttpServerError_WhenUserRepoThrowError() throws Exception {
        when(userRepository.findAll(UserSpecification.hasValidSalary())).thenThrow();
        mockMvc.perform(get("/users"))
                .andExpect(status().isInternalServerError());
    }
}
