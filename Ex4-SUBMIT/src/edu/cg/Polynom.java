package edu.cg;

public class Polynom {
    private double a;
    private double b;
    private double c;
    private double d;

    public Polynom(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public double getValueAtT(double t) {
    	double val = ( Math.pow(t, 3) * a ) + ( Math.pow(t, 2) * b ) + (t*c) +d;
        return val;
    }

    public double getFirstDerivativeAtT(double t) {
    	double val = ( Math.pow(t, 2) * 3*a ) + ( t * 2 * b ) + c;
        return val;
    }

    public double getSecondDerivativeAtT(double t) {
    	double val = 6*a*t + 2*b;
        return val;
    }
}
