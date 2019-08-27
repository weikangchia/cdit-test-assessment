package com.cdit.challenge.controller;

import com.cdit.challenge.externalDto.UserResults;
import com.cdit.challenge.model.UrlShortener;
import com.cdit.challenge.model.User;
import com.cdit.challenge.repository.UrlRepository;
import com.cdit.challenge.repository.UserRepository;
import com.cdit.challenge.specification.UrlSpecification;
import com.cdit.challenge.specification.UserSpecification;
import com.cdit.challenge.util.UrlConstant;
import com.cdit.challenge.util.UserUtil;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UrlRepository urlRepository;

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
                CSVParser userParseResults = CSVFormat.EXCEL.withHeader()
                    .parse(new InputStreamReader((userUploadFile.getInputStream())));
                List<User> users = UserUtil.parseCsvResults(userParseResults);
                userRepository.saveAll(users);
                return ResponseEntity
                    .ok(Collections.singletonMap("message", "Uploaded " + users.size() + " users"));
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("message", "File is empty"));
        }
    }

    @RequestMapping(value = "/shortener", method = RequestMethod.GET)
    public ResponseEntity urlShortener(@RequestParam("url") String userLongUrl) {
        log.info("User long url: " + userLongUrl);

        String shortenUrl = UrlConstant.baseUrl + Instant.now().getEpochSecond();

        log.info("Shorten url for " + userLongUrl + ": " + shortenUrl);

        try {
            urlRepository.save(new UrlShortener(shortenUrl, userLongUrl));
        } catch (Exception ex) {
            log.warn("Url exists");
        }

        Optional<UrlShortener> urlShortener = urlRepository
            .findOne(UrlSpecification.getShortUrl(userLongUrl));

        if (urlShortener.isPresent()) {
            return ResponseEntity.ok()
                .body(Collections.singletonMap("short_url", urlShortener.get().getShortUrl()));
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @RequestMapping(value = "/lengthener", method = RequestMethod.GET)
    public ResponseEntity urlLengthener(@RequestParam("url") String userShortUrl) {

        Optional<UrlShortener> urlShortener = urlRepository
            .findOne(UrlSpecification.getLongUrl(userShortUrl));

        if (urlShortener.isPresent()) {
            return ResponseEntity.ok()
                .body(Collections.singletonMap("long_url", urlShortener.get().getLongUrl()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
