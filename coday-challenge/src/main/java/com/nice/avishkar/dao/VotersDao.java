package com.nice.avishkar.dao;

import com.nice.avishkar.entities.Votes;

import java.nio.file.Path;
import java.util.List;

public interface VotersDao {
    List<Votes> getAllVoter(Path path);
}
