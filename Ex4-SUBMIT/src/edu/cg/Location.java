package edu.cg;

import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;

public class Location {
    public Point pos;
    public Vec normal;
    public Vec tangent;

    public Location(double t, Spline spline) {
        double x = spline.polynomsArr[0].getValueAtT(t);
        double y = spline.polynomsArr[1].getValueAtT(t);
        double z = spline.polynomsArr[2].getValueAtT(t);
        pos = new Point(x,y,z);
		tangent = spline.derivatives(t);
		normal = spline.splineNormal(t);
	}
    
    public Vec rightVector() {
        return tangent.cross(normal);
    }
}
