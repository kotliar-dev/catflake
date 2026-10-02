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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Snowflake extends Application {
    public final int SCENE_WIDTH = 800;
    public final int SCENE_HIGH = 800;

    //Cat face initial parameters
    public final int INITIAL_KITTY_WIDTH = 400;
    public final int INITIAL_KITTY_HIGH = 200;
    public final double START_KITTY_X = SCENE_WIDTH / 2.0;
    public final double START_KITTY_Y = SCENE_HIGH / 2.0;

    @Override
    public void start(Stage stage) throws Exception {
        Group catFace = new Group();

        drawKitty(catFace);

        Scene scene = new Scene(catFace, SCENE_WIDTH, SCENE_HIGH);

        stage.setScene(scene);
        stage.show();
    }

    public void drawKitty(Group group){
        buildHalf(1, 2,group, START_KITTY_X, START_KITTY_Y, 0);
        buildHalf(-1, 2, group, START_KITTY_X, START_KITTY_Y, 0);
    }

    public void buildHalf(int sideMultiplier, int level, Group group, double startX, double startY, int rotate) {
        Group kittyFace = new Group();

        if (level >= 1) {
            Line bottom = new Line(
                    startX,
                    startY + INITIAL_KITTY_HIGH / 2.0,
                    startX + INITIAL_KITTY_WIDTH / 2.0 * sideMultiplier,
                    startY + INITIAL_KITTY_HIGH / 2.0
            );

            Line cheek = new Line(
                    bottom.getEndX(),
                    bottom.getEndY(),
                    bottom.getEndX(),
                    bottom.getEndY() - INITIAL_KITTY_HIGH
            );

            Line ear = new Line(
                    cheek.getEndX(),
                    cheek.getEndY(),
                    cheek.getEndX() - INITIAL_KITTY_WIDTH / 4.0 * sideMultiplier,
                    cheek.getEndY() + INITIAL_KITTY_HIGH / 4.0
            );

            Line top = new Line(
                    ear.getEndX(),
                    ear.getEndY(),
                    bottom.getStartX(),
                    ear.getEndY()
            );

            Line noseSide = new Line(
                    bottom.getStartX(),
                    bottom.getStartY() - INITIAL_KITTY_HIGH / 8.0,
                    bottom.getStartX() + INITIAL_KITTY_WIDTH / 16.0 * sideMultiplier,
                    bottom.getStartY() - INITIAL_KITTY_HIGH / 4.0
            );

            Line noseTop = new Line(
                    noseSide.getEndX(),
                    noseSide.getEndY(),
                    noseSide.getStartX(),
                    noseSide.getEndY()
            );

            Circle eye = new Circle(
                    bottom.getStartX() + INITIAL_KITTY_WIDTH / 4.0 * sideMultiplier,
                    bottom.getStartY() - INITIAL_KITTY_HIGH / 8.0 * 3,
                    INITIAL_KITTY_HIGH / 6.0
            );

            kittyFace.getChildren().addAll(top, bottom, cheek, ear, noseSide, noseTop, eye);

            kittyFace.setRotate(90.0 * rotate * sideMultiplier);

            group.getChildren().add(kittyFace);

            buildHalf(-1, level - 1, group, cheek.getEndX(), cheek.getStartY() - cheek.getEndY() / 2, rotate + 1);
            buildHalf(1, level - 1, group, cheek.getEndX(), cheek.getStartY() - cheek.getEndY() / 2, rotate + 1);
        }
    }

    //After first kitty use helper

    public static void main(String[] args){
        launch(args);
    }
}
