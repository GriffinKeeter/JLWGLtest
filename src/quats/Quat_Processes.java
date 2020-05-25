package quats;

import java.util.*;

public class Quat_Processes {
	
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
	
	//public change 
	
	public static Quat generate_Versor(float angle, float[] axis) {
		Quat versor = new Quat(
				(float)(Math.cos(angle/2)), 
				(float)(Math.sin(angle/2)*axis[0]),
				(float)(Math.sin(angle/2)*axis[1]),
				(float)(Math.sin(angle/2)*axis[2])
				);
		return versor;
	}
}
