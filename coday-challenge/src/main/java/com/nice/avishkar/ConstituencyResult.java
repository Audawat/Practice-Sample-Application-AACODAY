package com.nice.avishkar;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.nice.avishkar.Constants.NOTA;
import static com.nice.avishkar.Constants.NO_WINNER;

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

	//Method to update the data as per current available voting count
    public void updateVote(String candidateName, int vote) {
        Optional<CandidateVotes> first = this.candidateList.stream().filter(p -> p.getCandidateName().equals(candidateName)).findFirst();
        if (first.isPresent()) {
            CandidateVotes candidateVotes = first.get();
            candidateVotes.updateVote(vote);
        } else {
            System.out.println("Candidate not found  " + candidateName);
        }
    }

    // Method to update result as per recent votes data
    public void updateResult() {
    	//Comparator to sort candidates as per criteria
        Comparator<CandidateVotes> comparator = (vote1, vote2) -> {
            if (vote1.getCandidateName().equals(NOTA)) {
                return 1;
            } else if (vote2.getCandidateName().equals(NOTA)) {
                return -1;
            } else if (vote2.getVotes() > vote1.getVotes()) {
                return 1;
            } else if (vote2.getVotes() < vote1.getVotes()) {
                return -1;
            } else {
                return vote1.getCandidateName().compareTo(vote2.getCandidateName());
            }
        };

        Collections.sort(this.candidateList, comparator);
        // Update winner name
        this.winnerName = candidateList.get(0).getVotes() == candidateList.get(1).getVotes() ? NO_WINNER : candidateList.get(0).getCandidateName();
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
