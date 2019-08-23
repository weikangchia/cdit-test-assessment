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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    // getUsers
    @MockBean
    private UserRepository userRepository;

    @Test
    public void getUsers_ShouldReturnHttpOk_WhenUserRepoReturnEmpty() throws Exception {
        List<User> emptyUsers = new ArrayList<>();

        when(userRepository.findAll(UserSpecification.hasValidSalary())).thenReturn(emptyUsers);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUsers_ShouldReturnEmptyJson_WhenUserRepoReturnEmpty() throws Exception {
        List<User> emptyUsers = new ArrayList<>();

        UserResults expectedEmptyUserResults = new UserResults();
        expectedEmptyUserResults.setResults(emptyUsers);

        when(userRepository.findAll(UserSpecification.hasValidSalary())).thenReturn(emptyUsers);

        mockMvc.perform(get("/users"))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedEmptyUserResults)));
    }

    @Test
    public void getUsers_ShouldReturnHttpOk_WhenUserRepoReturn1User() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("John", 100000));

        when(userRepository.findAll(UserSpecification.hasValidSalary())).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUsers_ShouldReturn1UserResult_WhenUserRepoReturn1User() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("John", 100000));

        UserResults expectedUserResults = new UserResults();
        expectedUserResults.setResults(users);

        when(userRepository.findAll(UserSpecification.hasValidSalary())).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedUserResults)));
    }

    @Test
    public void getUsers_ShouldReturnHttpServerError_WhenUserRepoThrowError() throws Exception {
        when(userRepository.findAll(UserSpecification.hasValidSalary())).thenThrow();
        mockMvc.perform(get("/users"))
                .andExpect(status().isInternalServerError());
    }

    // uploadUsers
    @Test
    public void uploadUsers_ShouldReturnHttpBadRequest_WhenUserFileIsEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload")
                .file(new MockMultipartFile("file", "empty.csv", "text/plain", "".getBytes())))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json("{'message':'File is empty'}"));
    }

    @Test
    public void uploadUsers_ShouldReturnOk_WhenUserFileIsValid() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("Jack", 100));

        when(userRepository.saveAll(any(ArrayList.class))).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload")
                .file(new MockMultipartFile("file", "user.csv", "text/plain", "name,salary\nJack,100".getBytes())))
                .andExpect(status().isOk());
    }

    @Test
    public void uploadUsers_ShouldReturnUploadedUserSize_WhenUserFileIsValid() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("Jack", 100));

        when(userRepository.saveAll(any(ArrayList.class))).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload")
                .file(new MockMultipartFile("file", "user.csv", "text/plain", "name,salary\nJack,100".getBytes())))
                .andExpect(content().json("{'message':'Uploaded 1 users'}"));
    }
}
