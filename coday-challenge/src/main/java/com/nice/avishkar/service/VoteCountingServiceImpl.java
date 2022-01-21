package com.nice.avishkar.service;

import com.nice.avishkar.ConstituencyResult;
import com.nice.avishkar.dao.VotersDao;
import com.nice.avishkar.entities.CastedVote;

import java.io.IOException;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteCountingServiceImpl implements VoteCountingService {

    private VotersDao votersDao;

    public VoteCountingServiceImpl(VotersDao votersDao) {
        this.votersDao = votersDao;
    }

    @Override
    public Map<String, ConstituencyResult> getResult(Path path, Map<String, ConstituencyResult> constituencyToCandidateMap) throws IOException {

        if (null != constituencyToCandidateMap) {
            Map<String, CastedVote> castedVotes = new HashMap<>();
            // Getting all data from dataset
            Instant start = Instant.now();
            List<String[]> allVoter = votersDao.getAllVoter(path);
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            System.err.println("getAllVoter Execution took "+ timeElapsed + " millis");

            start = Instant.now();
            allVoter.parallelStream().forEachOrdered(v -> {
                    try {
                        if (isValidVote(v[0], castedVotes, constituencyToCandidateMap)) {
                            ConstituencyResult constituencyResult1 = constituencyToCandidateMap.get(v[1]);
                            if (null != constituencyResult1) {
                                constituencyResult1.updateVote(v[3], 1);
                                CastedVote castedVote = new CastedVote(v[0], v[3], v[1], false);
                                castedVotes.put(v[0], castedVote);
                            } else {
                                System.out.println("Constituency Not Found: " + v[1]);
                            }
                        }
                    } catch (Exception e) {
                        String errorMessage = MessageFormat.format("Exception Occurred during Processing Votes for user: {0}", v[0]);
                        System.err.println(errorMessage);
                    }
            });

            finish = Instant.now();
            timeElapsed = Duration.between(start, finish).toMillis();
            System.err.println("forEachOrdered voter Execution took "+ timeElapsed + " millis");

            start = Instant.now();
            constituencyToCandidateMap.entrySet().stream().forEach(e -> {
                ConstituencyResult values = e.getValue();
                values.updateResult();
            });

            finish = Instant.now();
            timeElapsed = Duration.between(start, finish).toMillis();
            System.err.println("constituencyToCandidateMap final update Execution took "+ timeElapsed + " millis");
        }

        return constituencyToCandidateMap;
    }

    private boolean isValidVote(String voter, Map<String, CastedVote> castedVotes, Map<String, ConstituencyResult> constituencyToCandidateMap) {
        if (castedVotes.containsKey(voter)) {
            CastedVote castedVote = castedVotes.get(voter);
            disqualifyAllVotesForVoter(castedVote, constituencyToCandidateMap);
            return false;
        }
        return true;
    }

    private void disqualifyAllVotesForVoter(CastedVote castedVote, Map<String, ConstituencyResult> constituencyToCandidateMap) {
        if (!castedVote.isHandled()) {
            ConstituencyResult constituencyResult = constituencyToCandidateMap.get(castedVote.getConstituency());
            constituencyResult.updateVote(castedVote.getCandidate(), -1);
            castedVote.setHandled(true);
        }
    }
}
