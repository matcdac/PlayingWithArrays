package callable;

public class Array {
	
	private byte rows;
	
	private byte columns;
	
	private boolean[][] array;
	
	public byte getRows() {
		return rows;
	}

	public byte getColumns() {
		return columns;
	}

	public boolean[][] getArray() {
		return array;
	}

	public Array(byte rows, byte columns) {
		this.rows = rows;
		this.columns = columns;
		array = new boolean[rows][columns];
	}
	
	public Array(Array array) {
		this.rows = array.getRows();
		this.columns = array.getColumns();
		this.array = new boolean[rows][columns];
		for(byte row=0; row<rows; row++)
			for(byte column=0; column<columns; column++)
				this.array[row][column] = array.getArray()[row][column];
	}
	
}
