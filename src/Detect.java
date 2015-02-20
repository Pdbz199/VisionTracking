import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
//import org.opencv.core.Size;
//import org.opencv.highgui.VideoCapture;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Detect {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat image = Highgui.imread("img_1423144640576.png");
		// VideoCapture camera = new VideoCapture(1);
		// camera.open(1);
		// Mat image = new Mat();
		// if (camera.isOpened()) {
		// while (true) {
		// camera.retrieve(image);

		Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
		Imgproc.Canny(image, image, 300, 600, 5, true);

		List<MatOfPoint> contours = new ArrayList<>();
		MatOfInt4 hierarchy = new MatOfInt4();
		Imgproc.findContours(image, contours, hierarchy, Imgproc.RETR_LIST,
				Imgproc.CHAIN_APPROX_SIMPLE);
		Imgproc.drawContours(image, contours, -1, new Scalar(255, 255, 0));

		Random random = new Random();

		Mat drawing = Mat.zeros(image.size(), CvType.CV_8UC3);
		for (int i = 0; i < contours.size(); i++) {
			Rect rect = Imgproc.boundingRect(contours.get(i));
			if (rect.area() > 50000) {
				System.out.println("TRUE");
				System.out.println("x:" + rect.x + "\t" + "y:" + rect.y);
			}
			// System.out.println(rect.area());
			Scalar color = new Scalar(random.nextDouble() * 255,
					random.nextDouble() * 255, random.nextDouble() * 255);
			Imgproc.drawContours(drawing, contours, i, color, 2, 8, hierarchy,
					0, new Point());
		}
		Highgui.imwrite("test.png", drawing);
	}
}