package com.nice.avishkar.service;

import com.nice.avishkar.CandidateVotes;
import com.nice.avishkar.ConstituencyResult;
import com.nice.avishkar.dao.CandidatesDao;
import com.nice.avishkar.entities.CandidateConstituency;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CandidateService {
    //TODO Move to Constant File
    public static final String NOTA = "NOTA";

    CandidatesDao candidatesDao;

    public CandidateService(CandidatesDao candidatesDao) {
        this.candidatesDao = candidatesDao;
    }

    public Map<String, ConstituencyResult> getConstituencyToCandidateMap(Path path) {
        Map<String, ConstituencyResult> data = new HashMap<>();
        List<CandidateConstituency> allCandidate = candidatesDao.getAllCandidate(path);

        allCandidate.stream().forEach(p -> {
            if (data.containsKey(p.getConstituency())) {
                ConstituencyResult constituencyResult = data.get(p.getConstituency());
                constituencyResult.getCandidateList().add(new CandidateVotes(p.getCandidate(), 0L));
            } else {
                ConstituencyResult constituencyResult = new ConstituencyResult();
                List<CandidateVotes> candidateList = new ArrayList<>();
                CandidateVotes nota = new CandidateVotes(NOTA, 0L);
                CandidateVotes candidate = new CandidateVotes(p.getCandidate(), 0L);
                candidateList.add(nota);
                candidateList.add(candidate);

                constituencyResult.setWinnerName(null);
                constituencyResult.setCandidateList(candidateList);

                data.put(p.getConstituency(), constituencyResult);
            }
        });
        return data;
    }
}
