package com.nice.avishkar.dao;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class VotersDaoImpl implements VotersDao {

    @Override
    public List<String[]> getAllVoter(Path path) throws IOException {
        return DaoUtils.readVotesCsv(path);
    }
}
