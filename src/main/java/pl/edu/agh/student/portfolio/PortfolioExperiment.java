package pl.edu.agh.student.portfolio;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PortfolioExperiment {

    public static final int GENERATED_ARRAYS_SIZE = 100;
    public static final int MINIMUM_EXPECTED_RETURN = -100;
    public static final int MAXIMUM_EXPECTED_RETURN = 100;
    public static final int MINIMUM_RISK = 0;
    public static final int MAXIMUM_RISK = 100;
    public static final long DATA_GENERATOR_SEED = 0;
    public static final boolean SAVE_DATA_TO_FILE = false;

    public static final int RUNS = 100;
    public static final int ITERATIONS_DELTA = 100;
    public static final long PORTFOLIO_RUNNER_SEED = 1;

    public static final int POPULATION_SIZE = 100;
    public static final double CROSSOVER_PROBABILITY = 0.125;
    public static final double CROSSOVER_DISTRIBUTION_INDEX = 20.0;
    public static final double MUTATION_PROBABILITY = 0.9;
    public static final double MUTATION_DISTRIBUTION_INDEX = 20.0;

    public static void main(String[] args) throws IOException {
        DataGenerator dataGenerator = new DataGenerator(GENERATED_ARRAYS_SIZE, DATA_GENERATOR_SEED);
        double[] expectedReturns;
        double[][] covarianceMatrix;
        if (SAVE_DATA_TO_FILE) {
            expectedReturns = dataGenerator.generateExpectedReturns(MINIMUM_EXPECTED_RETURN, MAXIMUM_EXPECTED_RETURN,
                    "Expected returns", "data-" + GENERATED_ARRAYS_SIZE + "-" + DATA_GENERATOR_SEED);
            covarianceMatrix = dataGenerator.generateCovarianceMatrix(MINIMUM_RISK, MAXIMUM_RISK,
                    "Covariance matrix", "data-" + GENERATED_ARRAYS_SIZE + "-" + DATA_GENERATOR_SEED);
        } else {
            expectedReturns = dataGenerator.generateExpectedReturns(MINIMUM_EXPECTED_RETURN, MAXIMUM_EXPECTED_RETURN);
            covarianceMatrix = dataGenerator.generateCovarianceMatrix(MINIMUM_RISK, MAXIMUM_RISK);
        }

        List[] populations = new List[RUNS];

        FileWriter writer = new FileWriter(GENERATED_ARRAYS_SIZE + "-" + POPULATION_SIZE + "-"
                + CROSSOVER_PROBABILITY + "-" + MUTATION_PROBABILITY + ".csv", true);
        writer.append("Iteration,AverageReturn,AverageRisk,ReturnOfSolutionWithHighestReturn," +
                "RiskOfSolutionWithHighestReturn,ReturnOfSolutionWithLowestRisk,RiskOfSolutionWithLowestRisk\n");
        writer.flush();
        for (int i = 0; i < populations.length; i++) {
            List<PortfolioSolution> population = PortfolioRunner.run(expectedReturns, covarianceMatrix,
                    (i + 1) * ITERATIONS_DELTA,
                    POPULATION_SIZE, CROSSOVER_PROBABILITY, CROSSOVER_DISTRIBUTION_INDEX,
                    MUTATION_PROBABILITY, MUTATION_DISTRIBUTION_INDEX, PORTFOLIO_RUNNER_SEED
            );
            populations[i] = population;

            // noinspection OptionalGetWithoutIsPresent
            double averageReturn = population.stream().mapToDouble(solution -> solution.getObjective(0))
                    .average().getAsDouble();
            // noinspection OptionalGetWithoutIsPresent
            double averageRisk = population.stream().mapToDouble(solution -> solution.getObjective(1))
                    .average().getAsDouble();
            // noinspection OptionalGetWithoutIsPresent
            PortfolioSolution solutionWithHighestReturn = population.stream()
                    .min(Comparator.comparingDouble(solution1 -> solution1.getObjective(0))).get();
            // noinspection OptionalGetWithoutIsPresent
            PortfolioSolution solutionWithLowestRisk = population.stream()
                    .min(Comparator.comparingDouble(solution1 -> solution1.getObjective(1))).get();

            System.out.println("    Expected returns average: " + -averageReturn + ".");
            System.out.println("    Risks average: " + averageRisk + ".");
            System.out.println("    Solution with highest expected return:\n" +
                    "        expected return: " + -solutionWithHighestReturn.getObjective(0) + ",\n" +
                    "        risk: " + solutionWithHighestReturn.getObjective(1) + ".");
            System.out.println("    Solution with lowest risk:\n" +
                    "        expected return: " + -solutionWithLowestRisk.getObjective(0) + ",\n" +
                    "        risk: " + solutionWithLowestRisk.getObjective(1) + ".");

            writer.append(String.valueOf((i + 1) * ITERATIONS_DELTA)).append(",")
                    .append(String.valueOf(-averageReturn)).append(",")
                    .append(String.valueOf(averageRisk)).append(",")
                    .append(String.valueOf(-solutionWithHighestReturn.getObjective(0))).append(",")
                    .append(String.valueOf(solutionWithHighestReturn.getObjective(1))).append(",")
                    .append(String.valueOf(-solutionWithLowestRisk.getObjective(0))).append(",")
                    .append(String.valueOf(solutionWithLowestRisk.getObjective(1))).append("\n");
            writer.flush();
        }
        writer.close();

        writer = new FileWriter("result-" + GENERATED_ARRAYS_SIZE + "-" + POPULATION_SIZE
                + "-" + CROSSOVER_PROBABILITY + "-" + MUTATION_PROBABILITY, true);
        writer.append("Final population\n");
        for (Object solutionAsObject : populations[populations.length - 1]) {
            PortfolioSolution solution = (PortfolioSolution) solutionAsObject;
            writer.append(Arrays.toString(solution.getVariables())).append("\n")
                    .append(Arrays.toString(solution.getObjectives())).append("\n");
        }
        writer.flush();
        writer.close();
    }

}
