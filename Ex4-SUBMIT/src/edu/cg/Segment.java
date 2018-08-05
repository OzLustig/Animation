package edu.cg;

import Jama.Matrix;
import edu.cg.CyclicList;
import edu.cg.algebra.Point;
import java.util.ArrayList;
import java.util.List;

public class Segment {
	public double length = 0;
	public Spline spline = null;

	private Segment(Spline spline) {
		this.spline = spline;
		length = spline.getLength();
	}

	private static CyclicList<Polynom> getXPolynoms(CyclicList<Point> points) {
		ArrayList<Equation> constraintsList = new ArrayList<Equation>(points.getSize() * 4);
		for (int pointIndex = 0; pointIndex < points.getSize(); pointIndex++) {
			float point_i_xValue = points.get(pointIndex).x;
			constraintsList.addAll(Equation.createEquationsList(point_i_xValue, pointIndex, points.getSize()));
		}
		float[][] polynoms = Segment.findSolution(constraintsList);
		CyclicList<Polynom> xPolynoms = new CyclicList<Polynom>();
		for (int i = 0; i < polynoms.length; i++) {
			float a = polynoms[i][0];
			float b = polynoms[i][1];
			float c = polynoms[i][2];
			float d = polynoms[i][3];
			xPolynoms.add(new Polynom(a, b, c, d));
		}
		return xPolynoms;
	}

	private static CyclicList<Polynom> getYPolynoms(CyclicList<Point> points) {
		ArrayList<Equation> constraints = new ArrayList<Equation>(points.getSize() * 4);
		for (int i = 0; i < points.getSize(); i++) {
			float p = points.get(i).y;
			constraints.addAll(Equation.createEquationsList(p, i, points.getSize()));
		}
		float[][] polynoms = Segment.findSolution(constraints);
		CyclicList<Polynom> yPolynoms = new CyclicList<Polynom>();
		for (int i = 0; i < polynoms.length; i++) {
			float a = polynoms[i][0];
			float b = polynoms[i][1];
			float c = polynoms[i][2];
			float d = polynoms[i][3];
			yPolynoms.add(new Polynom(a, b, c, d));
		}
		return yPolynoms;
	}

	private static CyclicList<Polynom> getZPolynoms(CyclicList<Point> points) {
		ArrayList<Equation> constraints = new ArrayList<Equation>(points.getSize() * 4);
		for (int i = 0; i < points.getSize(); i++) {
			float p = points.get(i).z;
			constraints.addAll(Equation.createEquationsList(p, i, points.getSize()));
		}
		float[][] polynoms = Segment.findSolution(constraints);
		CyclicList<Polynom> zPolynoms = new CyclicList<Polynom>();
		for (int i = 0; i < polynoms.length; i++) {
			float a = polynoms[i][0];
			float b = polynoms[i][1];
			float c = polynoms[i][2];
			float d = polynoms[i][3];
			zPolynoms.add(new Polynom(a, b, c, d));
		}
		return zPolynoms;
	}

	private static Matrix buildAMtrix(List<Equation> constraints, int numberOfPoints) {
		double[][] A = new double[numberOfPoints*4][numberOfPoints*4];
		for (int i = 0; i < constraints.size(); i++) {
			Equation constraint = constraints.get(i);
			A[i] = constraint.getAMatRow();
		}
		return new Matrix(A);
	}
	
	private static Matrix buildBVec(List<Equation> constraints, int numberOfPoints) {
		double[] B = new double[numberOfPoints*4];
		for (int i = 0; i < constraints.size(); i++) {
			Equation constraint = constraints.get(i);
			B[i] = constraint.q;
		}
		return new Matrix(B, constraints.size());
	}
	
	private static float[][] findSolution(List<Equation> constraints) {
		Matrix A = buildAMtrix(constraints, constraints.size()/4);
		Matrix b = buildBVec(constraints, constraints.size()/4);
		Matrix result = A.solve(b);
		float[][] polynoms = new float[constraints.size()/4][4];
		int j=0;
		for (int i = 0; i < constraints.size(); i+=4) {
				polynoms[j][0] = (float)result.get(i, 0);
				polynoms[j][1] = (float)result.get(i+1, 0);
				polynoms[j][2] = (float)result.get(i+2, 0);
				polynoms[j][3] = (float)result.get(i+3, 0);
				j+=1;
		}
		return polynoms;
	}

	public static CyclicList<Segment> getSegmentsCyclicList(CyclicList<Point> points) {
		CyclicList<Polynom> xSegments = Segment.getXPolynoms(points);
		CyclicList<Polynom> ySegments = Segment.getYPolynoms(points);
		CyclicList<Polynom> zSegments = Segment.getZPolynoms(points);
		CyclicList<Segment> segmentsCyclicList = new CyclicList<Segment>();
		for (int i = 0; i < points.size(); i++) {
			Polynom xPolynom_i = xSegments.get(i);
			Polynom yPolynom_i = ySegments.get(i);
			Polynom zPolynom_i = zSegments.get(i);
			Spline currentSpline = new Spline(xPolynom_i, yPolynom_i, zPolynom_i);
			Segment currentSegment = new Segment(currentSpline);
			segmentsCyclicList.add(currentSegment);
		}
		return segmentsCyclicList;
	}
}