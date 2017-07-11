package callable;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Problem extends Array implements Callable<Object> {
	
	private static final byte ZERO = 0;
	
	private static final byte ONE = 1;
	
	public static int count;
	
	private static Set<Object> paths;
	
	private static ExecutorService executor;
	
	private byte positionRow;
	
	private byte positionColumn;
	
	private String path;
	
	static {
		count = 0;
		paths = new HashSet<Object>();
		executor = Executors.newFixedThreadPool(100);
	}
	
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
	
	public ExecutorService getExecutor() {
		return executor;
	}
	
	public Set<Object> getPaths() {
		return paths;
	}
	
	public Object start() {
		path = "START";
		return call();
	}

	private synchronized void info(String path) {
		++count;
		//System.out.println("Path " + count + " : " + path);
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
	
	public Object call() {
		if (finalLocation()) {
			path = path.concat("-END");
			info(path);
			return path;
		} else {
			startThreadsInValidDirections();
		}
		return null;
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
		execute(child);
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
		execute(child);
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
		execute(child);
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
		execute(child);
	}
	
	private void execute(Problem child) {
		Future<Object> submit = executor.submit(child);
		try {
			paths.add(submit.get());
		} catch(Exception ex) {
			
		}
	}

}
