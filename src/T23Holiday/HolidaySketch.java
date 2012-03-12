package T23Holiday;

import java.util.ArrayList;
import java.util.Random;

import org.processing.wiki.triangulate.*;

import processing.core.*;

// Big ups to http://wiki.processing.org/w/Triangulation


@SuppressWarnings("serial")
public class HolidaySketch extends PApplet {
	
	public static void main(String args[]) {
	    PApplet.main(new String[] { "--present", "T23Holiday.HolidaySketch" });
	}
	
	public enum Direction {
	    UP, RIGHT, DOWN, LEFT
	};

	Boolean is_setup = false;

	ArrayList<Triangle> triangles = new ArrayList<Triangle>();
	ArrayList<PVector> points = new ArrayList<PVector>();
    private static int pointCount = 3500;
    private Random random = new Random();
    private int fontSize = 95;
    
    private float rotationX = 20;
    private float rotationY = 20;
    
    private Direction rotationDirection;
    private boolean isAutoRotating = true;
    
    PFont font;
    
    float time = 0.0f;
    
    public void setup() {
    	if (!is_setup) {
			is_setup = true;
			size(1400, 1050, OPENGL);
			hint(ENABLE_OPENGL_4X_SMOOTH);
		}
	
    	strokeWeight(2.0f);
    	smooth();

    	font = createFont("franchise-bold-webfont.ttf", fontSize);
		
        points = generateRandomPoints();
        triangles = Triangulate.triangulate(points);
        
        rotationDirection = Direction.RIGHT;
	}
	
    public void keyPressed() {
    	if (key == 'm') {
    		isAutoRotating = !isAutoRotating;
    		rotationDirection = Direction.RIGHT;
    		rotationX = rotationY = 20.0f;
    	}
    }
    
	public void draw() {
		textFont(font);
		textAlign(CENTER);
		
	    background(0xffcb582c);
	    stroke(0x44ffffff);
	    noFill();
	    		    
	    time += 0.025f;

	    float zMult = 8.5f;
	    
		// Draw the triangle edges
	    for (Triangle t : triangles) {
	    	PVector point1 = t.p1;
	    	PVector point2 = t.p2;
	    	PVector point3 = t.p3;	
	    	
	    	point1.z = sin(point1.y + point1.x + time);
	    	point2.z = sin(point2.y + point2.x + time);
	    	point3.z = sin(point3.y + point3.x + time);
	    	
	    	point1.z *= zMult;
	    	point2.z *= zMult;
	    	point3.z *= zMult;
	    	
	    	drawLine(point1, point2);
	    	drawLine(point2, point3);
	    	drawLine(point3, point1);
	    }
	    
	    float midX = (float)width / 2.0f;
	    float midY = (float)height / 2.0f;

	    // Draw the text
		float textWidth = (float)fontSize * 3.4f;
		float textHeight = (float)fontSize * 4.8f;
		
		float textX = midX - textWidth / 2.0f;
		float textY = midY - textHeight / 2.0f;
		
		for (int i = 45; i < 50; i++) {
			text("THE BEST WAY TO PREDICT THE FUTURE IS TO INVENT IT", textX, textY, textWidth, textHeight, (float)i);
		}
		
		if (isAutoRotating) {
			switch (rotationDirection) {
				case RIGHT:
					rotationX += 3;
					if (rotationX >= width - 20) {
						rotationDirection = Direction.DOWN;
					}
					break;
				case DOWN:
					rotationY += 3;
					if (rotationY >= height - 20) {
						rotationDirection = Direction.LEFT;
					}
					break;
				case LEFT:
					rotationX -= 3;
					if (rotationX <= 20) {
						rotationDirection = Direction.UP;
					}
					break;
				case UP:
					rotationY -= 3;
					if (rotationY <= 20) {
						rotationDirection = Direction.RIGHT;
					}
					break;
			}
		} else {
			rotationX = mouseX;
			rotationY = mouseY;
		}
		
	    float xPer = (rotationX) / (float)width;
	    float yPer = (rotationY) / (float)height;
	    
	    float eyeX = midX + (xPer - 0.5f) * midX * 0.28f;
	    float eyeY = midY + (yPer - 0.5f) * midY * 0.28f;
	    	    
	    camera(eyeX, eyeY, (midY / tan(PI*60.0f / 360.0f)) / 1.2f, midX, midY, 0.0f, 0.0f, 1.0f, 0.0f);
	}
	
	private void drawLine(PVector p1, PVector p2) {
		line(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z);
	}
	
	private ArrayList<PVector> generateRandomPoints() {
		ArrayList<PVector> points = new ArrayList<PVector>();
		
		for (int i = 0; i < pointCount; i++) {
			float x = random.nextFloat() * (width + 20);
			float y = random.nextFloat() * (height + 20);
			float z = random.nextFloat() * 50;
			points.add(new PVector(x - 10, y - 10, z - 25));
		}
		
		return points;
	}
}
