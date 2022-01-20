package com.nice.avishkar;

import com.nice.avishkar.entities.CandidateConstituency;
import com.nice.avishkar.entities.Votes;
import com.univocity.parsers.common.processor.BatchedColumnProcessor;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

public class Solution {

	public ElectionResult execute(Path candidateFile, Path votingFile) {
		ElectionResult resultData = new ElectionResult();

		// Write code here to read CSV files and process them
		// Read Candidate File
		List<CandidateConstituency> parsedCandidates = readCsv(candidateFile);
		List<Votes> votes = readVotesCsv(votingFile);

		// Read Vote file

		return resultData;
	}


	public List<CandidateConstituency> readCsv(Path filePath) {
		List<CandidateConstituency> parsedRows = null;
		try (Reader inputReader = new InputStreamReader(new FileInputStream(filePath.toFile()), "UTF-8")) {
			CsvParserSettings settings = new CsvParserSettings();
			settings.setProcessor(new BatchedColumnProcessor(5) {
				@Override
				public void batchProcessed(int rowsInThisBatch) {}
			});
			BeanListProcessor<CandidateConstituency> rowProcessor = new BeanListProcessor<CandidateConstituency>(CandidateConstituency.class);
			settings.setLineSeparatorDetectionEnabled(true);
			settings.setHeaderExtractionEnabled(true);
			settings.setRowProcessor(rowProcessor);
			CsvParser parser = new CsvParser(settings);
			parser.parseAll(inputReader);
			parsedRows = rowProcessor.getBeans();

		} catch (IOException e) {
			// handle exception
		}
		return parsedRows;
	}

	public List<Votes> readVotesCsv(Path filePath) {
		List<Votes> parsedRows = null;
		try (Reader inputReader = new InputStreamReader(new FileInputStream(filePath.toFile()), "UTF-8")) {
			CsvParserSettings settings = new CsvParserSettings();
			settings.setProcessor(new BatchedColumnProcessor(5) {
				@Override
				public void batchProcessed(int rowsInThisBatch) {}
			});
			BeanListProcessor<Votes> rowProcessor = new BeanListProcessor<Votes>(Votes.class);
			settings.setLineSeparatorDetectionEnabled(true);
			settings.setHeaderExtractionEnabled(true);
			settings.setRowProcessor(rowProcessor);
			CsvParser parser = new CsvParser(settings);
			parser.parseAll(inputReader);
			parsedRows = rowProcessor.getBeans();

		} catch (IOException e) {
			// handle exception
		}
		return parsedRows;
	}

}