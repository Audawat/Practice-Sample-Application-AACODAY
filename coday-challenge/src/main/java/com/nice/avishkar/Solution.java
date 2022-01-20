package com.nice.avishkar;

import com.nice.avishkar.dao.CandidatesDao;
import com.nice.avishkar.dao.CandidatesDaoImpl;
import com.nice.avishkar.dao.VotersDao;
import com.nice.avishkar.dao.VotersDaoImpl;
import com.nice.avishkar.service.CandidateService;
import com.nice.avishkar.service.VoteCountingService;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Solution {

	public ElectionResult execute(Path candidateFile, Path votingFile) {
		ElectionResult resultData = new ElectionResult();
		Map<String, ConstituencyResult> data = new HashMap<>();
		// Write code here to read CSV files and process them
		CandidatesDao candidatesDao = new CandidatesDaoImpl();
		VotersDao votersDao = new VotersDaoImpl();
		CandidateService candidateService = new CandidateService(candidatesDao);
		Map<String, ConstituencyResult> constituencyToCandidateMap = candidateService.getConstituencyToCandidateMap(candidateFile);

		VoteCountingService voteCountingService = new VoteCountingService(votersDao);
		Map<String, ConstituencyResult> result = voteCountingService.getResult(votingFile, constituencyToCandidateMap);

		// Read Candidate File
		/*List<CandidateConstituency> parsedCandidates = readCsv(candidateFile);
		BeanListProcessor<CandidateConstituency> rowProcessor = new BeanListProcessor<CandidateConstituency>(CandidateConstituency.class);
		List<CandidateConstituency> votes2 = readVotesCsv(candidateFile, rowProcessor);

		BeanListProcessor<Votes> rowProcessor2 = new BeanListProcessor<Votes>(Votes.class);
		List<Votes> votes = readVotesCsv(votingFile, rowProcessor2);
*/
		// Read Vote file

		return resultData;
	}
}
