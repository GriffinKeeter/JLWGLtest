package quats;

import java.util.*;

public class Quat_Processes {
	
	public static void main(String[] args) {
		new MainWindow().run();
	}
	
	public static void run(Cube cube) {//called from loop in mainwindow

		//init rotation quat;
		float angle = 0.1f;
		float[] axis = {(float)Math.cos(0.3),(float)Math.sin(0.3),0};//{(float)Math.cos(0),(float)Math.sin(0),0};//should be vertical
		Quat versor = Quat_Processes.generate_Versor(angle, axis);
		
		//generate inverse quat
		Quat versor_Inv = versor.generate_Inverse();
		
		//for vector p (local axis) 
		for(int i = 0; i < 3; i ++) {
			Quat p_quat = cube.getBasisVector(i);
			Quat new_Basis = (versor.q_Mult(p_quat)).q_Mult(versor_Inv);//new_p vector <- r_quat * p * inv_r_quat
			cube.setBasisVector(i, new_Basis);
			
		}
		cube.updateVertices();
		
	}
	
	
	public static Quat generate_Versor(float angle, float[] axis) {
		Quat versor = new Quat(
				(float)(Math.cos(angle/2)), 
				(float)(Math.sin(angle/2)*axis[0]),
				(float)(Math.sin(angle/2)*axis[1]),
				(float)(Math.sin(angle/2)*axis[2])
				);
		return versor;
	}
	
	public static Quat SLERP(Quat v0, Quat v1, float t) {
		//takes an initial vector, a target vector, and t (0-1)
		//both vectors should be unit? with w = 0
		
		//calculate the angle between the vectors
		//find orthogonal vector v2 to v0 using v1 to use as unit
		//new angle a = t * prev_angle
		//new vector = cos(a)v0 + sin(a)v2
		//create new Quat with that, and return
		return v0;
	}
	
	public static float[] rand3Points() {
		float[] rand3 = new float[3];
		
		rand3[0] = (float)Math.random();
		rand3[1] = (float)Math.random();
		rand3[2] = (float)Math.random();
		
		float mag = (float)Math.sqrt(rand3[0]*rand3[0])+(rand3[1]*rand3[1])+(rand3[2]*rand3[2]);
		
		rand3[0] /= mag;
		rand3[1] /= mag;
		rand3[2] /= mag;
		
		return rand3;
	}
}
