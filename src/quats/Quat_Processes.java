package quats;

public class Quat_Processes {
	
	public static void main(String[] args) {
		System.out.println("yes sir");
		new MainWindow().run();
	}
	
	public static void run(Cube cube) {//called from loop in mainwindow
		
		//init p for each vector you want to rotate
		
		
		//init rotation quat;
		float angle = 0.05f;
		float[] axis = {(float)Math.cos(0.3),(float)Math.sin(0.3),0};//{(float)Math.cos(0),(float)Math.sin(0),0};//should be vertical
		Quat versor = Quat_Processes.generate_Versor(angle, axis);
		
		//generate inverse quat
		Quat versor_Inv = versor.generate_Inverse();
		
		//for each p
			//new_p vector <- r_quat * p * inv_r_quat
		Quat[] vertices = cube.getVertices();
		for(int i = 0; i < vertices.length; i ++) {
			vertices[i] = (versor.q_Mult(vertices[i])).q_Mult(versor_Inv);
		}
		
		//sets the cube's vertices to the rotated vertices
		cube.setVertices(vertices);
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
}
