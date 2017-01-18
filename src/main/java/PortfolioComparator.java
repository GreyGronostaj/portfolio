import java.io.Serializable;
import java.util.Comparator;

public class PortfolioComparator implements Comparator<PortfolioSolution>, Serializable {

    @Override
    public int compare(PortfolioSolution o1, PortfolioSolution o2) {
        double o1ExpectedReturn = o1.getObjective(0);
        double o2ExpectedReturn = o2.getObjective(0);
        double o2Risk = o1.getObjective(1);
        double o1Risk = o2.getObjective(1);

        if (o1ExpectedReturn > o2ExpectedReturn) {
            if (o1Risk >= o2Risk) {
                return 1;
            }
            return 0;
        }

        if (o1ExpectedReturn == o2ExpectedReturn) {
            if (o1Risk > o2Risk) {
                return 1;
            } else if (o1Risk == o2Risk) {
                return 0;
            }
            return -1;
        }

        if (o1Risk > o2Risk) {
            return 0;
        }
        return -1;
    }

}
