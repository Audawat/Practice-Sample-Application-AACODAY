package com.nice.avishkar.entities;

import com.univocity.parsers.annotations.Parsed;
import lombok.Data;

@Data
public class Votes<T> {

    @Parsed(field = "Voter")
    String name;
    @Parsed(field = "Constituency")
    String constituency;
    @Parsed(field = "PollingStation")
    String pollingStation;
    @Parsed(field = "Candidate")
    String candidate;
}
