package pl.edu.agh.student.portfolio;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.DoubleStream;

public class DataGenerator {

    private Random random;
    private int size;

    public DataGenerator(int size, long seed) {
        random = new Random(seed);
        this.size = size;
    }

    public double[][] generateCovarianceMatrix(int minimum, int maximum) {
        DoubleStream doubleStream = random.doubles(minimum, maximum);
        double[] randomDoubles = doubleStream.limit(size * size).toArray();
        double[][] values = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
                if (i > j) {
                    values[i][j] = values[j][i];
                } else {
                    values[i][j] = randomDoubles[i * size + j];
                }
            }
        }
        return values;
    }

    public double[] generateExpectedReturns(int minimum, int maximum) {
        return random.doubles(minimum, maximum).limit(size).toArray();
    }

    private void saveToFile(String title, double[] doubles, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, true);
        fileWriter.append(title).append("\n").append(Arrays.toString(doubles)).append("\n");
        fileWriter.flush();
        fileWriter.close();
    }

    private void saveToFile(String title, double[][] doubles, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, true);
        fileWriter.append(title).append("\n");
        for (double[] arrayOfDoubles : doubles) {
            fileWriter.append(Arrays.toString(arrayOfDoubles)).append("\n");
        }
        fileWriter.flush();
        fileWriter.close();
    }

    double[][] generateCovarianceMatrix(int minimum, int maximum, String title, String fileName) throws IOException {
        double[][] covarianceMatrix = generateCovarianceMatrix(minimum, maximum);
        saveToFile(title, covarianceMatrix, fileName);
        return covarianceMatrix;
    }

    public double[] generateExpectedReturns(int minimum, int maximum,  String title, String fileName) throws IOException {
        double[] expectedReturns = generateExpectedReturns(minimum, maximum);
        saveToFile(title, expectedReturns, fileName);
        return expectedReturns;
    }

}
