package com.nice.avishkar.entities;

import com.univocity.parsers.annotations.Parsed;

public class Votes {

    @Parsed(field = "Voter")
    String name;
    @Parsed(field = "Constituency")
    String constituency;
    @Parsed(field = "PollingStation")
    String pollingStation;
    @Parsed(field = "Candidate")
    String candidate;
}
