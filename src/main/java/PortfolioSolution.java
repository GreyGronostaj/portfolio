import org.uma.jmetal.solution.Solution;

import java.util.HashMap;
import java.util.Map;

public class PortfolioSolution implements Solution<Integer> {

    private double[] objectives;
    private int[] variables;
    private Map<Object, Object> attributes;

    public PortfolioSolution(int numberOfVariables, int numberOfObjectives) {
        variables = new int[numberOfVariables];
        objectives = new double[numberOfObjectives];
        attributes = new HashMap<>();
    }

    @Override
    public void setObjective(int index, double value) {
        objectives[index] = value;
    }

    @Override
    public double getObjective(int index) {
        return objectives[index];
    }

    @Override
    public Integer getVariableValue(int index) {
        return variables[index];
    }

    @Override
    public void setVariableValue(int index, Integer value) {
        variables[index] = value;
    }

    @Override
    public String getVariableValueString(int index) {
        return variables.toString();
    }

    @Override
    public int getNumberOfVariables() {
        return variables.length;
    }

    @Override
    public int getNumberOfObjectives() {
        return objectives.length;
    }

    @Override
    public Solution copy() {
        return null;
    }

    @Override
    public void setAttribute(Object id, Object value) {
        attributes.put(id, value) ;
    }

    @Override
    public Object getAttribute(Object id) {
        return attributes.get(id) ;
    }

}
