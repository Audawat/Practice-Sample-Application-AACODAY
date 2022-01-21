package com.nice.avishkar.dao;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface CandidatesDao {
    List<String[]> getAllCandidate(Path path) throws IOException;
}
