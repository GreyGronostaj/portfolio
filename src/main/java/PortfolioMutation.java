import org.uma.jmetal.operator.MutationOperator;

public class PortfolioMutation implements MutationOperator<PortfolioSolution> {

    @Override
    public PortfolioSolution execute(PortfolioSolution portfolioSolution) {
        return portfolioSolution;
    }

}
