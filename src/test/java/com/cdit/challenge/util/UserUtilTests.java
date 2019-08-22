package com.cdit.challenge.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UserUtilTests {

    // isValidSalary
    @Test
    public void shouldReturnTrue_WhenRawSalaryIsPositive() {
        assertThat(UserUtil.isValidSalary("10000")).isTrue();
    }

    @Test
    public void shouldReturnTrue_WhenRawSalaryIsNegative() {
        assertThat(UserUtil.isValidSalary("-10000")).isTrue();
    }

    @Test
    public void shouldReturnTrue_WhenRawSalaryIs0() {
        assertThat(UserUtil.isValidSalary("0")).isTrue();
    }

    @Test
    public void shouldReturnTrue_WhenRawSalaryHasLeadingSpace() {
        assertThat(UserUtil.isValidSalary(" 0")).isTrue();
    }

    @Test
    public void shouldReturnTrue_WhenRawSalaryHasTrailingSpace() {
        assertThat(UserUtil.isValidSalary("0 ")).isTrue();
    }

    @Test
    public void shouldReturnTrue_WhenRawSalaryHasBothLeadingAndTrailingSpace() {
        assertThat(UserUtil.isValidSalary("  0 ")).isTrue();
    }

    @Test
    public void shouldReturnFalse_WhenRawSalaryContainsNonIntegers() {
        assertThat(UserUtil.isValidSalary("123a")).isFalse();
    }

    @Test
    public void shouldReturnFalse_WhenRawSalaryHasDecimal() {
        assertThat(UserUtil.isValidSalary("200.00")).isFalse();
    }

    @Test
    public void shouldReturnFalse_WhenRawSalaryIsEmptyString() {
        assertThat(UserUtil.isValidSalary("")).isFalse();
    }

    @Test
    public void shouldReturnFalse_WhenRawSalaryIsNull() {
        assertThat(UserUtil.isValidSalary(null)).isFalse();
    }

    // isValidName
    @Test
    public void shouldReturnTrue_WhenRawNameIsValidString() {
        assertThat(UserUtil.isValidName("Mary")).isTrue();
    }

    @Test
    public void shouldReturnFalse_WhenRawNameIsNull() {
        assertThat(UserUtil.isValidName(null)).isFalse();
    }

    @Test
    public void shouldReturnFalse_WhenRawNameIsEmpty() {
        assertThat(UserUtil.isValidName("")).isFalse();
    }

    // parseRawSalary
    @Test
    public void shouldReturnSalaryInInteger_WhenRawSalaryIsPositiveInteger() {
        assertThat(200).isEqualTo(UserUtil.parseRawSalary("200"));
    }

    @Test
    public void shouldReturnSalaryInInteger_WhenRawSalaryIsNegativeInteger() {
        assertThat(-200).isEqualTo(UserUtil.parseRawSalary("-200"));
    }

    @Test
    public void shouldReturnSalaryInInteger_WhenRawSalaryIsPositiveIntegerAndHasLeadingSpace() {
        assertThat(200).isEqualTo(UserUtil.parseRawSalary("  200"));
    }

    @Test
    public void shouldReturnSalaryInInteger_WhenRawSalaryIsPositiveIntegerAndHasTrailingSpace() {
        assertThat(200).isEqualTo(UserUtil.parseRawSalary("200  "));
    }

    @Test
    public void shouldReturnSalaryInInteger_WhenRawSalaryIsPositiveIntegerAndHasBothLeadingAndTrailingSpace() {
        assertThat(200).isEqualTo(UserUtil.parseRawSalary(" 200  "));
    }

    // parseRawName
    @Test
    public void shouldReturnNameInString_WhenRawNameIsValid() {
        assertThat("John").isEqualTo(UserUtil.parseRawName("John"));
    }

    @Test
    public void shouldReturnTrimmedNameInString_WhenRawNameHasLeadingSpace() {
        assertThat("John").isEqualTo(UserUtil.parseRawName("   John"));
    }

    @Test
    public void shouldReturnTrimmedNameInString_WhenRawNameHasTrailingSpace() {
        assertThat("John").isEqualTo(UserUtil.parseRawName("John  "));
    }

    @Test
    public void shouldReturnTrimmedNameInString_WhenRawNameHasBothLeadingAndTrailingSpace() {
        assertThat("John").isEqualTo(UserUtil.parseRawName(" John  "));
    }
}
