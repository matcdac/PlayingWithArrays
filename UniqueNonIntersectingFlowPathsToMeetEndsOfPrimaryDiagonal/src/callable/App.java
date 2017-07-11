package callable;

public class App {
	
	private static final byte ROWS = 4;
	private static final byte COLUMNS = 4;
	
	public static void main(String[] args) {
		Problem problem = new Problem(ROWS, COLUMNS);
		problem.start();
		problem.getPaths().remove(null);
		System.out.println(problem.getPaths().size());
		problem.getExecutor().shutdown();
	}
	
}
