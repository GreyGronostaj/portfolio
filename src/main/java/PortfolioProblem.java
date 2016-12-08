import org.uma.jmetal.problem.impl.AbstractGenericProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PortfolioProblem extends AbstractGenericProblem<PortfolioSolution> {

    Random random = new Random();

    double[] expectedReturn;
    double[][] covariance;
    double[] weight;

    public PortfolioProblem(double[] expectedReturn, double[] weight, double[][] covariance) {
        setNumberOfVariables(3); // ilość assetów?
        setNumberOfObjectives(2);
        setNumberOfConstraints(2);
        setName("Portfolio");

        this.expectedReturn = expectedReturn;
        this.covariance = covariance;
        this.weight = weight;
    }

    @Override
    public void evaluate(PortfolioSolution solution) {

        double expectedReturn = 0;
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            Integer iValue = solution.getVariableValue(i);
            expectedReturn += weight[iValue] * this.expectedReturn[iValue];
        }

        double risk = 0;
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            for (int j = 0; j < solution.getNumberOfVariables(); j++) {
                Integer iValue = solution.getVariableValue(i);
                Integer jValue = solution.getVariableValue(j);
                risk += weight[iValue] * weight[jValue] * covariance[iValue][jValue];
            }
        }

        solution.setObjective(0, expectedReturn);
        solution.setObjective(1, -1.0 * risk);
    }

    @Override
    public PortfolioSolution createSolution() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < expectedReturn.length; i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        int numberOfElements = random.nextInt(expectedReturn.length);

        PortfolioSolution solution = new PortfolioSolution(numberOfElements, getNumberOfObjectives());

        for (int i = 0; i < numberOfElements; i++) {
            solution.setVariableValue(i, list.get(i));
        }

        return solution;
    }
}
