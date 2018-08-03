import org.newdawn.slick.SlickException;

import java.util.Random;

/**
 * The type Basic shooter.
 *
 * @author novan
 */
public class BasicShooter extends ShootingAliens {
    /**
     * default value for basic shooters
     */
    private static final int SHOT_PERIOD = 3500;
    private static final int VALUE = 200;
    private static final float Y_RANGE_MAX = 464;
    private static final float Y_RANGE_MIN = 48;
    private Random rand = new Random();

    /**
     * Instantiates a new Basic shooter.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public BasicShooter(String imageSource, float x, float y) throws
            SlickException {
        super(imageSource, x, y);
        // choosing the y coordinate to head to
        setyCoordinate(rand.nextInt((int)
                (Y_RANGE_MAX - Y_RANGE_MIN) + 1) + Y_RANGE_MIN);
        // sets the value
        setTimeSinceShot(SHOT_PERIOD);
        setShootPeriod(SHOT_PERIOD);
        setValue(VALUE);
    }

    /**
     * determines the behaviour by using behaviour number
     * @param delta
     * @throws SlickException
     */
    @Override
    public void setBehaviour(int delta) throws SlickException{
        if (getBehaviourNumber() == 0){

            /**
             * moving until it reach a certain point
             */
            if (reached(getX(), getyCoordinate())){
                setFinishedMove(true);
                setBehaviourNumber(getBehaviourNumber() + 1);
            } else {
                this.move(getX(), getyCoordinate(), delta);
            }
        } else if (getBehaviourNumber() == 1){

            /**
             * keeps shooting
             */
            setTimeSinceShot(getTimeSinceShot() + delta);

            if (!getCollided() && getTimeSinceShot() >= getShootPeriod()){
                setTimeSinceShot(0);
                shoot();
            }
        }
    }
}
