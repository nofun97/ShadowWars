import org.newdawn.slick.SlickException;

/**
 * The type Minion shooter.
 * @author novan
 */
public class MinionShooter extends ShootingAliens {
    private static final int SHOT_PERIOD = 500;
    private static final int VALUE = 200;
    private int horizontalDirection;

    /**
     * Instantiates a new Minion shooter.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public MinionShooter(String imageSource, float x, float y)
            throws SlickException {
        super(imageSource, x, y);
        setTimeSinceShot(SHOT_PERIOD);
        setShootPeriod(SHOT_PERIOD);
        setValue(VALUE);
        /**
         * determining the direction based on its initial location
         */
        horizontalDirection = x < App.SCREEN_WIDTH/2 ? RIGHT : LEFT;
    }

    /**
     * behaviour sets for this shooter
     * @param delta the delta
     * @throws SlickException
     */
    @Override
    public void setBehaviour(int delta) throws SlickException {
        /**
         * moving in a gradient while shooting
         */
        if (getBehaviourNumber() == 0){
            updateX(horizontalDirection, delta);
            updateY(DOWN, delta);

            setTimeSinceShot(getTimeSinceShot() + delta);

            if (!getCollided() && getTimeSinceShot() >= getShootPeriod()){
                setTimeSinceShot(0);
                shoot();
            }
        }
    }
}
