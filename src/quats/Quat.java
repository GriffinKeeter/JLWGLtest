package quats;

public class Quat {
	private float w;
	private float x;
	private float y;
	private float z;
	
	public Quat(float pw, float px, float py, float pz) {
		w = pw;
		x = px;
		y = py;
		z = pz;
	}
	
	public Quat() {
		w = 0.0f;
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}
	
	public float get(int i) {
		float out = 0;
		switch(i) {
		case 0:
			out = w;
			break;
		case 1:
			out = x;
			break;
		case 2:
			out = y;
			break;
		case 3:
			out = z;
			break;
		}
		return out;
	}
	
	public Quat q_Mult(Quat other) {
		float w2 = other.get(0);
		float x2 = other.get(1);
		float y2 = other.get(2);
		float z2 = other.get(3);
		
		float pw = (w*w2) - (x*x2) - (y*y2) - (z*z2);
		float px = (w*x2) + (x*w2) + (y*z2) - (z*y2);
		float py = (w*y2) - (x*z2) + (y*w2) + (z*x2);
		float pz = (w*z2) + (x*y2) - (y*x2) + (z*w2);
		
		Quat qProduct = new Quat(pw, px, py, pz);
		return qProduct;
	}
	
	public Quat generate_Inverse() {
		Quat q_Inv = new Quat(w, -x, -y, -z);//works only for unit quats
		return q_Inv;
	}
	
	public Quat cross(Quat q1) {//returns a perpendicular quat to the current one and a third quat
		//cross product : (b*c2)-(c*b2), (c*a2)-(a*c2), (ab2)-(ba2)
		//cross product : (y*z2)-(z*y2), (z*x2)-(x*z2), (x*y2)-(y*x2)
		float x2 = q1.get(1);
		float y2 = q1.get(2);
		float z2 = q1.get(3);
		
		Quat result = new Quat(
				0,
				(y*z2)-(z*y2),
				(z*x2)-(x*z2),
				(x*y2)-(y*x2)
				);
		return result;
	}
	public Quat normalize() {
		float mag = mag();
		Quat result = new Quat(w/mag, x/mag, y/mag, z/mag);
		return result;
		
	}
	public float mag() {
		float mag = (float)Math.sqrt((double)((w*w)+(x*x)+(y*y)+(z*z)));
		return mag;
		
	}
	public float dot(Quat q1) {
		float sum = 0; 
		for(int i = 0; i < 4; i ++) {
			sum += (q1.get(i)*this.get(i));
		}
		return sum;
	}
	
	public Quat scalar(float s) {
		Quat result = new Quat(w*s, x*s, y*s, z*s);
		return result;
	}
	
	public Quat orthoNorm(Quat q1) {
		float mag = dot(q1);//magnitude of result = cos(angle) = a dot b - for unit vector/quat only - this might result in errors if the magnitude drifts away from 1
		q1 = q1.scalar(mag);//q1 is unit length, so after this it is mag length; this is the projection
		
		Quat result = new Quat(w-q1.get(0), x-q1.get(1), y-q1.get(2), z-q1.get(3));//subtracts the component of this quat that is parallel with q1
		result = result.normalize();
		
		return result;
	}
	public void print() {
		System.out.println(w);
		System.out.println(x);
		System.out.println(y);
		System.out.println(z);
	}
}
