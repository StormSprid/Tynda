package org.example.musicplayer03;

import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Animations {

public static void rotateImage(ImageView place,double value){
    RotateTransition rotate = new RotateTransition();
    rotate.setDuration(Duration.millis(500));
    rotate.setAxis(Rotate.Y_AXIS);
    rotate.setCycleCount(1);
    rotate.setNode(place);
    rotate.setByAngle(value);
    rotate.play();
}
}
