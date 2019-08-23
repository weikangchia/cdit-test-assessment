package com.cdit.challenge.util;

import com.cdit.challenge.model.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UserUtilTests {

    // isValidSalary
    @Test
    public void isValidSalary_ShouldReturnTrue_WhenRawSalaryIsPositive() {
        assertThat(UserUtil.isValidSalary("10000")).isTrue();
    }

    @Test
    public void isValidSalary_ShouldReturnTrue_WhenRawSalaryIsNegative() {
        assertThat(UserUtil.isValidSalary("-10000")).isTrue();
    }

    @Test
    public void isValidSalary_ShouldReturnTrue_WhenRawSalaryIs0() {
        assertThat(UserUtil.isValidSalary("0")).isTrue();
    }

    @Test
    public void isValidSalary_ShouldReturnTrue_WhenRawSalaryHasLeadingSpace() {
        assertThat(UserUtil.isValidSalary(" 0")).isTrue();
    }

    @Test
    public void isValidSalary_ShouldReturnTrue_WhenRawSalaryHasTrailingSpace() {
        assertThat(UserUtil.isValidSalary("0 ")).isTrue();
    }

    @Test
    public void isValidSalary_ShouldReturnTrue_WhenRawSalaryHasBothLeadingAndTrailingSpace() {
        assertThat(UserUtil.isValidSalary("  0 ")).isTrue();
    }

    @Test
    public void isValidSalary_ShouldReturnFalse_WhenRawSalaryContainsNonIntegers() {
        assertThat(UserUtil.isValidSalary("123a")).isFalse();
    }

    @Test
    public void isValidSalary_ShouldReturnFalse_WhenRawSalaryHasDecimal() {
        assertThat(UserUtil.isValidSalary("200.00")).isFalse();
    }

    @Test
    public void isValidSalary_ShouldReturnFalse_WhenRawSalaryIsEmptyString() {
        assertThat(UserUtil.isValidSalary("")).isFalse();
    }

    @Test
    public void isValidSalary_ShouldReturnFalse_WhenRawSalaryIsNull() {
        assertThat(UserUtil.isValidSalary(null)).isFalse();
    }

    // isValidName
    @Test
    public void isValidName_ShouldReturnTrue_WhenRawNameIsValidString() {
        assertThat(UserUtil.isValidName("Mary")).isTrue();
    }

    @Test
    public void isValidName_ShouldReturnFalse_WhenRawNameIsNull() {
        assertThat(UserUtil.isValidName(null)).isFalse();
    }

    @Test
    public void isValidName_ShouldReturnFalse_WhenRawNameIsEmpty() {
        assertThat(UserUtil.isValidName("")).isFalse();
    }

    // parseRawSalary
    @Test
    public void parseRawSalary_ShouldReturnSalaryInInteger_WhenRawSalaryIsPositiveInteger() {
        assertThat(200).isEqualTo(UserUtil.parseRawSalary("200"));
    }

    @Test
    public void parseRawSalary_ShouldReturnSalaryInInteger_WhenRawSalaryIsNegativeInteger() {
        assertThat(-200).isEqualTo(UserUtil.parseRawSalary("-200"));
    }

    @Test
    public void parseRawSalary_ShouldReturnSalaryInInteger_WhenRawSalaryIsPositiveIntegerAndHasLeadingSpace() {
        assertThat(200).isEqualTo(UserUtil.parseRawSalary("  200"));
    }

    @Test
    public void parseRawSalary_ShouldReturnSalaryInInteger_WhenRawSalaryIsPositiveIntegerAndHasTrailingSpace() {
        assertThat(200).isEqualTo(UserUtil.parseRawSalary("200  "));
    }

    @Test
    public void parseRawSalary_ShouldReturnSalaryInInteger_WhenRawSalaryIsPositiveIntegerAndHasBothLeadingAndTrailingSpace() {
        assertThat(200).isEqualTo(UserUtil.parseRawSalary(" 200  "));
    }

    // parseRawName
    @Test
    public void parseRawName_ShouldReturnNameInString_WhenRawNameIsValid() {
        assertThat("John").isEqualTo(UserUtil.parseRawName("John"));
    }

    @Test
    public void parseRawName_ShouldReturnTrimmedNameInString_WhenRawNameHasLeadingSpace() {
        assertThat("John").isEqualTo(UserUtil.parseRawName("   John"));
    }

    @Test
    public void parseRawName_ShouldReturnTrimmedNameInString_WhenRawNameHasTrailingSpace() {
        assertThat("John").isEqualTo(UserUtil.parseRawName("John  "));
    }

    @Test
    public void parseRawName_ShouldReturnTrimmedNameInString_WhenRawNameHasBothLeadingAndTrailingSpace() {
        assertThat("John").isEqualTo(UserUtil.parseRawName(" John  "));
    }

    // parseCsvResults
    @Test
    public void parseCsvResults_ShouldReturn0_WhenCSVOnlyHasHeader() throws IOException {
        CSVParser userParseResults = CSVParser.parse("name,salary\n", CSVFormat.DEFAULT);
        List<User> parsedUsers = UserUtil.parseCsvResults(userParseResults);

        assertThat(0).isEqualTo(parsedUsers.size());
    }

    @Test
    public void parseCsvResults_ShouldReturn1User_WhenCSVHas1ValidUserRecord() throws IOException {
        CSVParser userParseResults = CSVParser.parse("name,salary\nMary,10000", CSVFormat.DEFAULT);
        List<User> parsedUsers = UserUtil.parseCsvResults(userParseResults);

        assertThat(1).isEqualTo(parsedUsers.size());
        assertThat("Mary").isEqualTo(parsedUsers.get(0).getName());
        assertThat(100.00).isEqualTo(parsedUsers.get(0).getSalary());
    }

    @Test
    public void parseCsvResults_ShouldReturn1User_WhenCSVHas1ValidUserRecordAnd1InvalidFormat() throws IOException {
        CSVParser userParseResults = CSVParser.parse("name,salary\nMary,10000\nabcdef", CSVFormat.DEFAULT);
        List<User> parsedUsers = UserUtil.parseCsvResults(userParseResults);

        assertThat(1).isEqualTo(parsedUsers.size());
        assertThat("Mary").isEqualTo(parsedUsers.get(0).getName());
        assertThat(100.00).isEqualTo(parsedUsers.get(0).getSalary());
    }
}
