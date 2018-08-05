package edu.cg.models;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.FloatBuffer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import edu.cg.CyclicList;
import edu.cg.TrackPoints;
import edu.cg.Segment;
import edu.cg.Location;
import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;

public class Track implements IRenderable {
	private IRenderable vehicle;
	private CyclicList<Point> trackPoints;
	private Texture texGrass = null;
	private Texture texTrack = null;
	private CyclicList<Segment> segments = null;
	private double t = 0;
	private int currentSegmentIndex = 0;
	double speed = 0.01;

	public Track(IRenderable vehicle, CyclicList<Point> trackPoints) {
		this.vehicle = vehicle;
		this.trackPoints = trackPoints;
	}

	public Track(IRenderable vehicle) {
		this(vehicle, TrackPoints.track3());
	}

	public Track() {
		//TODO: uncomment this and change it if for your needs.
		this(new Locomotive());
	}
	@Override
	public void init(GL2 gl) {
		//TODO: Build your track splines here.
		//Compute the length of each spline.
		//Do not repeat those calculations over and over in the render method.
		//It will make the application to run not smooth. 
		
		segments = Segment.getSegmentsCyclicList(trackPoints);
		loadTextures(gl);
		vehicle.init(gl);
	}

	private void loadTextures(GL2 gl) {
		File fileGrass = new File("grass.jpg");
		File fileRoad = new File("track.png");
		try {
			texTrack = TextureIO.newTexture(fileRoad, true);
			texGrass = TextureIO.newTexture(fileGrass, false);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GL2 gl) {
		renderVehicle(gl);
		renderField(gl);
		renderTrack(gl);
	}

	private void renderVehicle(GL2 gl2) {
		gl2.glPushMatrix();
		Segment currentSegment = drawVehicle(gl2);
		gl2.glPopMatrix();
		double dt = speed / currentSegment.length;
		t += dt;
		calcCurrentSegmentIndex(); 
	}

	private Segment drawVehicle(GL2 gl2) {
		Segment currentSegment = segments.get(currentSegmentIndex);
		Location currentLocation = new Location(t, currentSegment.spline);
		gl2.glTranslatef(currentLocation.pos.x, currentLocation.pos.y, currentLocation.pos.z);
		double[] matrix = new double[]{
				currentLocation.tangent.neg().x, currentLocation.tangent.neg().y, currentLocation.tangent.neg().z, 0,
				currentLocation.normal.x, currentLocation.normal.y, currentLocation.normal.z, 0,
				currentLocation.rightVector().neg().x, currentLocation.rightVector().neg().y, currentLocation.rightVector().neg().z, 0,
				0, 0, 0, 1};
		gl2.glMultMatrixd(matrix, 0);
		gl2.glScaled(.2, .2, .2);
		gl2.glTranslated(0,.3,0);
		vehicle.render(gl2);
		return currentSegment;
	}

	private void calcCurrentSegmentIndex() {
		if (t < 0) 
		{
			t = t + 1;
			currentSegmentIndex = (currentSegmentIndex - 1) % segments.size();
		}
		else if (t > 1)
		{
			t = t - 1;
			currentSegmentIndex = (currentSegmentIndex + 1) % segments.size();
		}
	}

	private void renderField(GL2 gl) {
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texGrass.getTextureObject());
		
		boolean lightningEnabled;
		if((lightningEnabled = gl.glIsEnabled(GL2.GL_LIGHTING)))
			gl.glDisable(GL2.GL_LIGHTING);

		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAX_LOD, 1);
		
		gl.glBegin(GL2.GL_QUADS);
		
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(-1.2, -1.2, -.02);
		gl.glTexCoord2d(4, 0);
		gl.glVertex3d(1.2, -1.2, -.02);
		gl.glTexCoord2d(4, 4);
		gl.glVertex3d(1.2, 1.2, -.02);
		gl.glTexCoord2d(0, 4);
		gl.glVertex3d(-1.2, 1.2, -.02);
		
		gl.glEnd();
		
		if(lightningEnabled)
			gl.glEnable(GL2.GL_LIGHTING);
		
		gl.glDisable(GL2.GL_TEXTURE_2D);
	}


	private void renderTrack(GL2 gl) {
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texTrack.getTextureObject());
		boolean lightningEnabled;
		if((lightningEnabled = gl.glIsEnabled(GL2.GL_LIGHTING)))
			gl.glDisable(GL2.GL_LIGHTING);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAX_LOD, 2);
		drawTrack(gl);
		if(lightningEnabled)
			gl.glEnable(GL2.GL_LIGHTING);
		gl.glDisable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_TEXTURE_2D);
	}

	private void drawTrack(GL2 gl) {
		gl.glBegin(gl.GL_TRIANGLES);
		for (int i = 0; i < segments.size(); i++) {
			double length = segments.get(i).length;
			int numOfSubSegments = (int)(length / 0.1) + 1;
			double dt = 1 / (double)numOfSubSegments;
			double t = 0;
			for (int j = 0; j < numOfSubSegments; j++) {
				Location start = new Location(t, segments.get(i).spline);
				Location end = new Location(t + dt, segments.get(i).spline);
				Vec rightVectorStart = start.rightVector();
				Vec rightVectorEnd = end.rightVector();
				Vec k = rightVectorStart.mult((float)0.1);
				Vec l = rightVectorStart.mult((float)-0.1);
				Vec m = rightVectorEnd.mult((float)0.1);
				Vec n = rightVectorEnd.mult((float)-0.1);
				Point p0 = start.pos.add(k);
				Point p3 = start.pos.add(l);
				Point p1 = end.pos.add(m);
				Point p2 = end.pos.add(n);
				FloatBuffer fp0 = FloatBuffer.wrap(new float[]{p0.x, p0.y, p0.z});
				FloatBuffer fp1 = FloatBuffer.wrap(new float[]{p1.x, p1.y, p1.z});
				FloatBuffer fp2 = FloatBuffer.wrap(new float[]{p2.x, p2.y, p2.z});
				FloatBuffer fp3 = FloatBuffer.wrap(new float[]{p3.x, p3.y, p3.z});
				gl.glTexCoord2d(0, 0);
				gl.glVertex3fv(fp0);
				gl.glTexCoord2d(0, 1);
				gl.glVertex3fv(fp1);
				gl.glTexCoord2d(1, 1);
				gl.glVertex3fv(fp2);
				gl.glTexCoord2d(0, 0);
				gl.glVertex3fv(fp0);
				gl.glTexCoord2d(1, 1);
				gl.glVertex3fv(fp2);
				gl.glTexCoord2d(0, 1);
				gl.glVertex3fv(fp1);
				gl.glTexCoord2d(0, 0);
				gl.glVertex3fv(fp0);
				gl.glTexCoord2d(1, 1);
				gl.glVertex3fv(fp2);
				gl.glTexCoord2d(1, 0);
				gl.glVertex3fv(fp3);
				gl.glTexCoord2d(0, 0);
				gl.glVertex3fv(fp0);
				gl.glTexCoord2d(1, 0);
				gl.glVertex3fv(fp3);
				gl.glTexCoord2d(1, 1);
				gl.glVertex3fv(fp2);
				t += dt;
			}
		}
		gl.glEnd();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void control(int type, Object params) {
		switch(type) {
		case KeyEvent.VK_UP:
			speed += 0.01;
			break;

		case KeyEvent.VK_DOWN:
			speed -= 0.01;
			break;

		case KeyEvent.VK_ENTER:
			try {
				Method m = TrackPoints.class.getMethod("track" + params);
				trackPoints = (CyclicList<Point>)m.invoke(null);
				//TODO: replace the track with the new one...
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;


		case IRenderable.TOGGLE_LIGHT_SPHERES:
			vehicle.control(type, params);
			break;

		default:
			System.out.println("Unsupported operation for Track control");
		}
	}

	@Override
	public boolean isAnimated() {
		return true;
	}

	@Override
	public void setCamera(GL2 gl) {
		//You should use:
		//		GLU glu = new GLU();
		//		glu.gluLookAt(eye.x, eye.y, eye.z, center.x, center.y, center.z, up.x, up.y, up.z);
		//TODO: set the camera here to follow the locomotive...
		Segment segment = segments.get(currentSegmentIndex);
		Location eye = new Location(t, segment.spline);
		Point center = eye.pos.add(eye.tangent);
		eye.pos = eye.pos.add(eye.tangent.mult((float)-0.5)).add(eye.normal.mult((float)0.5)).add(eye.rightVector().mult((float)-0.1));
		GLU glu = new GLU();
		Vec up = eye.normal;
		glu.gluLookAt(eye.pos.x, eye.pos.y, eye.pos.z, center.x, center.y, center.z, up.x, up.y, up.z);
	}

	@Override
	public void destroy(GL2 gl) {	
		texGrass.destroy(gl);
		texTrack.destroy(gl);
		vehicle.destroy(gl);
	}

}
