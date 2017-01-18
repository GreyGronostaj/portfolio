import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.solution.DoubleSolution;

public class PortfolioPolynomialMutation extends PolynomialMutation {

    public PortfolioPolynomialMutation(double mutationProbability, double distributionIndex) {
        super(mutationProbability, distributionIndex);
    }

    @Override
    public DoubleSolution execute(DoubleSolution solution) {
        solution = super.execute(solution);
        ((PortfolioSolution)solution).repair();
        return solution;
    }
}
