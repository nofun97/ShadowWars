import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


/**
 * The type Boss Space Hunter.
 * @author novan
 */
public class BossSpaceHunter extends Boss{
    /**
     * default values
     */
    private static final int SHOT_PERIOD = 100;
    private static final int NUMBER_OF_GUNS = 3;
    private static final int VALUE = 9000;
    private int health = 100;

    /**
     * behaviour, speed sequences and laser offset values
     */
    private static final int[] BEHAVIOUR_TIME_STAMP = new int[]
            {2000, 2000, 4000};
    private static final float[] SPEED_SEQUENCE = new float[]
            {(float) 0.05, (float) 0.5, (float) 0.25, (float) 0.1};
    private static final int SHOOT_DURATION = 5000;
    private static final float[] LASER_X_OFFSET_VALUES = new float[]
            {-84, 0, 84};
    private static final float[] LASER_Y_OFFSET_VALUES = new float[]
            {-40, -9, -40};
    private static final int[] LASER_HORIZONTAL_DIRECTION = new int[]
            {RIGHT, NO_MOVE, LEFT};
    private static final float MAX_Y_OFFSET = -500;
    private boolean activateLaserbeam = false;
    private boolean appeared = false;

    /**
     * Instantiates a new Boss space hunter.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public BossSpaceHunter(String imageSource, float x, float y)
            throws SlickException {
        super(imageSource, x, y);
        setBossStats(VALUE, NUMBER_OF_GUNS, SHOT_PERIOD, SPEED_SEQUENCE[0],
                health);
        setMaxYCoordinate(getMaxYCoordinate() + MAX_Y_OFFSET);
    }

    @Override
    public void shoot() throws SlickException {
        super.shoot();
        for (int i = getShotCounter() - NUMBER_OF_GUNS; i < getShotCounter();
             i++){
            if (i == getShotCounter() - NUMBER_OF_GUNS + 1){
                getLaser().get(i).setImageSource
                        (new Image(ActionCharacters.LASERBEAM_PATH));
                getLaser().get(i).setActive(activateLaserbeam);
            }
            getLaser().get(i).offsetX
                    (LASER_X_OFFSET_VALUES[i%LASER_X_OFFSET_VALUES.length]);
            getLaser().get(i).offsetY
                    (LASER_Y_OFFSET_VALUES[i%LASER_Y_OFFSET_VALUES.length]);
            getLaser().get(i).setHorizontalShotDirection
                    (LASER_HORIZONTAL_DIRECTION[i%LASER_HORIZONTAL_DIRECTION.length]);
        }
    }

    /**
     * behaviour sets for this boss
     * @param delta time that has passed
     * @throws SlickException
     */
    @Override
    public void setBehaviour(int delta) throws SlickException {
        setSpeed(SPEED_SEQUENCE[getBehaviourNumber()]);
        /**
         * speeding up the laser
         */
        setLaserSpeed(getLaserSpeed() * aggressionMultiplier());
        int multipliedDelta = delta * aggressionMultiplier();
        if (getBehaviourNumber() == 0){

            /**
             * moving to a certain point and then waiting
             */
            if(!reached(getX(), Boss.Y_COORDINATE) && !appeared){
                move(getX(), Boss.Y_COORDINATE, multipliedDelta);
                if(reached(getX(), Boss.Y_COORDINATE)){
                    setTimeRecord(World.worldTime);
                    appeared = true;
                }
            } else if (!isFinishedWait()){
                setFinishedWait(finishedWait(BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]));
                if (isFinishedWait()){
                    setNextXCoordinate(getRandomX());
                }
            }

            /**
             * moving to a certain point and then shooting for a certain amount
             * of time
             */
            if (!isFinishedMove() && isFinishedWait() && !reached
                    (getNextXCoordinate(), Boss.Y_COORDINATE)){
                move(getNextXCoordinate(), Boss.Y_COORDINATE, multipliedDelta);
                if (reached(getNextXCoordinate(), Boss.Y_COORDINATE)) {
                    setNextXCoordinate(getRandomX());
                    setFinishedMove(true);
                    setTimeRecord(World.worldTime + SHOOT_DURATION);
                }

            } else if (isFinishedMove()){
                move(getNextXCoordinate(), Boss.Y_COORDINATE, multipliedDelta);
                durationalShoot(delta);
            }

            /**
             * getting to the next behaviour
             */
            if (isFinishedWait() && isFinishedShoot() && isFinishedMove()){
                resetStatus();
                setTimeRecord(World.worldTime);
                setBehaviourNumber(getBehaviourNumber() + 1);
            }
        } else if (getBehaviourNumber() == 1){
            /**
             * waiting and then moving to a random coordinate
             */
            if (!isFinishedWait()){
                setFinishedWait(finishedWait(BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]));
                if (isFinishedWait()){
                    setNextXCoordinate(getRandomX());
                    setNextYCoordinate(getRandomY());
                    setTimeRecord(World.worldTime + BEHAVIOUR_TIME_STAMP
                            [getBehaviourNumber()]);
                }
            } else if (isFinishedWait() && !isFinishedMove()){
                if (World.worldTime <= getTimeRecord()){
                    move(getNextXCoordinate(), getNextYCoordinate(),
                            multipliedDelta);
                } else {
                    setFinishedMove(true);
                }

                if (isFinishedMove()){
                    setTimeRecord(World.worldTime + SHOOT_DURATION);
                }
            }

            /**
             * shooting a laserbeam in that coordinate
             */
            if (isFinishedShoot()){
                activateLaserbeam = false;
            } else if (!isFinishedShoot() && isFinishedMove()){
                activateLaserbeam = true;
                durationalShoot(delta);
            }

            /**
             * getting to the next behaviour
             */
            if (isFinishedWait() && isFinishedShoot() && isFinishedMove()){
                resetStatus();
                setBehaviourNumber(getBehaviourNumber() + 1);
            }
        } else if (getBehaviourNumber() == 2){
            /**
             * reaching a certain point and then waiting
             */
            if (!reached(getX(), Boss.Y_COORDINATE) && !isFinishedMove()){
                move(getX(), Boss.Y_COORDINATE, multipliedDelta);
                setFinishedMove(reached(getX(), Boss.Y_COORDINATE));
                if (isFinishedMove()){
                    setNextXCoordinate(getRandomX());
                    setNextYCoordinate(getRandomY());
                    setTimeRecord(World.worldTime);
                }
            } else if (!isFinishedWait()){
                setFinishedWait(finishedWait(BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]));
                if (isFinishedWait()){
                    setTimeRecord(World.worldTime + SHOOT_DURATION);
                }
            }

            /**
             * shooting faster for a certain amount of time
             */
            if (isFinishedWait()){
                if (World.worldTime <= getTimeRecord()){
                    shoot();
                } else {
                    setFinishedShoot(true);
                }
                move(getNextXCoordinate(), getNextYCoordinate(),
                        multipliedDelta);
            }

            /**
             * looping the behaviour
             */
            if (isFinishedWait() && isFinishedShoot() && isFinishedMove()){
                resetStatus();
                activateLaserbeam = false;
                setBehaviourNumber(0);
            }
        }
    }

    /**
     * activating the laser beam after a certain health point is reached
     * @return
     */
    @Override
    public int aggressionMultiplier() {
        if(getHealth() < 0.25 * health) activateLaserbeam = true;
        return super.aggressionMultiplier();
    }
}
