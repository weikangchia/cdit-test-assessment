package com.cdit.challenge.externalDto;

import com.cdit.challenge.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResults {
    private List<User> results;
}
