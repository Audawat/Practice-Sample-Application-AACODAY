package com.nice.avishkar.service;

import com.nice.avishkar.ConstituencyResult;

import java.nio.file.Path;
import java.util.Map;

public interface CandidateService {

    Map<String, ConstituencyResult> getConstituencyToCandidateMap(Path path);
}
