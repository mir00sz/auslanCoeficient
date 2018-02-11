import com.opencsv.CSVWriter;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CorrelationCoefficientProcessor {

    private static final int NUMBER_OF_CHANNELS = 22;
    private static final Path OUTPUT_DIR = Paths.get("/home/mirek/correlations");
    private static final Logger LOGGER = LoggerFactory.getLogger(CorrelationCoefficientProcessor.class.getName());
    final private PearsonsCorrelation pearsonsCorrelation;


    CorrelationCoefficientProcessor() {
        this.pearsonsCorrelation = new PearsonsCorrelation();
    }


    public void process(Path inputPath) throws IOException, MatrixDimensionsException {
        LOGGER.info("Processing files...");
        List<File> sortedListOfFiles = Files
                .list(inputPath)
                .sorted(Comparator.comparing(x -> x.getFileName().toString()))
                .map(Path::toFile).collect(Collectors.toList());

        List<ReadingsFileRepresentation> readingsFileRepresentations = convertFilesToReadingFilesRepresentations(sortedListOfFiles);

        for (int i = 0; i < NUMBER_OF_CHANNELS; i++) {
            LOGGER.info("Processing channel number " + i);
            CorrelationMatrix correlationMatrix = findCorrelationBetweenFilesOnChannel(i, readingsFileRepresentations);
            correlationMatrixToFile(correlationMatrix);
        }

    }

    private List<ReadingsFileRepresentation> convertFilesToReadingFilesRepresentations(List<File> listOfFiles) {
        LOGGER.info("Loading files...");
        return listOfFiles.stream().
                map(x -> new ReadingsFileRepresentation((x.toPath()))).
                sorted().
                collect(Collectors.toList());

    }


    private void correlationMatrixToFile(CorrelationMatrix correlationMatrix) throws IOException {
        List<String[]> lines = correlationMatrix.convertMatrixToListOfLines();
        Path filePath = Paths.get(OUTPUT_DIR.toString(), String.valueOf(correlationMatrix.getChannelNumber()) + ".csv");

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath.toString()))) {
            writer.writeAll(lines);
        }
    }

    private CorrelationMatrix findCorrelationBetweenFilesOnChannel(int channelIndex, List<ReadingsFileRepresentation> readingsFileRepresentations) throws MatrixDimensionsException {


        List<String> headers = getHeader(readingsFileRepresentations);

        CorrelationMatrix correlationMatrix = getCorrelationMatrix(channelIndex, headers);

        for (ReadingsFileRepresentation readingsFileRepresentation : readingsFileRepresentations) {
            readingsFileRepresentations.forEach(x -> {

                double[] readings1 = readingsFileRepresentation.getColumn(channelIndex);
                double[] readings2 = x.getColumn(channelIndex);
                double correlation = pearsonsCorrelation.correlation(readings1, readings2);
                correlationMatrix
                        .addCorrelation(
                        readingsFileRepresentation.getFilePath().getFileName().toString(),
                        x.getFilePath().getFileName().toString(),
                        correlation);


            });
        }
        return correlationMatrix;
    }

    private CorrelationMatrix getCorrelationMatrix(int channelIndex, List<String> headers) throws MatrixDimensionsException {
        return CorrelationMatrix
                .aCorrelationMatrix()
                .withChannel(channelIndex)
                .withColumnTitles(headers)
                .withLineTitles(headers).build();
    }

    private List<String> getHeader(List<ReadingsFileRepresentation> readingsFileRepresentations) {
        return readingsFileRepresentations.stream().
                map(x -> x.getFilePath()
                        .getFileName()
                        .toString())
                .sorted()
                .collect(Collectors.toList());
    }


}
