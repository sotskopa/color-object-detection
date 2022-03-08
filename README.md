# Object detection in Java

### Demo
[![Demo Doccou alpha](https://i.gyazo.com/d2ae108de7bdb97f01932924dc895407.gif)](https://i.gyazo.com/d2ae108de7bdb97f01932924dc895407.gif)

## Authors: Amin Kokhaei & Marcus Gisslén
### Object Detection in Java using OpenCV
This project involves tracking an object captured by a camera using [OpenCV](https://opencv.org/).
The object in question should be any monochrome object presented to the camera within reasonable
lighting conditions. The user will receive output in the form of an altered view of the camera
input, where each monochrome object in view is highlighted as its color is displayed.

### Libraries and modules used
#### Open Source Computer Vision (OpenCV)
The OpenCV module is used for color detection of monochrome objects. The OpenCV methods are used to detect the middle point of objects and draw contours around them. The contours have the same color as the objects presented.

#### JavaFX
JavaFX is a library used to create the application's user interface. The methods are for example used to create the windows of the program, the buttons and the checkboxes.

Compile with
`javac -cp "PATH_TO_OPENCV\build\java\opencv-xxx.jar;." --module-path "PATH_TO_JAVAFX\lib" --add-modules javafx.controls,javafx.fxml,javafx.swing application/*java`

To run, use
`java -cp "PATH_TO_OPENCV\build\java\opencv-xxx.jar;." --module-path "PATH_TO_JAVAFX\lib" --add-modules javafx.controls,javafx.fxml,javafx.swing -Djava.library.path="PATH_TO_OPENCV\build\java\x64" application.Main`
