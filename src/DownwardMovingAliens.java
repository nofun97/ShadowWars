import org.newdawn.slick.SlickException;

/**
 * The type Downward moving aliens.
 * @author novan
 */
public class DownwardMovingAliens extends Enemy{

    private static int VALUE = 50;
    private static float SPEED = (float) 0.2;

    /**
     * Instantiates a new Downward moving aliens.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public DownwardMovingAliens(String imageSource, float x, float y) throws SlickException {
        super(imageSource, x, y);
        setSpeed(SPEED);
        setValue(VALUE);
    }

    /**
     * behaviour set
     * @param delta the time that has passed
     */
    @Override
    public void setBehaviour(int delta) {
        /**
         * keep moving down
         */
        if(getBehaviourNumber() == 0){
            updateY(DOWN, delta);
        }
    }
}
