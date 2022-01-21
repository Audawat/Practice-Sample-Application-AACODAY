package com.nice.avishkar.service;

import com.nice.avishkar.CandidateVotes;
import com.nice.avishkar.ConstituencyResult;
import com.nice.avishkar.dao.CandidatesDao;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static com.nice.avishkar.Constants.NOTA;

public class CandidateServiceImpl implements CandidateService {
    private CandidatesDao candidatesDao;

    public CandidateServiceImpl(CandidatesDao candidatesDao) {
        this.candidatesDao = candidatesDao;
    }

    @Override
    public Map<String, ConstituencyResult> getConstituencyToCandidateMap(Path path) throws IOException {
        Map<String, ConstituencyResult> data = new HashMap<>();
        Instant start = Instant.now();
        List<String[]> allCandidate = candidatesDao.getAllCandidate(path);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.err.println("getAllCandidate Execution took "+ timeElapsed + " millis");

        start = Instant.now();
        if (null != allCandidate) {
            allCandidate.parallelStream().forEachOrdered(p -> {
                if (data.containsKey(p[0])) {
                    ConstituencyResult constituencyResult = data.get(p[0]);
                    constituencyResult.getCandidateList().add(new CandidateVotes(p[1], 0L));
                } else {
                    ConstituencyResult constituencyResult = new ConstituencyResult();
                    List<CandidateVotes> candidateList = new ArrayList<>();
                    CandidateVotes nota = new CandidateVotes(NOTA, 0L);
                    CandidateVotes candidate = new CandidateVotes(p[1], 0L);
                    candidateList.add(nota);
                    candidateList.add(candidate);

                    constituencyResult.setWinnerName(null);
                    constituencyResult.setCandidateList(candidateList);

                    data.put(p[0], constituencyResult);
                }
            });
        } else {
            System.out.println("No Constituency to candidate data");
        }

        finish = Instant.now();
        timeElapsed = Duration.between(start, finish).toMillis();
        System.err.println("forEachOrdered Execution took "+ timeElapsed + " millis");
        return data;
    }
}
