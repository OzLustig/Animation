package edu.cg;

import java.util.ArrayList;
import java.util.List;

class Equation {
        private double a1 = 0;
        private double b1 = 0;
        private double c1 = 0;
        private double d1 = 0;
        private double a2 = 0;
        private double b2 = 0;
        private double c2 = 0;
        private double d2 = 0;
        public double q = 0;
        private int pointIndex;
        private int numberOfPoints;

        private Equation(int i, int numberOfPoints) {
            pointIndex = i;
            this.numberOfPoints = numberOfPoints;
        }
        
        public double[] getAMatRow() {
            double[] rowInMatrixA = new double[numberOfPoints * 4];
            int columnIndex = pointIndex * 4;
            rowInMatrixA[columnIndex] = a1;
            rowInMatrixA[columnIndex + 1] = b1;
            rowInMatrixA[columnIndex + 2] = c1;
            rowInMatrixA[columnIndex + 3] = d1;
            columnIndex += 4;
            if(columnIndex == rowInMatrixA.length) {
            	columnIndex = 0;
            	rowInMatrixA[columnIndex] = a2;
                rowInMatrixA[columnIndex + 1] = b2;
                rowInMatrixA[columnIndex + 2] = c2;
                rowInMatrixA[columnIndex + 3] = d2;
            }
            else 
            {
            rowInMatrixA[columnIndex] = a2;
            rowInMatrixA[columnIndex + 1] = b2;
            rowInMatrixA[columnIndex + 2] = c2;
            rowInMatrixA[columnIndex + 3] = d2;
            }
            return rowInMatrixA;
        }

        public static List<Equation> createEquationsList(float pointAxeValue, int pointIndex, int numberOfPoints) {
            ArrayList<Equation> constraints = new ArrayList<Equation>(4);
            Equation firstEquation = new Equation(pointIndex, numberOfPoints);
            Equation secondEquation = new Equation(pointIndex, numberOfPoints);
            Equation thirdEquation = new Equation(pointIndex, numberOfPoints);
            Equation fourthEquation  = new Equation(pointIndex, numberOfPoints);
            firstEquation.q = pointAxeValue;
            firstEquation.d2 = 1;
            secondEquation.d1 = 1;
            secondEquation.c1 = 1;
            secondEquation.b1 = 1;
            secondEquation.a1 = 1;
            secondEquation.d2 = -1;
            thirdEquation.a1 = 3;
            thirdEquation.b1 = 2;
            thirdEquation.c1 = 1;
            thirdEquation.c2 = -1;
            fourthEquation.a1 = 3;
            fourthEquation.b1 = 1;
            fourthEquation.b2 = -1;
            constraints.add(firstEquation);
            constraints.add(secondEquation);
            constraints.add(thirdEquation);
            constraints.add(fourthEquation);
            return constraints;
        }
    }