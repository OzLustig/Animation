package edu.cg;
import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;
public class Spline {
    public Polynom[] polynomsArr = new Polynom[3];
    private double[] sectionLengthsArr;

    public Spline(Polynom polynom_x, Polynom polynom_y, Polynom polynom_z) {
        this.polynomsArr[0] = polynom_x;
        this.polynomsArr[1] = polynom_y;
        this.polynomsArr[2] = polynom_z;
    }

    public double getLength() {
        double t = 0;
        int sectionNum = 1024;
        double dist = 1.0 / (double)sectionNum;
        this.sectionLengthsArr = new double[sectionNum];
        for (int i = 0; i < sectionLengthsArr.length; i++) {
			
            Point start = calcStartEnd(t);
            Point end = calcStartEnd(t + dist);
            sectionLengthsArr[i] = start.sub(end).length();
            t += dist;
        }
        return reymanSum();
    }

    private double reymanSum() {
    	double sum=0;
    	for (int i = 0; i < sectionLengthsArr.length; i++) {
			sum += sectionLengthsArr[i];
		}
        return  sum;
    }

    public Point calcStartEnd(double t) {
        double x = polynomsArr[0].getValueAtT(t);
        double y = polynomsArr[1].getValueAtT(t);
        double z = polynomsArr[2].getValueAtT(t);
        Point result = new Point(x,y,z);
        return result;
    }

    public Vec derivatives(double t) {
    	double dx = polynomsArr[0].getFirstDerivativeAtT(t);
    	double dy = polynomsArr[1].getFirstDerivativeAtT(t);
    	double dz = polynomsArr[2].getFirstDerivativeAtT(t);
    	Vec result = new Vec(dx,dy,dz);
        return result.normalize();
    }

    public Vec splineNormal(double t) {
    	double dx = polynomsArr[0].getFirstDerivativeAtT(t);
        double dy = polynomsArr[1].getFirstDerivativeAtT(t);
        double dz = polynomsArr[2].getFirstDerivativeAtT(t);
    	double ddx = polynomsArr[0].getSecondDerivativeAtT(t);
    	double ddy = polynomsArr[1].getSecondDerivativeAtT(t);
    	double ddz = polynomsArr[2].getSecondDerivativeAtT(t);
    	Vec pointFirstDerivVec = new Vec(dx, dy, dz);
        Vec pointSecondDerivVec = new Vec(ddx, ddy, ddz);
        Vec normal = pointFirstDerivVec.cross(pointSecondDerivVec).cross(pointFirstDerivVec).normalize();
        return normal;
    }
}
