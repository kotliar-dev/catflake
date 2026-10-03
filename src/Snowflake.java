
// Just so you see what is the idea behind points

//                                   x2y2
//                |\        |       /|
//                | \_______|__x3y3/ |
//                |         |x4y3    |
//                |   ( )   |   ( )  |
//                |        \|/       |
//                |_________|________|
//                          |x1y1    x2y1

// I feel so freaking smart, actually :)
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * @author Nastia Kotliar
 *
 * @since 02.10.2026
 * @version 1.2
 */
public class Snowflake extends Application {
    public final int SCENE_WIDTH = 800;
    public final int SCENE_HEIGHT = 800;

    //Cat face initial parameters
    public final int INITIAL_KITTY_WIDTH = 400;
    public final int INITIAL_KITTY_HEIGHT = 200;
    public final Point2D INITIAL_MIDPOINT = new Point2D(
            SCENE_WIDTH / 2.0,
            SCENE_HEIGHT / 2.0
    );

    @Override
    public void start(Stage stage) throws Exception {
        int level = 0;
        int maxLevels = 5;

        Group recursiveCat = new Group();

        drawKitty(recursiveCat, level, maxLevels, INITIAL_MIDPOINT, 0);

        Scene scene = new Scene(recursiveCat, SCENE_WIDTH, SCENE_HEIGHT);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Regulates position, such as midpoint, rotation, and size.
     * Then passes these parameters to line drawing method.
     *
     * @param group where we put the drawn face
     * @param index help navigate through levels of recursions
     * @param level how many levels we're looking for
     * @param midpoint where to start drawing
     * @param rotation helps to rotate faces based on parent angle (Chatty helped with this too)
     */
    public void drawKitty (Group group, int index, int level, Point2D midpoint, int rotation){
        // The group needs to operate the position of the whole face, as I use symmetry to draw face's halves
        Group kittyFace = new Group();
        // Defines where program starts drawing
        Point2D position = group.sceneToLocal(midpoint);

        // Base case is when index equals to deepest level
        if (index < level) {
            // Drawing halves and rotating
            kittyFace.getChildren().addAll(
                    buildHalf(1, position, Math.pow(2, index)),
                    buildHalf(-1, position, Math.pow(2, index))
            );
            kittyFace.setRotate(rotation);

            // Adding each face to the main group
            group.getChildren().add(kittyFace);

            // Getting ALREADY ROTATED lines to later get future midpoints coordinates related to scene, not group
            Group rightHalf = (Group) kittyFace.getChildren().getFirst();
            Group leftHalf = (Group) kittyFace.getChildren().getLast();

            Line rightCheek = (Line) rightHalf.getChildren().getFirst();
            Line leftCheek = (Line) leftHalf.getChildren().getFirst();

            // Getting midpoints. This solvation was suggested by Chatster after me fighting with it for hours
            Point2D rightMidpoint = rightCheek.localToScene(
                    ((rightCheek.getStartX() + rightCheek.getEndX()) / 2) + (INITIAL_KITTY_HEIGHT / 2.0 / Math.pow(2, index + 1)),
                    (rightCheek.getStartY() + rightCheek.getEndY()) / 2
            );

            Point2D leftMidpoint = leftCheek.localToScene(
                    ((leftCheek.getStartX() + leftCheek.getEndX()) / 2) - (INITIAL_KITTY_HEIGHT / 2.0 / Math.pow(2, index + 1)),
                    (leftCheek.getStartY() + leftCheek.getEndY()) / 2
            );

            // Calling our baby recursion
            drawKitty(group, index + 1, level, rightMidpoint, rotation + 90);
            drawKitty(group, index + 1, level, leftMidpoint, rotation - 90);
        }
    }

    /**
     * Builds left or right halves of kitty faces based on the parent face's position
     *
     * @param sideMultiplier tells program to draw to the left or to the right
     * @param position midpoint to start drawing around
     * @param sizeMultiplier how much to divide to. half powered to index
     *
     * @return left or right half
     */
    public Group buildHalf(int sideMultiplier, Point2D position, double sizeMultiplier) {
        Group kittyFaceHalf = new Group();

        Line bottom = new Line(
                position.getX(),
                position.getY() + INITIAL_KITTY_HEIGHT / 2.0 / sizeMultiplier,
                position.getX() + INITIAL_KITTY_WIDTH / 2.0 * sideMultiplier / sizeMultiplier,
                position.getY() + INITIAL_KITTY_HEIGHT / 2.0 / sizeMultiplier
        );

        Line cheek = new Line(
                bottom.getEndX(),
                bottom.getEndY(),
                bottom.getEndX(),
                bottom.getEndY() - INITIAL_KITTY_HEIGHT / sizeMultiplier
        );

        Line ear = new Line(
                cheek.getEndX(),
                cheek.getEndY(),
                cheek.getEndX() - INITIAL_KITTY_WIDTH / 4.0 * sideMultiplier / sizeMultiplier,
                cheek.getEndY() + INITIAL_KITTY_HEIGHT / 4.0 / sizeMultiplier
        );

        Line top = new Line(
                ear.getEndX(),
                ear.getEndY(),
                bottom.getStartX(),
                ear.getEndY()
        );

        Line noseSide = new Line(
                bottom.getStartX(),
                bottom.getStartY() - INITIAL_KITTY_HEIGHT / 8.0 / sizeMultiplier,
                bottom.getStartX() + INITIAL_KITTY_WIDTH / 16.0 * sideMultiplier / sizeMultiplier,
                bottom.getStartY() - INITIAL_KITTY_HEIGHT / 4.0 / sizeMultiplier
        );

        Line noseTop = new Line(
                noseSide.getEndX(),
                noseSide.getEndY(),
                noseSide.getStartX(),
                noseSide.getEndY()
        );

        Circle eye = new Circle(
                bottom.getStartX() + INITIAL_KITTY_WIDTH / 4.0 * sideMultiplier / sizeMultiplier,
                bottom.getStartY() - INITIAL_KITTY_HEIGHT / 8.0 * 3 / sizeMultiplier,
                INITIAL_KITTY_HEIGHT / 6.0 / sizeMultiplier
        );

        kittyFaceHalf.getChildren().addAll(cheek, top, bottom, ear, noseSide, noseTop, eye);

        return kittyFaceHalf;
    }

    public static void main (String[] args){
        launch(args);
    }
}
