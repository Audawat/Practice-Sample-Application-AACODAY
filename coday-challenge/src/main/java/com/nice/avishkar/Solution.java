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
import java.text.MessageFormat;
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

		computeResult(candidateFile, votingFile, resultData, candidateService, voteCountingService);

		System.out.println(resultData);
		return resultData;
    }

	private void computeResult(Path candidateFile, Path votingFile, ElectionResult resultData, CandidateService candidateService, VoteCountingService voteCountingService) {
		Map<String, ConstituencyResult> constituencyToCandidateMap = null;
		Map<String, ConstituencyResult> result = null;
		try {
			//Getting Constituency To Candidate Data
			constituencyToCandidateMap = candidateService.getConstituencyToCandidateMap(candidateFile);
			//Getting votes data per constituency for candidates
			result = voteCountingService.getResult(votingFile, constituencyToCandidateMap);
			resultData.setResultData(result);
		} catch (Exception e) {
			String errorMessage = MessageFormat.format("Exception Occurred during Processing input file. Message: {0}", e.getMessage());
			System.err.println(errorMessage);
			e.printStackTrace();
		}
	}
}
