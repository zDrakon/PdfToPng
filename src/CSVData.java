import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * @author adivya822
 *
 */
public class CSVData {
	private double[][] data;
	private String[] columnNames;
	private int numRows;
	private String filePathToCSV;

	public CSVData(String filepath, String[] columnNames, int startRow) {
		this.filePathToCSV = filepath;

		String dataString = readFileAsString(filepath);
		String[] lines = dataString.split("\n");

		// number of data points
		int n = lines.length - startRow;
		this.numRows = n;
		int numColumns = columnNames.length;

		// create storage for column names
		this.columnNames = columnNames;

		// create storage for data
		this.data = new double[n][numColumns];
		for (int i = 0; i < lines.length - startRow; i++) {
			String line = lines[startRow + i];
			String[] coords = line.split(",");
			for (int j = 0; j < numColumns; j++) {
				if (coords[j].endsWith("#"))
					coords[j] = coords[j].substring(0, coords[j].length() - 1);
				double val = Double.parseDouble(coords[j]);
				data[i][j] = val;
			}
		}
	}

	private String readFileAsString(String filepath) {
		StringBuilder output = new StringBuilder();
		try (Scanner scanner = new Scanner(new File(filepath))) {
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				output.append(line + System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output.toString();
	}

	public static CSVData readCSVFile(String filename, int numLinesToIgnore, String[] columnNames) {
		return null;
	}

	/***
	 * returns new CSVData object for file ignoring lines at the top. It uses
	 * the 1st row as the column names.All other data stored as doubles.
	 * 
	 * @param filename
	 *            file to read
	 * @param numLinesToIgnore
	 *            number of lines at the top to ignore
	 * @return a CSVData object for that file
	 */
	public static CSVData readCSVFile(String filename, int numLinesToIgnore) {
		return null;
	}

	/***
	 * returns the values in that column
	 * 
	 * @param arr
	 *            the array with all columns
	 * @param column
	 *            the column from which you want to get the value
	 * @return
	 */
	public static double[] getColumn(double[][] arr, int column) {
		double[] array = new double[arr[0].length];
		for (int i = 0; i < arr[0].length; i++) {
			array[i] = arr[i][column];
		}
		return array;
	}
/***
 * 
 * @param arr
 * @param row
 * @return
 */
	public static double[] getRow(double[][] arr, int row) {
		double[] array = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			array[i] = arr[row][i];
		}
		return array;
	}
/***
 * 
 * @param arr
 * @param startRow
 * @param endRow
 * @return
 */
	public static double[][] getRows(double[][] arr, int startRow, int endRow) {
		double[][] array = new double[arr.length][arr[0].length];
		for (int r = startRow; r < endRow; r++) {
			for (int c = 0; c < arr[0].length; c++) {
				array[r][c] = arr[r][c];
			}
		}
		return null;
	}

	public static double[][] getCols(double[][] arr, int startCol, int endCol) {
		return null;
	}

	public static void setColumn(int columnIndex, double[] vals, double[][] data) {
for(int i = 0; i<data.length;i++){
	for(int j = 0; j<vals.length;j++){
		data[i][columnIndex] = vals
	}
}
	}

	public static void setRow(double[][] arr, int rowIndex, double[] vals) {

	}

	public void setValue(int rowIndex, int colIndex, double[] values) {

	}

	public String getTitles() {
		return null;
	}

	/***
	 * Saves the current state of object back into a CSV file
	 * 
	 * @param filename
	 */
	public void saveToFile(String filename) {

	}
}
