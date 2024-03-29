package quats;


import org.lwjgl.*;//you can import any packages, they don't have to be in the source folder
import org.lwjgl.opengl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.system.*;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;


public class MainWindow {
	//window handle
	private long window;
	
	public static void main(String[] args) {
		new MainWindow().run();
	}
	
	public void run() {
		System.out.println("version - "+Version.getVersion());
		float i = -1.0f;
		init(); //sets up window context, settings
		loop(i); 
		
		//free window callbacks, destroy window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		
		//terminate glfw and free error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	private void init() {
		//setup error callback, errors go to System.err
		GLFWErrorCallback.createPrint(System.err).set();
		
		//init glfw - returns 0 or 1
		if(!glfwInit()) {
			throw new IllegalStateException("can't initialize GLFW");
		}
		
		//config glfw
		glfwDefaultWindowHints();//optional
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);//window stays hidden after creation?
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);//window will be resizable
		
		//create window
		window = glfwCreateWindow(300,300, "Press the A key to choose random orientation", NULL, NULL);//the window is the context
		if(window == NULL) {
			throw new RuntimeException("the window could not be created");
		}
		
		//set up key callback - called whenever a key is pressed
		glfwSetKeyCallback(window, (window, key, scancode, action, mods)->{
			if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				glfwSetWindowShouldClose(window, true);//will be detected in rendering loop
			}
		});
		
		
		//get thread stack and push a new frame
		try (MemoryStack stack = stackPush()){
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			
			//pass window size to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);
			
			//get resolution of primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			//center window
			glfwSetWindowPos(
					window,
					(vidmode.width()-pWidth.get(0))/2,
					(vidmode.height()-pHeight.get(0))/2
			);
		}//stack frame popped automatically
		
		
		//make the context current
		glfwMakeContextCurrent(window);
		
		//enable v-sync whatever that is - how many draws to swap the buffer? 1 is just having 1 on the "backburner"
		glfwSwapInterval(1);
		
		//visableify window
		glfwShowWindow(window);
		
		//disables the cursor, puts it in center
		//glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		
		//hides cursor, but allows it to still be moved
		//glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
	}
	
	private void loop(float i) {
		//you can use OpenGL functions in this function
		//lwjgl detects current context in thread, creates GLcapabailities instance and makes the opengl bindings available for use
		GL.createCapabilities();
		
		glEnable(GL_TEXTURE_2D);
		
		glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

		Cube cube1 = new Cube(0.25f);
		Arrow a1 = new Arrow();
		//run loop until the user closes or presses esc
		
		int prev_key = GL_FALSE; //this variable to to prevent a key press from returning GLTRUE repeatedly while it is held down
		while(!glfwWindowShouldClose(window)) {//breaks the loop
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //clears framebuffer
			glEnable(GL_DEPTH_TEST);
			
			
			cube1.draw();
			cube1.slerp1.slerp_master();
			
			a1.draw(cube1.slerp1.get_target_vector());

		
			if(glfwGetKey(window, GLFW_KEY_A) == GL_TRUE) {
				if(prev_key == GL_FALSE) {
					cube1.slerp1.change_orientation();
				}
				prev_key = GL_TRUE;
			}else {
				prev_key = GL_FALSE;
			}
			glfwSwapBuffers(window);//swap color buffers
			glfwPollEvents(); //checks for window events
		}
	}
}
