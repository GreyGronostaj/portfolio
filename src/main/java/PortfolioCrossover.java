import org.uma.jmetal.operator.CrossoverOperator;

import java.util.*;

public class PortfolioCrossover implements CrossoverOperator<PortfolioSolution> {

    int numberOfParents;

    @Override
    public List<PortfolioSolution> execute(List<PortfolioSolution> portfolioSolutions) {
        numberOfParents = portfolioSolutions.size();
        int numberOfVariables = portfolioSolutions.get(0).getNumberOfVariables();
        int numberOfObjectives = portfolioSolutions.get(0).getNumberOfObjectives();

        PortfolioSolution newPortfolioSolution = new PortfolioSolution(numberOfVariables, numberOfObjectives);

        for (int i = 0; i < numberOfVariables; i++) {
            double sum = 0;
            for (PortfolioSolution portfolioSolution: portfolioSolutions) {
                sum += portfolioSolution.getVariableValue(i);
            }
            newPortfolioSolution.setVariableValue(i, sum / portfolioSolutions.size());
        }

        portfolioSolutions = new ArrayList<>();
        portfolioSolutions.add(newPortfolioSolution);
        return portfolioSolutions;

//        List<Integer> arrayList = new ArrayList();
//        arrayList.addAll(values);
//        Collections.shuffle(arrayList);
//
//        if (arrayList.size() > 1 ) {
//            arrayList = arrayList.subList(0, arrayList.size() / 2);
//            PortfolioSolution portfolioSolution = new PortfolioSolution(arrayList.size(), 2);
//            for (int i = 0; i < arrayList.size(); i++) {
//                portfolioSolution.setVariableValue(i, arrayList.get(i));
//            }
//            return new ArrayList<>(portfolioSolutions);
//        } else if (arrayList.size() == 1){
//            PortfolioSolution portfolioSolution = new PortfolioSolution(1, 2);
//            portfolioSolution.setVariableValue(0, arrayList.get(0));
//            portfolioSolutions = new ArrayList<>();
//            portfolioSolutions.add(portfolioSolution);
//            return portfolioSolutions;
//        }
//        portfolioSolutions = new ArrayList<>();
//        portfolioSolutions.add(new PortfolioSolution(0, 2));
//        return portfolioSolutions;
    }

    @Override
    public int getNumberOfParents() {
        return numberOfParents;
    }
}
