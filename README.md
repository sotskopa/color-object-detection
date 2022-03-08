# DD1349 Project

## Amin Kokhaei & Marcus Gisslén
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

### Installation Guide
1. To run this color detecting application in your IDE, you have to download the OpenCV module at:
* https://opencv.org/releases/

2. You also need the JavaFX SDK library in order for the IDE to be able to read all code that has to do with the GUI. You can do this at:
* https://openjfx.io/

3. You have to connect these two features to your project in your preferable IDE. For those who use Eclipse, try these pages:
* https://www.javatpoint.com/javafx-with-eclipse
* https://docs.opencv.org/2.4/doc/tutorials/introduction/java_eclipse/java_eclipse.html

For IntelliJ users, take a look at these links:
* https://www.jetbrains.com/help/idea/javafx.html
* https://medium.com/@aadimator/how-to-set-up-opencv-in-intellij-idea-6eb103c1d45c

If you want to run the program without an IDE (i.e. from the command line), please do the following steps:  

* Via the command line, navigate to the `/src/` folder.

* Compile with
`javac -cp "PATH_TO_OPENCV\build\java\opencv-xxx.jar;." --module-path "PATH_TO_JAVAFX\lib" --add-modules javafx.controls,javafx.fxml,javafx.swing application/*java`

* To run, use
`java -cp "PATH_TO_OPENCV\build\java\opencv-xxx.jar;." --module-path "PATH_TO_JAVAFX\lib" --add-modules javafx.controls,javafx.fxml,javafx.swing -Djava.library.path="PATH_TO_OPENCV\build\java\x64" application.Main`

> NOTE: Linux users should replace every ";" with an ":".
