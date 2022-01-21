package com.nice.avishkar.dao;

import com.nice.avishkar.entities.CandidateConstituency;

import java.nio.file.Path;
import java.util.List;

public interface CandidatesDao {
    List<String[]> getAllCandidate(Path path);
}
