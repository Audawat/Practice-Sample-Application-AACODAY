package com.nice.avishkar.dao;

import com.nice.avishkar.Constants;
import com.univocity.parsers.common.processor.BatchedColumnProcessor;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.List;

public class DaoUtils {

    public static  <T> List<T> readVotesCsv(Path filePath, BeanListProcessor<T> rowProcessor) {
        List<T> parsedRows = null;
        try (Reader inputReader = new InputStreamReader(new FileInputStream(filePath.toFile()), Constants.UTF_8)) {
            CsvParserSettings settings = new CsvParserSettings();
            settings.setProcessor(new BatchedColumnProcessor(1000) {
                @Override
                public void batchProcessed(int rowsInThisBatch) {}
            });
            //Settings to read CSVs
            settings.setLineSeparatorDetectionEnabled(true);
            settings.setHeaderExtractionEnabled(true);
            settings.setRowProcessor(rowProcessor);
            settings.setSkipEmptyLines(true);
            CsvParser parser = new CsvParser(settings);
            parser.parseAll(inputReader);
            parsedRows = rowProcessor.getBeans();

        } catch (IOException e) {

        }
        return parsedRows;
    }
}

