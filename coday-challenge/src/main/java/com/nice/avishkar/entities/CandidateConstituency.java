package com.nice.avishkar.entities;

import com.univocity.parsers.annotations.Parsed;
import lombok.Data;

import static com.nice.avishkar.Constants.CONSTITUENCY;
import static com.nice.avishkar.Constants.CANDIDATE;

@Data
public class CandidateConstituency<T> {

    @Parsed(field = CONSTITUENCY)
    String Constituency;
    @Parsed(field = CANDIDATE)
    String Candidate;
}
