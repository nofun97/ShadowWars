import org.newdawn.slick.SlickException;
import java.lang.Math;

/**
 * The type Sine moving aliens.
 * @author novan
 */
public class SineMovingAliens extends Enemy {
    private static final float SPEED = (float) 0.15;
    /**
     * sine movement values
     */
    private int amplitude = 96;
    private int period = 1500;
    private int delay = 2;
    private int time = 0;
    private float initialX;

    /**
     * Instantiates a new Sine moving aliens.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public SineMovingAliens(String imageSource, float x, float y)
            throws SlickException{
        super(imageSource, x, y);
        this.initialX = x;
        setSpeed(SPEED);
    }

    /**
     * behaviour sets for this alien
     * @param delta the delta
     */
    @Override
    public void setBehaviour(int delta) {
        /**
         * moving down in a sine wave
         */
        if(getBehaviourNumber() == 0){
            time += delta;
            float offsetX = (float) (amplitude * Math.sin((float) 2 *
                    Math.PI/period * (time - delay)));
            setX(initialX + offsetX);
            updateY(DOWN, delta);
        }
    }
}
