package quats;

public class SLERP {
	private Quat prev_pointVector  = new Quat();//what the cube "points to" - also the j/y axis in cube
	private Quat next_pointVector  = new Quat();
	private Cube cube;
	private float t = 0;//used in transition vect to transition from prev to next basis
	
	public Quat get_target_vector() {
		return next_pointVector;
	}
	
	public SLERP(Cube cube1){
		cube = cube1;
		change_orientation();
	}
	
	public void slerp_master() {
		if(t<1.0f) {
			t += 0.05f;
		}
		Quat pointer = transition_vect(prev_pointVector, next_pointVector, t);
		Quat[] new_basis = generate_Basis(pointer);
		
		cube.set_basis(new_basis);
		
		cube.updateVertices();
	}
	
	public void change_orientation() {
		//generate new point vector
		next_pointVector = rand3dQuat();
		prev_pointVector = cube.getBasisVector(0);
		t = 0.0f;
	}
	
	
	private Quat transition_vect(Quat v0, Quat v1, float t) { //this will be run for each basis vector?
		//takes an initial vector, a target vector, and t (0-1)
		//both vectors should be unit? with w = 0
		double angle = Math.acos(v0.dot(v1));//this will stay the same from t [0-1]
		
		Quat temp_Unit = v1.orthoNorm(v0);//used as "y component" to make finding the new angle easier *** if v0 and v1 are the same ie the goal orientation is reached, the returned vector will be NaN
		//calculate the angle between the vectors
		//find orthogonal vector v2 to v0 using v1 to use as unit
		double new_angle = t*angle;
		//new angle a = t * prev_angle
		//new vector = cos(a)v0 + sin(a)v2
		v0 = v0.scalar((float)Math.cos(new_angle));
		temp_Unit = temp_Unit.scalar((float)Math.sin(new_angle));
		//create new Quat with that, and return
		Quat result = new Quat(
				v0.get(0)+temp_Unit.get(0),
				v0.get(1)+temp_Unit.get(1),
				v0.get(2)+temp_Unit.get(2),
				v0.get(3)+temp_Unit.get(3)
				);
		return result;
	}
	
	
	private Quat rand3dQuat() {//generates a random 3d vector of unit length, x y and z of quat, w = 0
		Quat result = new Quat(
				0,
				(float)(Math.random()-0.5),
				(float)(Math.random()-0.5),
				(float)(Math.random()-0.5)
				);
		result = result.normalize();
		
		return result;
	}
	
	
	
	private boolean check_basis(Quat[] b1, Quat[] b2) {//checks if any vectors in the basis are close to each other
		float dif = 0.05f;
		boolean same = false;
		for(int i = 0; i < 3; i ++) {
			for(int j = 1; j < 4; j ++) {
				if((b1[i].get(j) > b2[i].get(j) - dif) && (b1[i].get(j) < b2[i].get(j) + dif)) {
					same = true;
				}
			}
		}
		return same;
	}
	
	private Quat[] generate_Basis(Quat p1) {//generates the next basis from one vector
		Quat[] result = new Quat[3];
		result[0] = p1;
		result[1] = cube.getBasisVector(1).orthoNorm(result[0]);
		result[1] = result[1].orthoNorm(p1);
		result[2] = result[0].cross(result[1]);
		return result;
	}
}
