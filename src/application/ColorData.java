package application;
import java.util.HashMap;
import java.util.Set;

import org.opencv.core.Scalar;

/**
 * The ColorData class is used to hold the data about different colors.
 * The data is used by the main class of the program.
 */
public class ColorData {

	private static HashMap<String, Scalar[]> map;

	/**
	 * Creates a ColorData object with an empty map.
	 */
	public ColorData() {
		map = new HashMap<>();
	}

	/**
	 * Get the Scalar array of HSV or BGR color data for a given color.
	 * @param color The name of the color.
	 * @return an array of color values.
	 */
	public Scalar[] get(String color) {
		return map.get(color.toLowerCase());
	}

	/**
	 * Add a preset color to the map. The contour color is stored as the array's last element.
	 * @param color The name of the color to add.
	 */
	public void add(String color) {
		color = color.toLowerCase();
		switch(color) {
		case "red":
			map.put("red", new Scalar[] {new Scalar(0, 100, 80), new Scalar(15, 255, 255),
					new Scalar(155, 100, 80), new Scalar(180, 255, 255), new Scalar(130, 130, 255)});
			break;
		case "green":
			map.put("green", new Scalar[] {new Scalar(40, 80, 70), new Scalar(80, 255, 255), new Scalar(130, 255, 130)});
			break;
		case "blue":
			map.put("blue", new Scalar[] {new Scalar(90, 100, 52), new Scalar(115, 255, 255), new Scalar(255, 130, 130)});
			break;
		case "yellow":
			map.put("yellow", new Scalar[] {new Scalar(20, 100, 100), new Scalar(30, 255, 255), new Scalar(80, 210, 250)});
			break;
		case "teal":
			map.put("teal", new Scalar[] {new Scalar(81, 120, 80), new Scalar(89, 255, 255), new Scalar(250, 170, 80)});
			break;
		case "purple":
			map.put("purple", new Scalar[] {new Scalar(120, 120, 52), new Scalar(140, 125, 255), new Scalar(250, 80, 170)});
			break;
		}
	}

	/**
	 * @return the added color names.
	 */
	public Set<String> getKeys() {
		return map.keySet();
	}

	/**
	 * Remove a color from the map.
	 * @param color the color to remove.
	 */
	public void remove(String color) {
		color = color.toLowerCase();
		if (map.containsKey(color)) {
			map.remove(color);
		}
	}
}
