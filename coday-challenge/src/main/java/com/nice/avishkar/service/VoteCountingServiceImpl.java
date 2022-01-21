package com.nice.avishkar.service;

import com.nice.avishkar.ConstituencyResult;
import com.nice.avishkar.dao.VotersDao;
import com.nice.avishkar.entities.CastedVote;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class VoteCountingServiceImpl implements VoteCountingService {

    private VotersDao votersDao;

    public VoteCountingServiceImpl(VotersDao votersDao) {
        this.votersDao = votersDao;
    }

    @Override
    public Map<String, ConstituencyResult> getResult(Path path, Map<String, ConstituencyResult> constituencyToCandidateMap) {

        if (null != constituencyToCandidateMap) {
            Map<String, CastedVote> castedVotes = new HashMap<>();
            // Getting all data from dataset
            List<String[]> allVoter = votersDao.getAllVoter(path);

            AtomicInteger counter = new AtomicInteger();
            allVoter.parallelStream().forEachOrdered(v -> {
                if (null != v) {
                    try {
                        counter.getAndIncrement();
                        if (isValidVote(v[0], castedVotes, constituencyToCandidateMap)) {
                            ConstituencyResult constituencyResult1 = constituencyToCandidateMap.get(v[1]);
                            if (null != constituencyResult1) {
                                constituencyResult1.updateResult(v[3], 1);
                                CastedVote castedVote = new CastedVote(v[0], v[3], v[1], false);
                                castedVotes.put(v[0], castedVote);
                            } else {
                                System.out.println("Constituency Not Found: " + v[1]);
                            }
                        }
                    } catch (Exception e) {
                        String errorMessage = MessageFormat.format("Exception Occurred during Processing Votes for user: {0}," +
                                " for Constituency: {1}, for Candidate: {2}, for Polling Station: {3}", v[0], v[1], v[3], v[2]);
                        System.out.println(errorMessage);
                    }
                } else {
                    System.out.println("Empty Vote record found hence ignoring");
                }
            });

            constituencyToCandidateMap.entrySet().stream().forEach(e -> {
                ConstituencyResult values = e.getValue();
                values.finalUpdate();
            });
            System.out.println("Total rows: " + counter.get());
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
            constituencyResult.updateResult(castedVote.getCandidate(), -1);
            castedVote.setHandled(true);
        }
    }
}
