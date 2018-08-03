import org.newdawn.slick.SlickException;

import java.util.Random;

/**
 * The type Purple Splitter Boss.
 * @author novan
 */
public class BossPurpleSplitter extends Boss {
    /**
     * default values for this boss
     */
    private static final int SHOT_PERIOD = 125;
    private static final int NUMBER_OF_GUNS = 5;
    private static final int VALUE = 7500;
    private int health = 100;
    /**
     * behaviour time and speed sequences
     */
    private static final int[] BEHAVIOUR_TIME_STAMP = new int[]
            {3000, 3000, 3000, 4000};
    private static final float[] SPEED_SEQUENCE = new float[]
            {(float) 0.05, (float) 0.2, (float) 0.2, (float) 0.1};

    /**
     * laser offset values and directions
     */
    private static final float[] LASER_X_OFFSET_VALUES = new float[]
            {-75, -58, 0, 58, 75};
    private static final float[] LASER_Y_OFFSET_VALUES = new float[]
            {-82, -12, 0, -12, -82};
    private static final int[] LASER_HORIZONTAL_DIRECTION = new int[]
            {LEFT, LEFT, NO_MOVE, RIGHT, RIGHT};
    private static final int[] LASER_VERTICAL_DIRECTION = new int[]
            {NO_MOVE, DOWN, DOWN, DOWN, NO_MOVE};
    private static final int MAX_Y_OFFSET = -200;

    /**
     * Instantiates a new Purple Splitter boss.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public BossPurpleSplitter(String imageSource, float x, float y)
            throws SlickException {
        super(imageSource, x, y);
        setBossStats(VALUE, NUMBER_OF_GUNS, SHOT_PERIOD, SPEED_SEQUENCE[0],
                health);
        /**
         * setting max y coordinate
         */
        setMaxYCoordinate(getMaxYCoordinate() + MAX_Y_OFFSET);
    }

    /**
     * shooting method for this boss
     * @throws SlickException
     */
    @Override
    public void shoot() throws SlickException {
        super.shoot();
        for (int i = getShotCounter() - NUMBER_OF_GUNS; i < getShotCounter();
             i++){
            /**
             * offsets the values and directions
             */
            getLaser().get(i).offsetX(LASER_X_OFFSET_VALUES
                    [i%LASER_X_OFFSET_VALUES.length]);
            getLaser().get(i).offsetY(LASER_Y_OFFSET_VALUES
                    [i%LASER_Y_OFFSET_VALUES.length]);
            getLaser().get(i).setHorizontalShotDirection(
                    LASER_HORIZONTAL_DIRECTION
                            [i%LASER_HORIZONTAL_DIRECTION.length]);
            getLaser().get(i).setVerticalShotDirection(
                    LASER_VERTICAL_DIRECTION
                            [i%LASER_VERTICAL_DIRECTION.length]);
        }
    }

    /**
     * behaviour sets for this boss
     * @param delta the time that has passed
     * @throws SlickException
     */
    @Override
    public void setBehaviour(int delta) throws SlickException{
        setSpeed(SPEED_SEQUENCE[getBehaviourNumber()]);
        if(getBehaviourNumber() == 0){
            /**
             * moving and waiting
             */
            if (!isFinishedMove() && this.reached(getNextXCoordinate(),
                    Boss.Y_COORDINATE)){
                setTimeRecord(World.worldTime);
                setFinishedMove(true);

            } else if(!isFinishedMove()) {
                move(getNextXCoordinate(), Boss.Y_COORDINATE, delta);
            }
            /**
             * resetting values and getting to the next behaviour
             */
            if (isFinishedMove()){
                resetStatus();
                setNextXCoordinate(getRandomX());
                setTimeRecord(World.worldTime + BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]);
                setBehaviourNumber(getBehaviourNumber() + 1);
            }
        } else if (getBehaviourNumber() == 1) {
            /**
             * moving to a random x coordinate while shooting and then waiting
             */
            if (!isFinishedMove() && this.reached(getNextXCoordinate(),
                    Boss.Y_COORDINATE)) {
                setFinishedMove(true);
                setTimeRecord(World.worldTime);
            } else if (!isFinishedMove()) {
                move(getNextXCoordinate(), Boss.Y_COORDINATE, delta);
            } else if (!isFinishedWait()){
                setFinishedWait(finishedWait(
                        BEHAVIOUR_TIME_STAMP[getBehaviourNumber()]));
            }

            durationalShoot(delta);

            /**
             * resetting values and getting to the next behaviour
             */
            if (isFinishedMove() && isFinishedShoot() && isFinishedWait()) {
                setTimeSinceShot(getShootPeriod());
                resetStatus();
                setNextXCoordinate(getX());
                setBehaviourNumber(getBehaviourNumber() + 1);
            }
        } else if (getBehaviourNumber() == 2){
            /**
             * moving horizontally while shooting
             */
            if (!isFinishedMove() && this.reached(getNextXCoordinate(),
                    getMaxYCoordinate())){
                setFinishedMove(true);
            } else if (!isFinishedMove()){
                move(getNextXCoordinate(), getMaxYCoordinate(), delta);
                setTimeSinceShot(getTimeSinceShot() + delta);
                if (getTimeSinceShot() >= getShootPeriod()){
                    setTimeSinceShot(0);
                    shoot();
                }
            }

            /**
             * returning the the boss y coordinate and waiting
             */
            if (!this.reached(getNextXCoordinate(), Boss.Y_COORDINATE) &&
                    isFinishedMove()){
                move(getNextXCoordinate(), Boss.Y_COORDINATE, delta);
                if(reached(getNextXCoordinate(), Boss.Y_COORDINATE)){
                    setTimeRecord(World.worldTime);
                }
            } else if (!isFinishedWait()){
                setFinishedWait(finishedWait(BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]));
            } else if (isFinishedMove() && isFinishedWait()){
                /**
                 * resetting values and getting to the next behaviour
                 */
                resetStatus();
                setBehaviourNumber(getBehaviourNumber() + 1);
                setNextXCoordinate((App.SCREEN_WIDTH - getWidth())/2);
                setNextYCoordinate((App.SCREEN_HEIGHT - getHeight())/2);
            }
        } else if (getBehaviourNumber() == 3){
            /**
             * moving to the centre of the screen, waiting and shooting
             */
            if (!isFinishedMove() && this.reached(getNextXCoordinate(),
                    getNextYCoordinate())){
                setFinishedMove(true);
                setTimeRecord(World.worldTime);
            } else if (!isFinishedMove()){
                move(getNextXCoordinate(), getNextYCoordinate(), delta);
            } else if (!isFinishedWait()){
                setFinishedWait(finishedWait(BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]));
                if (isFinishedWait()){
                    setTimeRecord(World.worldTime + BEHAVIOUR_TIME_STAMP
                            [getBehaviourNumber()]);
                }
            }

            /**
             * shooting after waiting
             */
            if (isFinishedWait()){
                setTimeSinceShot(getTimeSinceShot() + delta);
                durationalShoot(delta);
            }

            /**
             * looping the behaviour
             */
            if (isFinishedWait() && isFinishedMove() && isFinishedShoot()){
                resetStatus();
                setNextXCoordinate(getRandomX());
                setBehaviourNumber(0);
            }
        }
    }
}
