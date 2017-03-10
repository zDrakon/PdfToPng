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
		image.loadPixels();

		AnswerSheet sheet = new AnswerSheet(38, 4);

		for (int r = 0; r < image.height; r++) {
			for (int c = 0; c < image.width; c++) {
				sheet.addAnswer(determineBubble(image, r, c, 8, 8, 5), r, c);

			}
		}

		return sheet;

	}

	public int getPixelAt(int row, int col, PImage image) {
		image.loadPixels();

		int index = row * image.width + col;
		return image.pixels[index] & 255;
	}

	public String determineBubble(PImage image, int r, int c, int kernelWidth, int kernelHeight, int numBubbles) {
		int rectWidth = 195;

		String answer = "";

		int[] indexes = getDivided(rectWidth, numBubbles);

		for (int row = r; row < r + kernelHeight; row++) {
			for (int col = c; col < c + kernelWidth; col++) {
				int[][] kernel = getKernel(image, row, col, kernelWidth, kernelHeight);
				if (isAnswerRow(kernel, 5)) {
					answer = getAnswer(kernel, indexes);
				}
			}
		}

		return answer;
	}

	private String getAnswer(int[][] answerRow, int[] indexes) {
		int sum = 0;
		int startIndex = 0;

		for (int i = 0; i < indexes.length; i++) {
			for (int r = startIndex; r < answerRow.length; r++) {
				for (int c = 0; c < indexes[i]; c++) {
					if (answerRow[r][c] == 0) {
						sum += answerRow[r][c];
					}
				}
			}
			if (sum >= 0) {
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

			startIndex = indexes[i];
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
