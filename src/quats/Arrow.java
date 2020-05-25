package quats;

import static org.lwjgl.opengl.GL11.*;

public class Arrow {
	public void draw(Quat target) {
		glColor4f(0,0,0,0);
		glLineWidth(3.0f);
		glBegin(GL_LINES);
		    glVertex3f(0.0f, 0.0f, 0.0f);
		    glVertex3f(target.get(1), target.get(2), target.get(3));
		glEnd();
		
		glBegin(GL_LINES);
	    glVertex3f(target.get(1)-0.05f, target.get(2)-0.05f, target.get(3));
	    glVertex3f(target.get(1)+0.05f, target.get(2)+0.05f, target.get(3));
	    glEnd();
	    
	    glBegin(GL_LINES);
	    glVertex3f(target.get(1)+0.05f, target.get(2)-0.05f, target.get(3));
	    glVertex3f(target.get(1)-0.05f, target.get(2)+0.05f, target.get(3));
	    glEnd();
	}
}
