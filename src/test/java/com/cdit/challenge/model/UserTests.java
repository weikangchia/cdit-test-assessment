package com.cdit.challenge.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UserTests {

    @Test
    public void shouldSerializeSalaryIn2Dp_WhenSalaryIsDivisibleBy100() throws JsonProcessingException {
        String actualResult = new ObjectMapper().writeValueAsString(new User("John", 100000));
        String expectedResult = "{\"name\":\"John\",\"salary\":1000.00}";
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    public void shouldSerializeSalaryIn2Dp_WhenSalaryIsNotDivisbleBy100() throws JsonProcessingException {
        String actualResult = new ObjectMapper().writeValueAsString(new User("John", 250005));
        String expectedResult = "{\"name\":\"John\",\"salary\":2500.05}";
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    public void shouldSerializeSalaryIn2Dp_WhenSalaryIs0() throws JsonProcessingException {
        String actualResult = new ObjectMapper().writeValueAsString(new User("John", 0));
        String expectedResult = "{\"name\":\"John\",\"salary\":0.00}";
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    public void shouldSerializeSalaryIn2Dp_WhenSalaryIsPositive() throws JsonProcessingException {
        String actualResult = new ObjectMapper().writeValueAsString(new User("John", 100000));
        String expectedResult = "{\"name\":\"John\",\"salary\":1000.00}";
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    public void shouldSerializeSalaryIn2Dp_WhenSalaryIsNegative() throws JsonProcessingException {
        String actualResult = new ObjectMapper().writeValueAsString(new User("John", -100000));
        String expectedResult = "{\"name\":\"John\",\"salary\":-1000.00}";
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
