package application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.opencv.videoio.VideoCapture;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;

public class CameraController {

	/**
	 * The CameraController class manages the video source, GUI elements, and the internal
	 * image processing.
	 */

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Load the OpenCV Core library
	}
	@FXML
	private Button startB;
	@FXML
	private ImageView currentFrame;
	@FXML
	private CheckBox toggleRed;
	@FXML
	private CheckBox toggleYellow;
	@FXML
	private CheckBox toggleGreen;
	@FXML
	private CheckBox toggleTeal;
	@FXML
	private CheckBox toggleBlue;
	@FXML
	private CheckBox togglePurple;

	private ScheduledExecutorService timer;
	private VideoCapture capture = new VideoCapture();
	private boolean cameraActive = false;
	private ColorData data = new ColorData();
	private static final int minSize = 800;	// Minimum size threshold for contour area

	/**
	 * Toggle the camera on/off.
	 */
	@FXML
	protected void startCamera(ActionEvent event) {
		if (!this.cameraActive) {
			this.capture.open(0);
			if (this.capture.isOpened()) {
				this.cameraActive = true;

				Runnable frameGrabber = new Runnable() {

					@Override
					public void run() {
						try {
							Mat frame = grabFrame();
							MatOfByte buffer = new MatOfByte();
							Imgcodecs.imencode(".bmp", frame, buffer);
							BufferedImage im = ImageIO.read(new ByteArrayInputStream(buffer.toArray()));
							currentFrame.setImage(SwingFXUtils.toFXImage(im, null));
						} catch (IOException e) {
							System.out.println("Error");
						}
					}
				};
				// Create a new SEC to run frameGrabber in 30 FPS
				this.timer = Executors.newSingleThreadScheduledExecutor();
				this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
				this.startB.setText("Stop Camera");
			} else {
				System.err.println("Impossible to open the camera connection...");
			}
		} else {
			// User pressed 'Stop Camera'
			this.cameraActive = false;
			this.startB.setText("Start Camera");
			this.stopAcquisition();
		}
	}

	/**
	 * @return a Mat with processed color highlights.
	 *
	 */
	private Mat grabFrame() {
		Mat frame = new Mat();
		if (this.capture.isOpened()) {
			try {
				this.capture.read(frame);

				if (!frame.empty()) {
					Core.flip(frame, frame, +1);
					for (String color : data.getKeys()) {
						frame = detect(frame, color);
					}
				}
			} catch (Exception e) {
				System.err.println("Exception during the image elaboration: " + e);
			}
		}
		return frame;
	}

	/**
	 * Detect and highlight a color on a given Mat.
	 *
	 * @param mat a Mat to be processed.
	 * @param color the color to be detected.
	 * @return a processed Mat.
	 */
	private Mat detect(Mat mat, String color) {
		Scalar[] scalars = data.get(color);
		Mat hsv = new Mat();
		Mat mask1 = new Mat();
		Mat mask2 = new Mat();
		Imgproc.cvtColor(mat, hsv, Imgproc.COLOR_BGR2HSV); // Convert color space
		Mat mask = new Mat();
		if (color.equals("red")) {
			Core.inRange(hsv, scalars[0], scalars[1], mask1);
			Core.inRange(hsv, scalars[2], scalars[3], mask2);
			Core.add(mask1, mask2, mask);
		} else {
			Core.inRange(hsv, scalars[0], scalars[1], mask);
		}
		Scalar contourColor = scalars[scalars.length - 1];
		contour(contourColor, mask, mat, color);
		return mat;
	}

	/**
	 * Finds and highlights sufficiently large countours of a given mask.
	 *
	 * @param color a BGR Scalar to be used as highlight and text color.
	 * @param mask a binary image Mat to be scanned for contours.
	 * @param mat the non-binary Mat.
	 * @param text the text to be displayed at the center of the contour.
	 */
	private void contour(Scalar color, Mat mask, Mat mat, String text) {
		List<MatOfPoint> contours = new ArrayList<>();
		List<MatOfPoint> objContours = new ArrayList<>();

		// Smooth out the mask and remove noise
		Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_OPEN, new Mat(), new Point(-1, -1), 3);
		Imgproc.dilate(mask, mask, new Mat());

		// Find all contours
		Imgproc.findContours(mask, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		for (MatOfPoint c : contours) {
			// If area is large enough, draw midpoint and save to object
			if (Imgproc.contourArea(c) > minSize) {
				Moments m = Imgproc.moments(c);
				int x = (int) (m.get_m10() / m.get_m00());
				int y = (int) (m.get_m01() / m.get_m00());
				Imgproc.circle(mat, new Point(x, y), 5, color);
				Imgproc.putText(mat, text, new Point(x + 10, y), Imgproc.FONT_HERSHEY_SIMPLEX, 1, color, 3);

				objContours.add(c);
			}
		}
		for (int i = 0; i < objContours.size(); i++) {
			// Color each object contour and put them on input Mat
			Imgproc.drawContours(mat, objContours, i, color);
		}
	}

	/*
	 * Checkbox methods for choosing which colors to detect.
	 */

	@FXML
	protected void toggleRed(ActionEvent event) {
		if (this.data.get("red") != null) {
			this.data.remove("red");
		} else {
			this.data.add("red");
		}
	}

	@FXML
	protected void toggleYellow(ActionEvent event) {
		if (this.data.get("yellow") != null) {
			this.data.remove("yellow");
		} else {
			this.data.add("yellow");
		}
	}

	@FXML
	protected void toggleGreen(ActionEvent event) {
		if (this.data.get("green") != null) {
			this.data.remove("green");
		} else {
			this.data.add("green");
		}
	}

	@FXML
	protected void toggleTeal(ActionEvent event) {
		if (this.data.get("teal") != null) {
			this.data.remove("teal");
		} else {
			this.data.add("teal");
		}
	}

	@FXML
	protected void toggleBlue(ActionEvent event) {
		if (this.data.get("blue") != null) {
			this.data.remove("blue");
		} else {
			this.data.add("blue");
		}
	}

	@FXML
	protected void togglePurple(ActionEvent event) {
		if (this.data.get("purple") != null) {
			this.data.remove("purple");
		} else {
			this.data.add("purple");
		}
	}

	/**
	 * Stop camera acquisition
	 */
	private void stopAcquisition() {
		if (this.timer != null && !this.timer.isShutdown()) {
			try {
				this.timer.shutdown();
				this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
			}
		}
		if (this.capture.isOpened()) {
			this.capture.release();
		}
	}
}
