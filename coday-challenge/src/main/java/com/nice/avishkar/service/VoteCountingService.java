package com.nice.avishkar.service;

import com.nice.avishkar.ConstituencyResult;
import com.nice.avishkar.dao.VotersDao;
import com.nice.avishkar.entities.CastedVote;
import com.nice.avishkar.entities.Votes;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class VoteCountingService {

    private VotersDao votersDao;
    public VoteCountingService(VotersDao votersDao) {
        this.votersDao = votersDao;
    }

    public Map<String, ConstituencyResult> getResult(Path path, Map<String, ConstituencyResult> constituencyToCandidateMap) {
        /*Map<String, ConstituencyResult> resultMap = new HashMap<>();
        ConstituencyResult constituencyResult = new ConstituencyResult();
        CandidateVotes candidateVotes = new CandidateVotes();*/
        Map<String, CastedVote> castedVotes = new HashMap<>();
       // CastedVote castedVote  = new CastedVote();


        // Getting all data from dataset
        List<Votes> allVoter = votersDao.getAllVoter(path);
        AtomicInteger counter = new AtomicInteger();
        allVoter.stream().forEach(v -> {
            counter.getAndIncrement();
            if(isValidVote(v.getName(), castedVotes, constituencyToCandidateMap)) {
                ConstituencyResult constituencyResult1 = constituencyToCandidateMap.get(v.getConstituency());
                if (null != constituencyResult1) {
                    constituencyResult1.updateResult(v.getCandidate(), 1);
                    CastedVote castedVote = new CastedVote(v.getName(), v.getCandidate(), v.getConstituency(), false);
                    castedVotes.put(v.getName(), castedVote);
                } else {
                    System.out.println("Constutiency Not Found: "+ v.getConstituency());
                }
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
