package com.nice.avishkar;

import java.util.List;
import java.util.Optional;

public class ConstituencyResult {

	private String winnerName;
	private List<CandidateVotes> candidateList;
	
	public ConstituencyResult() {
		super();
	}

	public ConstituencyResult(String winnerName, List<CandidateVotes> candidateList) {
		super();
		this.winnerName = winnerName;
		this.candidateList = candidateList;
	}

	public String getWinnerName() {
		return winnerName;
	}

	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}

	public List<CandidateVotes> getCandidateList() {
		return candidateList;
	}

	public void setCandidateList(List<CandidateVotes> candidateList) {
		this.candidateList = candidateList;
	}


	public void updateResult(String candidateName, int vote) {
		Optional<CandidateVotes> first = this.candidateList.stream().filter(p -> p.getCandidateName().equals(candidateName)).findFirst();
		if (first.isPresent()) {
			CandidateVotes candidateVotes = first.get();
			candidateVotes.updateVote(vote);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConstituencyResult [winnerName=");
		builder.append(winnerName);
		builder.append(", candidateList=");
		builder.append(candidateList);
		builder.append("]");
		return builder.toString();
	}

}
