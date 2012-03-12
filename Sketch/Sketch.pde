package T23Holiday;

import java.util.ArrayList;
import java.util.Random;

import org.processing.wiki.triangulate.*;

import fullscreen.*; 

import processing.core.*;

// Big ups to http://wiki.processing.org/w/Triangulation

@SuppressWarnings("serial")
public class HolidaySketch extends PApplet {
	
	public enum Direction {
	    UP, RIGHT, DOWN, LEFT
	};

	Boolean is_setup = false;

	ArrayList<Triangle> triangles = new ArrayList<Triangle>();
	ArrayList<PVector> points = new ArrayList<PVector>();
    private static int pointCount = 5000;
    private Random random = new Random();
    private int fontSize = 66;
    
    private float rotationX = 20;
    private float rotationY = 20;
    
    private Direction rotationDirection;
    private boolean isAutoRotating = true;
    
    SoftFullScreen fs;
    
    public void setup() {
    	if (!is_setup) {
			is_setup = true;
			size(420, 600, OPENGL);
			hint(ENABLE_OPENGL_4X_SMOOTH);
			 // Create the fullscreen object
			  fs = new SoftFullScreen(this); 
			  
			  // enter fullscreen mode
			  fs.enter(); 
		}
    	
    	strokeWeight(1.4f);
    	smooth();

		textFont(createFont("franchise-bold-webfont.ttf", fontSize)); 
		textAlign(CENTER);
		
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
	    background(0xffcb582c);
	    stroke(0x33ffffff);
	    noFill();
	    		    
		// Draw the triangle edges
	    for (Triangle t : triangles) {
	    	line(t.p1.x, t.p1.y, t.p1.z, t.p2.x, t.p2.y, t.p2.z);
	    	line(t.p2.x, t.p2.y, t.p2.z, t.p3.x, t.p3.y, t.p3.z);
	    	line(t.p3.x, t.p3.y, t.p3.z, t.p1.x, t.p1.y, t.p1.z);
	    }
	    
	    float midX = (float)width / 2.0f;
	    float midY = (float)height / 2.0f;

	    // Draw the text
		float textWidth = (float)fontSize * 3.4f;
		float textHeight = (float)fontSize * 4.8f;
		
		float textX = midX - textWidth / 2.0f;
		float textY = midY - textHeight / 2.0f;
		
		for (int i = 35; i < 50; i++) {
			text("THE BEST WAY TO PREDICT THE FUTURE IS TO INVENT IT", textX, textY, textWidth, textHeight, (float)i);
		}
		
		if (isAutoRotating) {
			switch (rotationDirection) {
				case RIGHT:
					rotationX++;
					if (rotationX == width - 20) {
						rotationDirection = Direction.DOWN;
					}
					break;
				case DOWN:
					rotationY++;
					if (rotationY == height - 20) {
						rotationDirection = Direction.LEFT;
					}
					break;
				case LEFT:
					rotationX--;
					if (rotationX == 20) {
						rotationDirection = Direction.UP;
					}
					break;
				case UP:
					rotationY--;
					if (rotationY == 20) {
						rotationDirection = Direction.LEFT;
					}
					break;
			}
		} else {
			rotationX = mouseX;
			rotationY = mouseY;
		}
		
	    float xPer = (rotationX) / (float)width;
	    float yPer = (rotationY) / (float)height;
	    
	    float eyeX = midX + (xPer - 0.5f) * midX * 0.57f;
	    float eyeY = midY + (yPer - 0.5f) * midY * 0.57f;
	    	    
	    camera(eyeX, eyeY, (midY / tan(PI*60.0f / 360.0f)) / 1.2f, midX, midY, 0.0f, 0.0f, 1.0f, 0.0f);
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

