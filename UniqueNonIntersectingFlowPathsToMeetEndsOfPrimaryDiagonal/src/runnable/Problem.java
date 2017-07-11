package runnable;

public class Problem extends Array implements Runnable {
	
	private static final byte ZERO = 0;
	
	private static final byte ONE = 1;
	
	public static int count = 0;
	
	private byte positionRow;
	
	private byte positionColumn;
	
	private String path;
	
	public Problem(byte rows, byte columns) {
		this(rows, columns, ZERO, ZERO);
	}
	
	private Problem(byte rows, byte columns, byte positionRow, byte positionColumn) {
		super(rows, columns);
		this.positionRow = positionRow;
		this.positionColumn = positionColumn;
		getArray()[positionRow][positionColumn] = true;
	}
	
	private Problem(Problem problem, String appendPath, byte positionRow, byte positionColumn) {
		super(problem);
		this.path = problem.getPath().concat(appendPath);
		this.positionRow = positionRow;
		this.positionColumn = positionColumn;
		getArray()[positionRow][positionColumn] = true;
	}
	
	public String getPath() {
		return path;
	}
	
	public void start() {
		path = "START";
		run();
	}
	
	private synchronized void info(String path) {
		++count;
		System.out.println("Path " + count + " : " + path);
	}
	
	private void startThreadsInValidDirections() {
		if (canGoUp()) {
			goUp();
		}
		if (canGoDown()) {
			goDown();
		}
		if (canGoLeft()) {
			goLeft();
		}
		if (canGoRight()) {
			goRight();
		}
	}

	public void run() {
		if (finalLocation()) {
			path = path.concat("-END");
			info(path);
		} else {
			startThreadsInValidDirections();
		}
	}
	
	private boolean finalLocation() {
		if(getRows()-1 == positionRow && getColumns()-1 == positionColumn)
			return true;
		else
			return false;
	}
	
	private boolean canGoUp() {
		if(0 == positionRow)
			return false;
		if(getArray()[positionRow-1][positionColumn])
			return false;
		return true;
	}
	
	private void goUp() {
		Problem child = new Problem(this, "-Up", (byte)(positionRow-ONE), positionColumn);
		Thread thread = new Thread(child);
		thread.start();
	}
	
	private boolean canGoDown() {
		if(getRows()-1 == positionRow)
			return false;
		if(getArray()[positionRow+1][positionColumn])
			return false;
		return true;
	}
	
	private void goDown() {
		Problem child = new Problem(this, "-Down", (byte)(positionRow+ONE), positionColumn);
		Thread thread = new Thread(child);
		thread.start();
	}
	
	private boolean canGoLeft() {
		if(0 == positionColumn)
			return false;
		if(getArray()[positionRow][positionColumn-1])
			return false;
		return true;
	}
	
	private void goLeft() {
		Problem child = new Problem(this, "-Left", positionRow, (byte)(positionColumn-ONE));
		Thread thread = new Thread(child);
		thread.start();
	}
	
	private boolean canGoRight() {
		if(getColumns()-1 == positionColumn)
			return false;
		if(getArray()[positionRow][positionColumn+1])
			return false;
		return true;
	}
	
	private void goRight() {
		Problem child = new Problem(this, "-Right", positionRow, (byte)(positionColumn+ONE));
		Thread thread = new Thread(child);
		thread.start();
	}

}
