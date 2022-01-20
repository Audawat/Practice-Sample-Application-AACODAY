package com.nice.avishkar.entities;

import com.univocity.parsers.annotations.Parsed;

public class CandidateConstituency {

    @Parsed(field = "Constituency")
    String Constituency;
    @Parsed(field = "Candidate")
    String Candidate;
}
