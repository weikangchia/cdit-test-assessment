package com.cdit.challenge.externalDto;

import com.cdit.challenge.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UserResultsTests {

    @Test
    public void shouldReturnNullResult_WhenIsNull() throws JsonProcessingException {
        UserResults userResults = new UserResults();
        userResults.setResults(null);

        String actualResult = new ObjectMapper().writeValueAsString(userResults);;
        String expectedResult = "{\"results\":null}";

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    public void shouldReturnEmptyResult_WhenThereIs0User() throws JsonProcessingException {
        List<User> emptyUsers = new ArrayList<>();

        UserResults userResults = new UserResults();
        userResults.setResults(emptyUsers);

        String actualResult = new ObjectMapper().writeValueAsString(userResults);;
        String expectedResult = "{\"results\":[]}";

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    public void shouldReturnResultWithArray_WhenThereIs1User() throws JsonProcessingException {
        List<User> users = new ArrayList<>();
        users.add(new User("John", 100000));

        UserResults userResults = new UserResults();
        userResults.setResults(users);

        String actualResult = new ObjectMapper().writeValueAsString(userResults);;
        String expectedResult = "{\"results\":[{\"name\":\"John\",\"salary\":1000.00}]}";

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    public void shouldReturnResultWithArray_WhenThereIs2User() throws JsonProcessingException {
        List<User> users = new ArrayList<>();
        users.add(new User("John", 100000));
        users.add(new User("Mary Posa", 250005));

        UserResults userResults = new UserResults();
        userResults.setResults(users);

        String actualResult = new ObjectMapper().writeValueAsString(userResults);;
        String expectedResult = "{\"results\":[{\"name\":\"John\",\"salary\":1000.00},{\"name\":\"Mary Posa\",\"salary\":2500.05}]}";

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
