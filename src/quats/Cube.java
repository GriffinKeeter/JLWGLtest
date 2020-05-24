package quats;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;
import org.ejml.simple.*;

public class Cube {
	private Quat[] lvertices = new Quat[8];//local vertices
	private Quat[] vertices = new Quat[8];
	private Quat[] basisVectors  = new Quat[3];
	//vertices is a list of quats
	
	public Cube(float radius) {
		
		//init local verts
		lvertices[0] = new Quat(0,radius, radius, radius);
		lvertices[1] = new Quat(0,-radius, radius, radius);
		lvertices[2] = new Quat(0,-radius, -radius, radius);
		lvertices[3] = new Quat(0,radius, -radius, radius);
		
		lvertices[4] = new Quat(0,radius, radius, -radius);
		lvertices[5] = new Quat(0,-radius, radius, -radius);
		lvertices[6] = new Quat(0,-radius, -radius, -radius);
		lvertices[7] = new Quat(0,radius, -radius, -radius);
		
		
		//init global vert
		vertices[0] = new Quat(0,radius, radius, radius);
		vertices[1] = new Quat(0,-radius, radius, radius);
		vertices[2] = new Quat(0,-radius, -radius, radius);
		vertices[3] = new Quat(0,radius, -radius, radius);
		
		vertices[4] = new Quat(0,radius, radius, -radius);
		vertices[5] = new Quat(0,-radius, radius, -radius);
		vertices[6] = new Quat(0,-radius, -radius, -radius);
		vertices[7] = new Quat(0,radius, -radius, -radius);
		
		//init local axes
		basisVectors[0] = new Quat(0,1,0,0);
		basisVectors[1] = new Quat(0,0,1,0);
		basisVectors[2] = new Quat(0,0,0,1);
	}
	
	public void draw() {
		//update vertices
		glBegin(GL_QUADS);
			//front face
			glColor4f(0,0,1,0);
			glVertex3f(vertices[0].get(1), vertices[0].get(2), vertices[0].get(3));
			glVertex3f(vertices[1].get(1), vertices[1].get(2), vertices[1].get(3));
			glColor4f(1,1,0,0);
			glVertex3f(vertices[2].get(1), vertices[2].get(2), vertices[2].get(3));
			glVertex3f(vertices[3].get(1), vertices[3].get(2), vertices[3].get(3));
			
			//top face
			glColor4f(0,1,0,0);
			glVertex3f(vertices[0].get(1), vertices[0].get(2), vertices[0].get(3));
			glVertex3f(vertices[4].get(1), vertices[4].get(2), vertices[4].get(3));
			glColor4f(1,0,1,0);
			glVertex3f(vertices[5].get(1), vertices[5].get(2), vertices[5].get(3));
			glVertex3f(vertices[1].get(1), vertices[1].get(2), vertices[1].get(3));
			
			
			//back face
			glColor4f(0,0,1,0);
			glVertex3f(vertices[5].get(1), vertices[5].get(2), vertices[5].get(3));
			glVertex3f(vertices[4].get(1), vertices[4].get(2), vertices[4].get(3));
			glColor4f(1,1,0,0);
			glVertex3f(vertices[7].get(1), vertices[7].get(2), vertices[7].get(3));
			glVertex3f(vertices[6].get(1), vertices[6].get(2), vertices[6].get(3));
			
			
			//bottom face
			glColor4f(0,1,0,0);
			glVertex3f(vertices[7].get(1), vertices[7].get(2), vertices[7].get(3));
			glVertex3f(vertices[3].get(1), vertices[3].get(2), vertices[3].get(3));
			glColor4f(1,0,1,0);
			glVertex3f(vertices[2].get(1), vertices[2].get(2), vertices[2].get(3));
			glVertex3f(vertices[6].get(1), vertices[6].get(2), vertices[6].get(3));
			
			//right face
			glColor4f(1,0,0,0);
			glVertex3f(vertices[4].get(1), vertices[4].get(2), vertices[4].get(3));
			glVertex3f(vertices[0].get(1), vertices[0].get(2), vertices[0].get(3));
			glColor4f(0,1,1,0);
			glVertex3f(vertices[3].get(1), vertices[3].get(2), vertices[3].get(3));
			glVertex3f(vertices[7].get(1), vertices[7].get(2), vertices[7].get(3));
			
			
			//left face
			glColor4f(1,0,0,0);
			glVertex3f(vertices[1].get(1), vertices[1].get(2), vertices[1].get(3));
			glVertex3f(vertices[5].get(1), vertices[5].get(2), vertices[5].get(3));
			glColor4f(0,1,1,0);
			glVertex3f(vertices[6].get(1), vertices[6].get(2), vertices[6].get(3));
			glVertex3f(vertices[2].get(1), vertices[2].get(2), vertices[2].get(3));
			
			
		glEnd();
	}
	
	
	/*
	//public update vertices(quat x wuat y quat z -- all local)
		//for each vertex:
		//	vertex_x = xl*xi + yl*yi + zl*zi
		 * 
		 * 
		
		 */
	public void updateVertices() {
		for(int v = 0; v < lvertices.length; v ++) {
			//surround all of this in a for loop iterating over all local vertices
			float x = lvertices[v].get(1);
			float y = lvertices[v].get(2);
			float z = lvertices[v].get(3);
			//create 3x1 local coord matrix
			float[][] local_coords = {{x},{y},{z}};
			
			SimpleMatrix local_coords_mat = new SimpleMatrix(local_coords);
			
			
			//matrix mult transform matrix local coords --> global
			//change of basis matrix - [i, j, k] -> 
			/*[ix, jx, kx]
			 *[iy, jy, ky]
			 *[iz, jz, kz]
			 */
			float[][] transform_matrix = new float[3][3];
			for(int i = 0; i < 3; i ++) {//i - local vector index
				for(int j = 0; j < 3; j ++) {//j - index within one local vector eg j_x, j_y, j_z
					transform_matrix[j][i] = basisVectors[i].get(j+1);//get the value from the j+1th index of the ith Quat
					//the i and j index are flipped in transf_matrix because the localaxes vectors need to be vertical
				}
			}
	
			
			SimpleMatrix transf_mat = new SimpleMatrix(transform_matrix);
			SimpleMatrix global_coords = transf_mat.mult(local_coords_mat);
	
			vertices[v] = new Quat(
					0, 
					(float)global_coords.get(0,0),
					(float)global_coords.get(1,0),
					(float)global_coords.get(2,0)
					);
		}
		
		
	}
	public Quat getBasisVector(int i) {
		return basisVectors[i];
	}
	public void setBasisVector(int i, Quat newVect) {
		basisVectors[i] = newVect;
	}
}
