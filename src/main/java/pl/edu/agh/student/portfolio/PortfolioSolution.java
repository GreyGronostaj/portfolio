package pl.edu.agh.student.portfolio;

import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;

import java.util.HashMap;
import java.util.Map;

public class PortfolioSolution implements DoubleSolution {

    private double[] objectives;
    private double[] variables;
    private Map<Object, Object> attributes;

    public PortfolioSolution(int numberOfVariables, int numberOfObjectives) {
        variables = new double[numberOfVariables];
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
    public Double getVariableValue(int index) {
        return variables[index];
    }

    @Override
    public void setVariableValue(int index, Double value) {
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
    public Solution<Double> copy() {
        int numberOfVariables = getNumberOfVariables();
        int numberOfObjectives = getNumberOfObjectives();
        PortfolioSolution portfolioSolution = new PortfolioSolution(numberOfVariables, numberOfObjectives);
        for (int i = 0; i < numberOfVariables; i++) {
            portfolioSolution.setVariableValue(i, getVariableValue(i));
        }
        for (int i = 0; i < numberOfObjectives; i++) {
            portfolioSolution.setObjective(i, getObjective(i));
        }
        for (Object attributeKey : attributes.keySet()) {
            portfolioSolution.setAttribute(attributeKey, getAttribute(attributeKey));
        }
        return portfolioSolution;
    }

    @Override
    public void setAttribute(Object id, Object value) {
        attributes.put(id, value) ;
    }

    @Override
    public Object getAttribute(Object id) {
        return attributes.get(id) ;
    }

    @Override
    public Double getLowerBound(int index) {
        return 0.0;
    }

    @Override
    public Double getUpperBound(int index) {
        return 1.0;
    }

    public void repair() {
        double sum = 0;
        for(int i = 0; i < getNumberOfVariables(); i++) {
            sum += getVariableValue(i);
        }
        for(int i = 0; i < getNumberOfVariables(); i++) {
            setVariableValue(i, getVariableValue(i) / sum);
        }
    }

    public double[] getObjectives() {
        return objectives;
    }

    public double[] getVariables() {
        return variables;
    }

}
