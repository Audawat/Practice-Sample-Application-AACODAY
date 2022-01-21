package com.nice.avishkar.service;

import com.nice.avishkar.ConstituencyResult;
import com.nice.avishkar.dao.VotersDao;
import com.nice.avishkar.entities.CastedVote;
import com.nice.avishkar.entities.Votes;

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
        Map<String, CastedVote> castedVotes = new HashMap<>();

        // Getting all data from dataset

        List<Votes> allVoter = votersDao.getAllVoter(path);

        AtomicInteger counter = new AtomicInteger();
        //List<Votes> votes = Collections.synchronizedList(allVoter);
        allVoter.parallelStream().forEachOrdered(v -> {
            if (null != v) {
                try {
                    counter.getAndIncrement();
                    if(isValidVote(v.getName(), castedVotes, constituencyToCandidateMap)) {
                        ConstituencyResult constituencyResult1 = constituencyToCandidateMap.get(v.getConstituency());
                        if (null != constituencyResult1) {
                            constituencyResult1.updateResult(v.getCandidate(), 1);
                            CastedVote castedVote = new CastedVote(v.getName(), v.getCandidate(), v.getConstituency(), false);
                            castedVotes.put(v.getName(), castedVote);
                        } else {
                            System.out.println("Constituency Not Found: "+ v.getConstituency());
                        }
                    }
                }catch (Exception e){
                    String errorMessage = MessageFormat.format("Exception Occurred during Processing Votes for user: {0}," +
                            " for Constituency: {1}, for Candidate: {2}, for Polling Station: {3}",v.getName(), v.getConstituency(), v.getCandidate(), v.getPollingStation());
                    System.out.println(errorMessage);
                }
            } else {

                System.out.println("Empty Vote record found hence ignoring");
            }
        });

        constituencyToCandidateMap.entrySet().stream().forEach(e->{
            ConstituencyResult values = e.getValue();
            values.finalUpdate();
        });
        System.out.println("Total rows: "+ counter.get());
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
