import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class PortfolioRunner {

    public static void main(String[] args) {
        double[] expectedReturn = {0, 10, 5, 0};
        double[][] covariance = {
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1}
        };

        for (int i = 0; i < expectedReturn.length; i++) expectedReturn[i] = -expectedReturn[i];

        PortfolioProblem problem = new PortfolioProblem(expectedReturn, covariance);
        SPEA2 algorithm;

        BinaryTournamentSelection selection;

        double crossoverProbability = 0.125;
        double crossoverDistributionIndex = 20.0;
        SBXCrossover crossover = new PortfolioSBXCrossover(crossoverProbability, crossoverDistributionIndex); //crossoverProbability, crossoverDistributionIndex

        double mutationProbability = 0.9; //1.0 / problem.getNumberOfVariables();
        double mutationDistributionIndex = 20.0;
        PolynomialMutation mutation = new PortfolioPolynomialMutation(mutationProbability, mutationDistributionIndex); //(mutationProbability, mutationDistributionIndex);

        selection = new BinaryTournamentSelection<>(new PortfolioComparator());

        algorithm = new SPEA2Builder(problem, crossover, mutation)
                .setSelectionOperator(selection)
                .setMaxIterations(500)
                .setPopulationSize(50)
                .build();

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        List<PortfolioSolution> population = algorithm.getResult();

        long computingTime = algorithmRunner.getComputingTime();

        population.sort(Comparator.comparingDouble(solution ->
                solution.getObjective(0) * solution.getObjective(0) + solution.getObjective(1)));

        for (PortfolioSolution portfolioSolution : population) {
            System.out.println("Portfolio solution");
            for (int i = 0; i < portfolioSolution.getNumberOfVariables(); i++) {
                System.out.println("    " + portfolioSolution.getVariableValue(i));
            }
            BigDecimal fitness = new BigDecimal(portfolioSolution.getObjective(0) * portfolioSolution.getObjective(0) +
                    portfolioSolution.getObjective(1));
            fitness.setScale(2, BigDecimal.ROUND_UP);
            System.out.println("    Fitness: " + Math.round(fitness.doubleValue()) + ".");
        }

        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms.");

//        printFinalSolutionSet(population);
//        if (!referenceParetoFront.equals("")) {
//            printQualityIndicators(population, referenceParetoFront);
//        }
    }

}
