import java.util.ArrayList;

import processing.core.PImage;

/***
 * Class to perform image processing for optical mark reading
 * 
 */
public class OpticalMarkReader {

	private short[][] firstPage;
	private ArrayList<AnswerSheet> sheets = new ArrayList<AnswerSheet>();

	/***
	 * Method to do optical mark reading on page image. Return an AnswerSheet
	 * object representing the page answers.
	 * 
	 * @param image
	 * @return
	 */
	public AnswerSheet processPageImage(PImage image) {
		image.filter(PImage.GRAY);

	}

	public int getPixelAt(int row, int col, PImage image) {
		image.loadPixels();

		int index = row * image.width + col;
		return image.pixels[index] & 255;
	}

	public String[] determineBubble(PImage image, int r, int c, int kernelWidth, int kernelHeight, int numBubbles) {
		int firstRectWidth = 195, secondRectWidth = 186, answerHeight = 20;
		int offset = 138;

		int[] firstIndexes = getDivided(firstRectWidth, numBubbles);
		int[] secondIndexes = getDivided(secondRectWidth, numBubbles);

		String[] answers = new String[2];

		for (int i = 0; i < answers.length; i++) {
			for (int row = 0; row < kernelHeight; row++) {
				for (int col = 0; col < kernelWidth; col++) {
					int[][] kernel = getKernel(image, row, col, kernelWidth, kernelHeight);
					if (isAnswerRow(kernel, 5)) {
						int[][] answerRow;
						String answer;
						if(i < 1) {
						 answerRow = getKernel(image, row, col + offset, firstRectWidth, answerHeight);
						  answer = getAnswer(answerRow, firstIndexes);
						} else {
							offset = 721;
							answerRow = getKernel(image, row, col + offset, secondRectWidth, answerHeight);
							answer = getAnswer(answerRow, secondIndexes);
						}
						
						answers[i] = answer;
					}
				}
			}
		}
		
		return answers;
	}

	private String getAnswer(int[][] firstAnswerRow, int[] firstIndexes) {
		int sum = 0;
		int startIndex = 0;

		int threshold = 100;

		for (int i = 0; i < firstIndexes.length; i++) {
			for (int r = startIndex; r < firstAnswerRow.length; r++) {
				for (int c = 0; c < firstIndexes[i]; c++) {
					sum += firstAnswerRow[r][c];
				}
			}
			if (sum <= threshold) {
				if (i == 0) {
					return "A";
				}
				if (i == 1) {
					return "B";
				}
				if (i == 2) {
					return "C";
				}
				if (i == 3) {
					return "D";
				}
				if (i == 4) {
					return "E";
				}

			}

			startIndex = firstIndexes[i];
		}
		return "0";
	}

	private int[] getDivided(int rectWidth, int numBubbles) {
		int[] indexes = new int[numBubbles];
		int divided = (int) (rectWidth / numBubbles);
		for (int i = 0; i < numBubbles; i++) {
			indexes[i] = divided;
			divided += rectWidth / numBubbles;
		}

		return indexes;
	}

	private int[][] getKernel(PImage image, int r, int c, int width, int height) {
		int[][] kernel = new int[height][width];
		for (int row = r; row < height; row++) {
			for (int col = c; col < height; col++) {
				kernel[row][col] = getPixelAt(row, col, image);
			}
		}

		return kernel;
	}

	public boolean isAnswerRow(int[][] kernel, int threshold) {
		int sum = 0;
		for (int r = 0; r < kernel.length; r++) {
			for (int c = 0; c < kernel[0].length; c++) {
				sum += kernel[r][c];
			}
		}
		if (sum <= 0 + threshold) {
			return true;
		}
		return false;
	}

	public int calculateSum(int r, int c, PImage image, int numBubbles, int col) {
		int sum = 0;
		for (int i = r; i < (image.height / numBubbles) * r; i++) {
			for (int j = col; j < (image.height / numBubbles) * c; j++) {
				sum += getPixelAt(i, j, image);
			}

		}

		return sum;
	}

}
