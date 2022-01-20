package com.nice.avishkar;

import com.nice.avishkar.dao.CandidatesDao;
import com.nice.avishkar.dao.CandidatesDaoImpl;
import com.nice.avishkar.dao.VotersDao;
import com.nice.avishkar.dao.VotersDaoImpl;
import com.nice.avishkar.service.CandidateService;
import com.nice.avishkar.service.VoteCountingService;

import java.nio.file.Path;
import java.util.Map;

public class Solution {

	public ElectionResult execute(Path candidateFile, Path votingFile) {
		ElectionResult resultData = new ElectionResult();
		CandidatesDao candidatesDao = new CandidatesDaoImpl();
		VotersDao votersDao = new VotersDaoImpl();
		CandidateService candidateService = new CandidateService(candidatesDao);
		Map<String, ConstituencyResult> constituencyToCandidateMap = candidateService.getConstituencyToCandidateMap(candidateFile);
		VoteCountingService voteCountingService = new VoteCountingService(votersDao);
		Map<String, ConstituencyResult> result = voteCountingService.getResult(votingFile, constituencyToCandidateMap);
		resultData.setResultData(result);
		return resultData;
	}
}
