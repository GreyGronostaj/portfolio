import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;

import java.util.List;

public class PortfolioRunner {

    public static void main(String[] args) {
        double[] expectedReturn = {0, 5, 0};
        double[] weight = {1, 1, 1};
        double[][] covariance = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};

        PortfolioProblem problem = new PortfolioProblem(expectedReturn, weight, covariance);
        SPEA2 algorithm;
        PortfolioCrossover crossover;
        PortfolioMutation mutation;
        BinaryTournamentSelection selection;

        double crossoverProbability = 0.9;
        double crossoverDistributionIndex = 20.0;
        crossover = new PortfolioCrossover(); //crossoverProbability, crossoverDistributionIndex

        double mutationProbability = 0.0; //1.0 / problem.getNumberOfVariables();
        double mutationDistributionIndex = 20.0;
        mutation = new PortfolioMutation(); //(mutationProbability, mutationDistributionIndex);

        selection = new BinaryTournamentSelection<>(new PortfolioComparator());

        algorithm = new SPEA2Builder(problem, crossover, mutation)
                .setSelectionOperator(selection)
                .setMaxIterations(250)
                .setPopulationSize(100)
                .build();

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        List<PortfolioSolution> population = algorithm.getResult();

        long computingTime = algorithmRunner.getComputingTime();


        for (PortfolioSolution portfolioSolution : population) {
            System.out.println("List solution:");
            for (int i = 0; i < portfolioSolution.getNumberOfVariables(); i++) {
                System.out.println("    " + portfolioSolution.getVariableValue(i));
            }
        }

        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

//        printFinalSolutionSet(population);
//        if (!referenceParetoFront.equals("")) {
//            printQualityIndicators(population, referenceParetoFront);
//        }
    }

}
