package com.nice.avishkar.dao;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface VotersDao {
    List<String[]> getAllVoter(Path path) throws IOException;
}
