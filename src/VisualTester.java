import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class VisualTester extends PApplet {
	ArrayList<PImage> images;
	PImage current_image;
	int currentImageIndex = 0;
	int w = 1200;
	int h = 900;

	public void setup() {
		size(w, h);
		images = PDFHelper.getPImagesFromPdf("/test3.pdf");
	}

	public void draw() {
		background(255);
		if (images.size() > 0) {
			current_image = images.get(currentImageIndex);
			image(current_image, 0, 0); // display image i

			fill(0);
			text(mouseX + " " + mouseY, 30, 30);
		}
	}

	public void mouseReleased() {
		currentImageIndex = (currentImageIndex + 1) % images.size(); // increment
																		// current
																		// image
	}
}
