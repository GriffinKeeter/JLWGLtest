package quats;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

public class Cube {
	private Quat[] vertices = new Quat[8];
	//vertices is a list of quats
	
	public Cube(float radius) {
		//front face
		vertices[0] = new Quat(0,radius, radius, radius);
		vertices[1] = new Quat(0,-radius, radius, radius);
		vertices[2] = new Quat(0,-radius, -radius, radius);
		vertices[3] = new Quat(0,radius, -radius, radius);
		
		vertices[4] = new Quat(0,radius, radius, -radius);
		vertices[5] = new Quat(0,-radius, radius, -radius);
		vertices[6] = new Quat(0,-radius, -radius, -radius);
		vertices[7] = new Quat(0,radius, -radius, -radius);
	}
	
	public void draw() {
		glBegin(GL_QUADS);
			//front face
			glColor4f(0,0,1,0);
			glVertex3f(vertices[0].get(1), vertices[0].get(2), vertices[0].get(3));
			glVertex3f(vertices[1].get(1), vertices[1].get(2), vertices[1].get(3));

			glVertex3f(vertices[2].get(1), vertices[2].get(2), vertices[2].get(3));
			glVertex3f(vertices[3].get(1), vertices[3].get(2), vertices[3].get(3));
			
			//top face
			glColor4f(0,1,0,0);
			glVertex3f(vertices[0].get(1), vertices[0].get(2), vertices[0].get(3));
			glVertex3f(vertices[4].get(1), vertices[4].get(2), vertices[4].get(3));
			
			glVertex3f(vertices[5].get(1), vertices[5].get(2), vertices[5].get(3));
			glVertex3f(vertices[1].get(1), vertices[1].get(2), vertices[1].get(3));
			
			
			//back face
			glColor4f(0,0,1,0);
			glVertex3f(vertices[5].get(1), vertices[5].get(2), vertices[5].get(3));
			glVertex3f(vertices[4].get(1), vertices[4].get(2), vertices[4].get(3));
			
			glVertex3f(vertices[7].get(1), vertices[7].get(2), vertices[7].get(3));
			glVertex3f(vertices[6].get(1), vertices[6].get(2), vertices[6].get(3));
			
			
			//bottom face
			glColor4f(0,1,0,0);
			glVertex3f(vertices[7].get(1), vertices[7].get(2), vertices[7].get(3));
			glVertex3f(vertices[3].get(1), vertices[3].get(2), vertices[3].get(3));
			
			glVertex3f(vertices[2].get(1), vertices[2].get(2), vertices[2].get(3));
			glVertex3f(vertices[6].get(1), vertices[6].get(2), vertices[6].get(3));
			
			//right face
			glColor4f(1,0,0,0);
			glVertex3f(vertices[4].get(1), vertices[4].get(2), vertices[4].get(3));
			glVertex3f(vertices[0].get(1), vertices[0].get(2), vertices[0].get(3));
			
			glVertex3f(vertices[3].get(1), vertices[3].get(2), vertices[3].get(3));
			glVertex3f(vertices[7].get(1), vertices[7].get(2), vertices[7].get(3));
			
			
			//left face
			glColor4f(1,0,0,0);
			glVertex3f(vertices[1].get(1), vertices[1].get(2), vertices[1].get(3));
			glVertex3f(vertices[5].get(1), vertices[5].get(2), vertices[5].get(3));
			
			glVertex3f(vertices[6].get(1), vertices[6].get(2), vertices[6].get(3));
			glVertex3f(vertices[2].get(1), vertices[2].get(2), vertices[2].get(3));
			
			
		glEnd();
	}
	
	public Quat[] getVertices() {
		return vertices;
	}
	public void setVertices(Quat[] new_verts) {
		vertices = new_verts; //haha garbage
	}
}
