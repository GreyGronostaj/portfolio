package pl.edu.agh.student.portfolio;

import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.impl.JavaRandomGenerator;

import java.util.List;

public class PortfolioRunner {

    public static List<PortfolioSolution> run(double[] expectedReturns, double[][] covarianceMatrix, int maxIterations, int populationSize, double crossoverProbability, double crossoverDistributionIndex, double mutationProbability, double mutationDistributionIndex, long seed) {
        JMetalRandom.getInstance().setRandomGenerator(new JavaRandomGenerator(seed));

        PortfolioProblem problem = new PortfolioProblem(expectedReturns, covarianceMatrix);
        SPEA2<PortfolioSolution> algorithm;

        SBXCrossover crossover = new PortfolioSBXCrossover(crossoverProbability, crossoverDistributionIndex); //crossoverProbability, crossoverDistributionIndex

        PolynomialMutation mutation = new PortfolioPolynomialMutation(mutationProbability, mutationDistributionIndex); //(mutationProbability, mutationDistributionIndex);

        BinaryTournamentSelection selection = new BinaryTournamentSelection<>(new PortfolioComparator());

        // noinspection unchecked
        algorithm = new SPEA2Builder<>(problem, crossover, mutation)
                .setSelectionOperator(selection)
                .setMaxIterations(maxIterations)
                .setPopulationSize(populationSize)
                .build();

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        List<PortfolioSolution> population = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();


        System.out.println(maxIterations + " iterations");

//        population.sort(Comparator.comparingDouble(solution ->
//                solution.getObjective(1)));
//
//        population.sort(Comparator.comparingDouble(solution ->
//                -solution.getObjective(0)));
//
//        for (PortfolioSolution portfolioSolution : population) {
//            System.out.println("Portfolio solution");
//            for (int i = 0; i < portfolioSolution.getNumberOfVariables(); i++) {
//                BigDecimal variableValue = new BigDecimal(portfolioSolution.getVariableValue(i));
//                System.out.println("    " + variableValue.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
//            }
//            BigDecimal objective1 = new BigDecimal(portfolioSolution.getObjective(0));
//            System.out.println("    Potential return: " + -1 * objective1.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue() + ".");
//
//            BigDecimal objective2 = new BigDecimal(portfolioSolution.getObjective(1));
//            System.out.println("    Risk: " + objective2.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue() + ".");
//        }

        System.out.println("    Total execution time: " + computingTime + "ms.");

        return population;
    }
}
