package com.cdit.challenge.util;

import com.cdit.challenge.model.User;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserUtil {
    private static final Logger LOG = LoggerFactory.getLogger(UserUtil.class);

    public static String parseRawName(String rawName) {
        return rawName.trim();
    }

    public static int parseRawSalary(String rawSalary) {
        return Integer.parseInt(rawSalary.trim());
    }

    public static boolean isValidName(String rawName) {
        return rawName != null && rawName.trim().length() > 0;
    }

    public static boolean isValidSalary(String rawSalary) {
        if (rawSalary == null) {
            return false;
        }

        try {
            Integer.parseInt(rawSalary.trim());
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static List<User> parseCsvResults(CSVParser userParseResults) throws IOException {
        List<CSVRecord> userCsvRecords = userParseResults.getRecords();
        List<User> users = new ArrayList<>();

        for (int i = 0; i < userCsvRecords.size(); i++) {
            if (userCsvRecords.get(i).size() != 2) {
                LOG.warn("Invalid format at line " + i);
                continue;
            }

            String rawName = userCsvRecords.get(i).get(0);
            String rawSalary = userCsvRecords.get(i).get(1);

            if (isValidName(rawName) && isValidSalary(rawSalary)) {
                users.add(new User(parseRawName(rawName), parseRawSalary(rawSalary)));
            } else {
                LOG.warn("Wrong format at line " + i);
            }
        }

        return users;
    }
}
