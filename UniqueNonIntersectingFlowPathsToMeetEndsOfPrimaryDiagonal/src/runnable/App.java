package runnable;

public class App {
	
	private static final byte DIMENSIONS = 4;
	
	public static void main(String[] args) {
		Problem problem = new Problem(DIMENSIONS, DIMENSIONS);
		problem.start();
	}
	
}
