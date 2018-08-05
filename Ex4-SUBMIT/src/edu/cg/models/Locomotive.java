package edu.cg.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class Locomotive implements IRenderable
{
	// Define class fields.

	private float windowR=0.3f,windowG=0.3f,windowB=0.6f;
	private float doorR=1f,doorG=1f,doorB=1f;
	private float wallR = 1f;
	private float wallB = 0.5f;
	private float wallG = 1f;
	private float wheelB = 0;
	private float wheelG = 0;
	private float wheelR = 0;
	private float plateR = (float)244/255;
	private float plateB = (float)160/255;
	private float plateG = (float)65/255;
	private float lightHouseB = (float)12/255;
	private float lightHouseG = (float)25/255;
	private float lightHouseR = (float)38/255;
	private float lightR = 1;
	private float lightG = 1;
	private float lightB = (float)179/255;
	private float roofG=0;
	private float roofR=0;
	private float roofB=0;
	

	@Override
	public void render(GL2 gl2) 
	{
		GLU glu = new GLU();
		GLUquadric gluQuadric = glu.gluNewQuadric();
		roofDrawing(gluQuadric,glu, gl2);
		drawChassis(gl2);
		chimneyDrawing(gluQuadric,glu, gl2);
		lightsDrawing(gluQuadric,glu, gl2);
		wheelsDrawing(gluQuadric,glu,gl2);
	}

	/**
	 * @param gl2
	 */
	private void drawChassis(GL2 gl2)
	{
		// Draw front and back windows
		gl2.glColor3f(windowR,windowG,windowB);
		gl2.glPushMatrix();

		gl2.glNormal3f(1f,0f,0f); 
		drawQuad(gl2,0.5,-0.05,0.5,0.15,0.15); 

		gl2.glPopMatrix();
		gl2.glPushMatrix();

		// Front window
		gl2.glNormal3f(-1f,0f,0f);
		drawQuad(gl2,-0.3,0.15,-0.3,0.0,0.15);

		gl2.glPopMatrix();


		gl2.glColor3f(wallR,wallG,wallB);

		gl2.glNormal3f(0f, 1f,0f);
		drawQuad(gl2, 0.5, 0.2, -0.3, 0.2, 0.2);
		drawQuad(gl2,-0.3,0.0,-0.8,0.0,0.2);
		gl2.glNormal3f(0f, -1f,0f);
		drawQuad(gl2,-0.8,-0.2,0.5,-0.2,0.2);

		gl2.glNormal3f(1f, 0f,0f);
		drawQuad(gl2,0.5,-0.2,0.5,0.2,0.2);
		gl2.glNormal3f(-1f, 0f,0f);
		drawQuad(gl2,-0.8,0.0,-0.8,-0.2,0.2);
		drawQuad(gl2,-0.3,0.2,-0.3,0.0,0.2);

		// Draw left side
		gl2.glFrontFace(GL2.GL_CW);
		gl2.glScaled(1,1,-1);

		windowDrawing(gl2, 0.3,0.19);
		windowDrawing(gl2,-0.2,0.19);
		windowDrawing(gl2, 0.05,0.19);

		gl2.glNormal3f(0f, 0f,-1f);
		wallDrawing(gl2);
		gl2.glScaled(1,1,-1); 
		gl2.glFrontFace(GL2.GL_CCW);

		// Draw right side
		gl2.glNormal3f(0f, 0f,-1f); 

		windowDrawing(gl2, 0.05,0.19);
		windowDrawing(gl2, 0.3,0.19);
		doorDrawing(gl2,-0.2,0.19);
		wallDrawing(gl2);

	}

	/**
	 * @param gluQuadric
	 * @param glu
	 * @param gl2
	 */
	private void roofDrawing(GLUquadric gluQuadric, GLU glu, GL2 gl2)
	{
		gl2.glColor3f(roofR,roofG,roofB);
		gl2.glPushMatrix();
		gl2.glTranslated(-0.3,0.2,0);
		gl2.glScaled(1,0.33,1);
		gl2.glRotated(90,0,1,0);
		glu.gluCylinder(gluQuadric,0.2,0.2,0.8,20,1);
		gl2.glRotated(180,0,1,0);
		glu.gluDisk(gluQuadric,0,0.2,50,1);
		gl2.glRotated(-180,0,1,0);
		gl2.glTranslated(0,0,0.8);
		glu.gluDisk(gluQuadric,0,0.2,50,1);
		gl2.glPopMatrix();
	}

	/**

	 * @param gl2
	 * @param x
	 * @param y
	 */
	private void doorDrawing(GL2 gl2, double x, double y)
	{
		gl2.glColor3f(doorR,doorG,doorB);
		gl2.glBegin(GL2.GL_QUADS);
		gl2.glVertex3d(x,y - 0.4,-0.2);
		gl2.glVertex3d(x,y,-0.2);
		gl2.glVertex3d(x + 0.15,y,-0.2);
		gl2.glVertex3d(x + 0.15,y - 0.4,-0.2);
		gl2.glEnd();
	}

	/**
	 * @param gl2
	 * @param x
	 * @param y
	 */
	private void windowDrawing(GL2 gl2,double x, double y)
	{
		gl2.glColor3f(windowR,windowG,windowB);
		gl2.glBegin(GL2.GL_QUADS);
		gl2.glVertex3d(x,y - 0.2,-0.2);
		gl2.glVertex3d(x,y,-0.2);
		gl2.glVertex3d(x + 0.15,y,-0.2);
		gl2.glVertex3d(x + 0.15,y - 0.2,-0.2);

		gl2.glEnd();
	}

	/**
	 * @param gl2
	 */
	private void wallDrawing(GL2 gl2)
	{
		gl2.glColor3f(wallR, wallG, wallB);
		gl2.glBegin(GL2.GL_QUADS);

		gl2.glVertex3d(0.5,0.2,-0.2);
		gl2.glVertex3d(0.5,-0.2,-0.2);
		gl2.glVertex3d(-0.3,-0.2,-0.2);
		gl2.glVertex3d(-0.3,0.2,-0.2);

		gl2.glVertex3d(-0.8,-0.2,-0.2);
		gl2.glVertex3d(-0.8,0.0,-0.2);
		gl2.glVertex3d(-0.3,0.0,-0.2);
		gl2.glVertex3d(-0.3,-0.2,-0.2);

		gl2.glEnd();
	}


	/**
	 * @param gluQuadric
	 * @param glu
	 * @param gl2
	 */
	private void wheelsDrawing(GLUquadric gluQuadric, GLU glu, GL2 gl2)
	{
		gl2.glPushMatrix();
		gl2.glTranslated(0.2,-0.2,0.2);
		wheelDrawing(gluQuadric, glu,gl2);

		gl2.glPopMatrix();
		gl2.glPushMatrix();
		gl2.glTranslated(-0.6,-0.2,0.2);
		wheelDrawing(gluQuadric, glu,gl2);

		gl2.glPopMatrix();
		gl2.glPushMatrix();
		gl2.glTranslated(-0.6,-0.2,-0.2);
		wheelDrawing(gluQuadric, glu,gl2);

		gl2.glPopMatrix();
		gl2.glPushMatrix();
		gl2.glTranslated(0.2,-0.2,-0.2);
		wheelDrawing(gluQuadric, glu,gl2);

		gl2.glPopMatrix();
	}

	/**
	 * @param gluQuadric
	 * @param glu
	 * @param gl2
	 */
	private void wheelDrawing(GLUquadric gluQuadric, GLU glu, GL2 gl2) 
	{
		gl2.glColor3f(wheelR,wheelG,wheelB);
		gl2.glTranslated(0,0,-0.05);
		glu.gluCylinder(gluQuadric, 0.1,0.1, 0.1,20, 1);
		gl2.glRotated(180, 1, 0, 0);
		glu.gluDisk(gluQuadric,0.07,0.1,50,1);
		gl2.glRotated(-180, 1, 0, 0);
		gl2.glTranslated(0,0,0.1);
		glu.gluDisk(gluQuadric,0.07,0.1,50,1);

		gl2.glColor3f(plateR,plateG,plateB);
		glu.gluDisk(gluQuadric,0,0.07,50,1);
		gl2.glTranslated(0,0,-0.1);
		gl2.glRotated(180, 1, 0, 0);
		glu.gluDisk(gluQuadric,0,0.07,50,1);
		gl2.glRotated(-180, 1, 0, 0);
	}

	/**
	 * @param gluQuadric
	 * @param glu
	 * @param gl2
	 */
	private void lightsDrawing(GLUquadric gluQuadric, GLU glu, GL2 gl2)
	{
		gl2.glPushMatrix();
		gl2.glTranslated(-0.825,-0.1,-0.1);
		gl2.glRotated(90,0,1,0);

		lightDrawing(gluQuadric, glu, gl2);

		gl2.glRotated(-90,0,1,0);
		gl2.glTranslated(0,0,0.2);
		gl2.glRotated(90,0,1,0);
		lightDrawing(gluQuadric,glu, gl2);

		gl2.glRotated(-90,0,1,0);
		gl2.glPopMatrix();
	}

	/**
	 * @param gluQuadric
	 * @param glu
	 * @param gl2
	 */
	private void lightDrawing(GLUquadric gluQuadric, GLU glu, GL2 gl2)
	{
		gl2.glColor3f(lightHouseR,lightHouseG,lightHouseB);
		glu.gluCylinder(gluQuadric, 0.05,0.05, 0.05,50, 1);
		gl2.glColor3f(lightR,lightG,lightB);
		gl2.glRotated(180,0,1,0);
		glu.gluDisk(gluQuadric,0,0.05,50,1);
		gl2.glRotated(-180,0,1,0);
	}

	/**
	 * @param gluQuadric
	 * @param glu
	 * @param gl2
	 */
	private void chimneyDrawing(GLUquadric gluQuadric, GLU glu, GL2 gl2)
	{
		gl2.glColor3f(wallR,wallG,wallB);
		gl2.glPushMatrix();
		gl2.glTranslated(-0.55,0,0);
		gl2.glRotated(-90,1,0,0);
		glu.gluCylinder(gluQuadric,0.075, 0.075,0.2, 50,1);
		gl2.glRotated(90,1,0,0);

		gl2.glTranslated(0,0.2,0);
		gl2.glRotated(-90,1,0,0);
		glu.gluCylinder(gluQuadric,0.1,0.1,0.1,50,1);
		gl2.glRotated(180,1,0,0);
		glu.gluDisk(gluQuadric,0.075,0.1,50,1);
		gl2.glRotated(-180,1,0,0);
		gl2.glRotated(90,1,0,0);

		gl2.glTranslated(0,0,0);
		gl2.glRotated(-90,1,0,0);
		glu.gluDisk(gluQuadric,0,0.1,50,1);
		gl2.glRotated(90,1,0,0);
		gl2.glPopMatrix();
	}

	/**
	 * @param gl2 - OpenGL instance
	 * @param p1X
	 * @param p1Y
	 * @param p2X
	 * @param p2Y
	 * @param z
	 */
	private void drawQuad(GL2 gl2, double p1X, double p1Y, double p2X, double p2Y, double z)
	{
		gl2.glBegin(GL2.GL_QUADS);
		gl2.glVertex3d(p2X,p2Y,z);
		gl2.glVertex3d(p1X,p1Y,z);
		gl2.glVertex3d(p1X,p1Y,-z);
		gl2.glVertex3d(p2X,p2Y,-z);
		gl2.glEnd();
	}

	@Override

	public void init(GL2 gl) {

	}

	@Override
	public void control(int type, Object params) {

	}

	@Override
	public boolean isAnimated() {
		return false;
	}

	@Override
	public void setCamera(GL2 gl) {

	}

}
