import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * The background.
 * @author novan
 */
public class Background {
    private static final int BACKGROUND_AMOUNT = 6;
    private static final float BACKGROUND_MOVE = (float) 0.2;
    private static final int[] MULTIPLIER_SEQUENCE = new int[]{-1, 0, 1};
    private final float[][] INITIAL_LOCATION;
    private float newLocationDelta;
    private Image[] background;

    /**
     * Instantiates a new Background.
     *
     * @throws SlickException the slick exception
     */
    public Background() throws SlickException {
        // setting multiple backgrounds and initial locations
        background = new Image[BACKGROUND_AMOUNT];
        INITIAL_LOCATION = new float[BACKGROUND_AMOUNT][2];

        // stacking images
        for (int i = 0; i < BACKGROUND_AMOUNT; i++){
            background[i] = new Image("assets/space.png");
            INITIAL_LOCATION[i][0] = i%2 * background[i].getWidth();
            INITIAL_LOCATION[i][1] = MULTIPLIER_SEQUENCE
                    [i%MULTIPLIER_SEQUENCE.length]
                    * background[i].getHeight();
        }

        newLocationDelta = 0;

    }

    /**
     * Update the images location.
     *
     * @param delta the delta
     */
    public void update(int delta){

        // updating to new location and resets should it not be in screen
        newLocationDelta += (BACKGROUND_MOVE * delta);
        newLocationDelta %= background[0].getHeight();
    }

    /**
     * Render the images.
     */
    public void render() {

        // Drawing all changing positions
        for(int i = 0; i < BACKGROUND_AMOUNT; i++){
            float xPosition = INITIAL_LOCATION[i][0];
            float yPosition = INITIAL_LOCATION[i][1] + newLocationDelta;
            background[i].draw(xPosition, yPosition);
        }
    }
}
