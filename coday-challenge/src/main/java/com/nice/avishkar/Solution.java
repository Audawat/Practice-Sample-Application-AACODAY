package com.nice.avishkar;

import com.nice.avishkar.dao.CandidatesDao;
import com.nice.avishkar.dao.CandidatesDaoImpl;
import com.nice.avishkar.dao.VotersDao;
import com.nice.avishkar.dao.VotersDaoImpl;
import com.nice.avishkar.service.CandidateService;
import com.nice.avishkar.service.CandidateServiceImpl;
import com.nice.avishkar.service.VoteCountingService;
import com.nice.avishkar.service.VoteCountingServiceImpl;

import java.nio.file.Path;
import java.util.Map;

public class Solution {

	public ElectionResult execute(Path candidateFile, Path votingFile) {
		ElectionResult resultData = new ElectionResult();
		//Creating Dao's
		CandidatesDao candidatesDao = new CandidatesDaoImpl();
		VotersDao votersDao = new VotersDaoImpl();

		//Creating Services and Injecting Dao
		CandidateService candidateService = new CandidateServiceImpl(candidatesDao);
		VoteCountingService voteCountingService = new VoteCountingServiceImpl(votersDao);

		//Getting Constituency To Candidate Data Mapped to avoid Unmapped Candidates
		Map<String, ConstituencyResult> constituencyToCandidateMap = candidateService.getConstituencyToCandidateMap(candidateFile);

		Map<String, ConstituencyResult> result = voteCountingService.getResult(votingFile, constituencyToCandidateMap);

		resultData.setResultData(result);
		return resultData;
	}
}
