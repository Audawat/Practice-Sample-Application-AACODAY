package com.nice.avishkar.dao;

import com.nice.avishkar.entities.Votes;
import com.univocity.parsers.common.processor.BeanListProcessor;

import java.nio.file.Path;
import java.util.List;

public class VotersDaoImpl implements VotersDao {

    @Override
    public List<Votes> getAllVoter(Path path) {
        BeanListProcessor<Votes> rowProcessor = new BeanListProcessor<Votes>(Votes.class);
        return DaoUtils.readVotesCsv(path, rowProcessor);
    }
}
