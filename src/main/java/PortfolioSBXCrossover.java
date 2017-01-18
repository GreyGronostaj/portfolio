import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.List;

public class PortfolioSBXCrossover extends SBXCrossover {

    public PortfolioSBXCrossover(double crossoverProbability, double distributionIndex) {
        super(crossoverProbability, distributionIndex);
    }

    @Override
    public List<DoubleSolution> execute(List<DoubleSolution> solutions) {
        solutions = super.execute(solutions);
        for (DoubleSolution solution : solutions) {
            ((PortfolioSolution)solution).repair();
        }
        return solutions;
    }
}
