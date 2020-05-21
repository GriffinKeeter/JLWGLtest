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
	public float norm(Quat q1) {
		float mag = (float)Math.sqrt(
				(q1.get(0)*q1.get(0))
				+(q1.get(1)*q1.get(1))
				+(q1.get(2)*q1.get(2))
				+(q1.get(3)*q1.get(3))
				);
		return mag;
	}
	
	public void print() {
		System.out.println(w);
		System.out.println(x);
		System.out.println(y);
		System.out.println(z);
	}
}
