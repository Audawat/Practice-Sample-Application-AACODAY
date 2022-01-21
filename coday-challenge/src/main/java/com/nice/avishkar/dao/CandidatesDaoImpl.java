package com.nice.avishkar.dao;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class CandidatesDaoImpl implements CandidatesDao {

    @Override
    public List<String[]> getAllCandidate(Path path) throws IOException {
        return DaoUtils.readVotesCsv(path);
    }
}
