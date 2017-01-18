import java.io.Serializable;
import java.util.Comparator;

public class PortfolioComparator implements Comparator<PortfolioSolution>, Serializable {

    @Override
    public int compare(PortfolioSolution o1, PortfolioSolution o2) {
        double o1result = o1.getObjective(0) * o1.getObjective(0) + o1.getObjective(1);
        double o2result = o2.getObjective(0) * o2.getObjective(0) + o2.getObjective(1);

//        if (o1.getObjective(0) == o2.getObjective(0)) {
//           if (o1.getObjective(1) < o2.getObjective(1)) {
//               return -1;
//           } else if (o1.getObjective(1) == o2.getObjective(1)) {
//               return 0;
//           }
//           return 1;
//        }
//
//        if (o1.getObjective(1) == o2.getObjective(1)) {
//            if (o1.getObjective(0) > o2.getObjective(0)) {
//                return -1;
//            } else if (o1.getObjective(0) == o2.getObjective(0)) {
//                return 0;
//            }
//            return 1;
//        }
//
//        if (o1.getObjective(0) > o2.getObjective(0)) {
//            if (o1.getObjective(1) > o2.getObjective(1)) {
//                return 0;
//            }
//            return -1;
//        }
//
//        if (o1.getObjective(0) < o2.getObjective(0)) {
//            if (o1.getObjective(1) < o2.getObjective(1)) {
//                return 0;
//            }
//            return 1;
//        }
//
//        if (o1.getObjective(1) < o2.getObjective(1)) {
//            if (o1.getObjective(0) < o2.getObjective(0)) {
//                return 0;
//            }
//            return -1;
//        }
//
//
//        if (o1.getObjective(1) > o2.getObjective(1)) {
//            if (o1.getObjective(0) > o2.getObjective(0)) {
//                return 0;
//            }
//            return 1;
//        }
//
//        return 0;



//        if (o1.getObjective(0) > o2.getObjective(0)) {
//            if (o1.getObjective(1) < o2.getObjective(1)) {
//                return -1;
//            }
//            return 0;
//        } else if (o1.getObjective(0) < o2.getObjective(0)) {
//            if (o1.getObjective(1) > o2.getObjective(1)) {
//                return 1;
//            }
//            return 0;
//        }
//        return 1;




        if (o1result < o2result) {
            return -1;
        } else if (o1result == o2result) {
            return 0;
        }
        return 1;
    }

}
