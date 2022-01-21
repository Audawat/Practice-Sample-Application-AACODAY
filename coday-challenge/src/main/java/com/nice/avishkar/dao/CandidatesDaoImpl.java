package com.nice.avishkar.dao;

import com.nice.avishkar.entities.CandidateConstituency;
import com.univocity.parsers.common.processor.BeanListProcessor;

import java.nio.file.Path;
import java.util.List;

public class CandidatesDaoImpl implements CandidatesDao {

    @Override
    public List<String[]> getAllCandidate(Path path) {
        BeanListProcessor<CandidateConstituency> rowProcessor = new BeanListProcessor<CandidateConstituency>(CandidateConstituency.class);
        return DaoUtils.readVotesCsv(path);
    }
}
