package com.nice.avishkar.entities;

import com.univocity.parsers.annotations.Parsed;
import lombok.Data;

import static com.nice.avishkar.Constants.*;

@Data
public class Votes<T> {

    @Parsed(field = VOTER)
    String name;
    @Parsed(field = CONSTITUENCY)
    String constituency;
    @Parsed(field = POLLING_STATION)
    String pollingStation;
    @Parsed(field = CANDIDATE)
    String candidate;
}
