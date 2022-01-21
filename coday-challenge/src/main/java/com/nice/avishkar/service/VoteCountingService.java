package com.nice.avishkar.service;

import com.nice.avishkar.ConstituencyResult;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface VoteCountingService {

    Map<String, ConstituencyResult> getResult(Path path, Map<String, ConstituencyResult> constituencyToCandidateMap) throws IOException;
}
