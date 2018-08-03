import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * The Final Boss.
 * @author novan
 */
public class BossFinal extends Boss{
    /**
     * default values for this boss
     */
    private static final int NUMBER_OF_GUNS = 12;
    private static final int VALUE = 50000;
    private static final int SHOT_PERIOD = 200;
    private float laserSpeed = (float) 0.2;
    private int health = 150;

    /**
     * behaviour and speed for each behaviour number
     */
    private static final int[] BEHAVIOUR_TIME_STAMP =
            new int[]{5000, 2000, 3000};
    private static final int SHOT_DURATION = 7000;
    private static final float[] SPEED_SEQUENCE =
            new float[]{(float) 0.05, (float) 0.1, (float) 0.05};

    /**
     * offset values for the laser
     */
    private static final float[] LASER_OFFSET_X_VALUES =
            new float[]{-223, -209, -193, -178, -87, -73,
                    73, 87, 178, 193, 209, 223};
    private static final float[] LASER_OFFSET_Y_VALUES =
            new float[]{-81, -73, -66, -61, -21, 0, 0, -21, -61, -66, -73, -81};
    private static float MAX_Y_OFFSET = 400;
    private static float MAX_X = 396;

    /**
     * laser beam activation status
     */
    private boolean activateLaser = false;

    /**
     * Instantiates a new Boss final.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public BossFinal(String imageSource, float x, float y)
            throws SlickException {
        super(imageSource, x, y);
        setBossStats(VALUE, NUMBER_OF_GUNS, SHOT_PERIOD, SPEED_SEQUENCE[0],
                health);
        setLaserSpeed(laserSpeed);
        setMaxXCoordinate(MAX_X);
        setMaxYCoordinate(getMaxYCoordinate() - MAX_Y_OFFSET);
    }

    /**
     * shoot method for this boss
     * @throws SlickException
     */
    @Override
    public void shoot() throws SlickException {
        super.shoot();
        for (int i = getShotCounter() - NUMBER_OF_GUNS; i < getShotCounter();
             i++){
            /**
             * adding laserbeam
             */
            if (activateLaser && i >= getShotCounter() - NUMBER_OF_GUNS + 4
                    && i < getShotCounter() - NUMBER_OF_GUNS + 8 ){
                getLaser().get(i).setImageSource(new Image(LASERBEAM_PATH));
            }
            /**
             * offsets the laser
             */
            getLaser().get(i).offsetX(LASER_OFFSET_X_VALUES
                    [i%LASER_OFFSET_Y_VALUES.length]);
            getLaser().get(i).offsetY(LASER_OFFSET_Y_VALUES
                    [i%LASER_OFFSET_Y_VALUES.length]);
        }
    }

    /**
     * sets of behaviour for this boss to do based on the behaviour number
     * @param delta shows the time that has passed
     * @throws SlickException
     */
    @Override
    public void setBehaviour(int delta) throws SlickException {
        setSpeed(aggressionMultiplier() * SPEED_SEQUENCE[getBehaviourNumber()]);
        if (getBehaviourNumber() == 0) {

            /**
             * moving to a certain point and the waiting certain amount of time
             */
            if (!isFinishedMove() && this.reached(getNextXCoordinate(),
                    Boss.Y_COORDINATE)) {
                setTimeRecord(World.worldTime);
                setFinishedMove(true);

            } else if (!isFinishedMove()) {
                move(getNextXCoordinate(), Boss.Y_COORDINATE, delta);

            } else if (!isFinishedWait()) {
                setFinishedWait(finishedWait(BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]));
            }

            /**
             * if if it is finished waiting, reset the conditions and create
             * values for next behaviour
             */
            if (isFinishedWait()) {
                resetStatus();
                setNextXCoordinate(getRandomX());
                setTimeRecord(World.worldTime + BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]);
                setBehaviourNumber(getBehaviourNumber() + 1);
            }
        } else if (getBehaviourNumber() == 1){

            /**
             * moving to a random x coordinate and then shooting for a
             * certain amount of time
             */
            if (!isFinishedMove() && this.reached(getNextXCoordinate(),
                    Boss.Y_COORDINATE)){
                setFinishedMove(true);
            } else if(!isFinishedMove()){
                move(getNextXCoordinate(), Boss.Y_COORDINATE, delta);
            }
            durationalShoot(delta);

            /**
             * should it finished this behaviour set, it readies itself for
             * the next behaviour sets
             */
            if (isFinishedMove() && isFinishedShoot()){
                setTimeSinceShot(getShootPeriod());
                resetStatus();
                setNextYCoordinate(Boss.Y_COORDINATE);
                setBehaviourNumber(getBehaviourNumber() + 1);
                setTimeRecord(World.worldTime);
            }
        } else if (getBehaviourNumber() == 2){
            /**
             * waiting for a certain amount of time
             */
            if (!isFinishedWait()){
                setFinishedWait(finishedWait(BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]));
                if (isFinishedWait()){
                    setNextXCoordinate(getRandomX());
                    setNextYCoordinate(getRandomY());
                }
            }

            /**
             * moving after waiting
             */
            if(isFinishedWait() && !isFinishedMove()){
                move(getNextXCoordinate(), getNextYCoordinate(), delta);
                setFinishedMove(reached(getNextXCoordinate(),
                        getNextYCoordinate()));
                if (isFinishedMove()){
                    setTimeRecord(World.worldTime + SHOT_DURATION);
                }
            }

            /**
             * shooting with a laser beam
             */
            if (isFinishedMove()){
                activateLaser = true;
                durationalShoot(delta);
            }

            /**
             * looping the behaviour
             */
            if (isFinishedMove() && isFinishedWait() && isFinishedShoot()){
                activateLaser = false;
                resetStatus();
                setBehaviourNumber(0);
            }
        }
    }
}
