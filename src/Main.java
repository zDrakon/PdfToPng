import java.util.ArrayList;

import processing.core.PImage;

public class Main {
	public static final String PDF_PATH = "/omrtest.pdf";
	public static OpticalMarkReader markReader = new OpticalMarkReader();
	
	public static void main(String[] args) {
		System.out.println("Welcome!  I will now auto-score your pdf!");
		System.out.println("Loading file..." + PDF_PATH);
		ArrayList<PImage> images = PDFHelper.getPImagesFromPdf(PDF_PATH);

		System.out.println("Scoring all pages...");
		scoreAllPages(images);

		System.out.println("Complete!");
		
		// Optional:  add a saveResults() method to save answers to a csv file
	}

	/***
	 * Score all pages in list, using index 0 as the key.
	 * 
	 * NOTE:  YOU MAY CHANGE THE RETURN TYPE SO YOU RETURN SOMETHING IF YOU'D LIKE
	 * 
	 * @param images List of images corresponding to each page of original pdf
	 */
	private static void scoreAllPages(ArrayList<PImage> images) {
		ArrayList<AnswerSheet> scoredSheets = new ArrayList<AnswerSheet>();

		// Score the first page as the key
		AnswerSheet key = markReader.processPageImage(images.get(0));

		for (int i = 1; i < images.size(); i++) {
			PImage image = images.get(i);

			AnswerSheet answers = markReader.processPageImage(image);

			answers.exportCSV();
			itemAnalysis(scoredSheets);
		}
	}

	private static void itemAnalysis(ArrayList<AnswerSheet> scoredSheets) {
		AnswerSheet key = scoredSheets.get(0);
		int[] incorrect = new int[26];
		for(int i = 1; i < scoredSheets.size(); i++) {
			AnswerSheet a = scoredSheets.get(i);
			incorrect = a.compare(key);
			
		}
		int[][] analyze = new int[26][2];
		for(int r = 0; r<analyze.length;r++){
			analyze[r][0] = incorrect[r];
			analyze[r][1] = incorrect[r]/scoredSheets.size()-1;
		}
		
	}
}