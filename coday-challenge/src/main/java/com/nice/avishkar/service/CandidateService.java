package com.nice.avishkar.service;

import com.nice.avishkar.ConstituencyResult;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface CandidateService {

    Map<String, ConstituencyResult> getConstituencyToCandidateMap(Path path) throws IOException;
}
