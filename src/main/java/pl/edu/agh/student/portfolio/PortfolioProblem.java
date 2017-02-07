package pl.edu.agh.student.portfolio;

import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.impl.AbstractGenericProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.Random;

public class PortfolioProblem extends AbstractGenericProblem<DoubleSolution> implements DoubleProblem {

    Random random = new Random();

    double[] expectedReturns;
    double[][] covariance;

    public PortfolioProblem(double[] expectedReturns, double[][] covariance) {
        setNumberOfVariables(expectedReturns.length); // 3 // ilość assetów?
        setNumberOfObjectives(2);
        setNumberOfConstraints(2);
        setName("Portfolio");

        this.expectedReturns = expectedReturns;
        this.covariance = covariance;
    }

//    @Override
//    public void evaluate(PortfolioSolution solution) {
//        evaluate((DoubleSolution)solution);
//    }

    @Override
    public void evaluate(DoubleSolution solution) {
        double expectedReturn = 0;
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            expectedReturn += solution.getVariableValue(i) * expectedReturns[i];
        }

        double risk = 0;
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            for (int j = 0; j < solution.getNumberOfVariables(); j++) {
                risk += solution.getVariableValue(i) * solution.getVariableValue(j) * covariance[i][j];
            }
        }

        solution.setObjective(0, expectedReturn);
        solution.setObjective(1, -1.0 * risk);
    }

    @Override
    public PortfolioSolution createSolution() {
        int numberOfVariables = getNumberOfVariables();

        double[] values = new double[numberOfVariables];

        double sum = 0;
        for(int i = 0; i < numberOfVariables; i++) {
            double randomDouble = random.nextDouble();
            values[i] = randomDouble;
            sum += randomDouble;
        }

        PortfolioSolution portfolioSolution = new PortfolioSolution(numberOfVariables, getNumberOfObjectives());

        for(int i = 0; i < numberOfVariables; i++) {
            portfolioSolution.setVariableValue(i, values[i] / sum);
        }

        return portfolioSolution;

//        ArrayList<Integer> list = new ArrayList<>();
//        for (int i = 0; i < expectedReturns.length; i++) {
//            list.add(i);
//        }
//
//        Collections.shuffle(list);
//
//        int numberOfElements = random.nextInt(expectedReturns.length);
//
//        pl.edu.agh.student.portfolio.PortfolioSolution solution = new pl.edu.agh.student.portfolio.PortfolioSolution(numberOfElements, getNumberOfObjectives());
//
//        for (int i = 0; i < numberOfElements; i++) {
//            solution.setVariableValue(i, list.get(i));
//        }
//
//        return solution;
    }

    @Override
    public Double getLowerBound(int index) {
        return null;
    }

    @Override
    public Double getUpperBound(int index) {
        return null;
    }
}
