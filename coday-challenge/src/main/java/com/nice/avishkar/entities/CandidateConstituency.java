package com.nice.avishkar.entities;

import com.univocity.parsers.annotations.Parsed;
import lombok.Data;

@Data
public class CandidateConstituency<T> {
    @Parsed(field = "Constituency")
    String Constituency;
    @Parsed(field = "Candidate")
    String Candidate;
}
