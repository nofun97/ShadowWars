import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.Random;

/**
 * The type first boss.
 * @author novan
 */
public class BossOne extends Boss {
    /**
     * default values for this boss
     */
    private static final int SHOT_PERIOD = 200;
    private static final int NUMBER_OF_GUNS = 4;
    private static final int VALUE = 5000;
    private int health = 60;

    /**
     * behaviour time and speed sequences
     */
    private static final int[] BEHAVIOUR_TIME_STAMP = new int[]
            {5000, 2000, 3000};
    private static final float[] SPEED_SEQUENCE = new float[]
            {(float) 0.05, (float) 0.2, (float) 0.1};
    /**
     * laser offset values
     */
    private static final float[] LASER_OFFSET_VALUES = new float[]
            {-97, -74, 74, 97};
    private Random rand = new Random();

    /**
     * Instantiates a new regular boss.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public BossOne(String imageSource, float x, float y) throws SlickException {
        super(imageSource, x, y);
        setBossStats(VALUE, NUMBER_OF_GUNS, SHOT_PERIOD, SPEED_SEQUENCE[0],
                health);
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
             * offsetting the lasers
             */
            getLaser().get(i).offsetX(LASER_OFFSET_VALUES
                    [i%LASER_OFFSET_VALUES.length]);
        }
    }

    /**
     * doing the behaviour based on the behaviour number
     * @param delta time that has passed
     * @throws SlickException
     */
    @Override
    public void setBehaviour(int delta) throws SlickException{
        setSpeed(SPEED_SEQUENCE[getBehaviourNumber()]);
        if(getBehaviourNumber() <= 1){
            /**
             * moving to a position and waiting a certain time
             */
            if (!isFinishedMove() && this.reached(getNextXCoordinate(),
                    Boss.Y_COORDINATE)){
                setTimeRecord(World.worldTime);
                setFinishedMove(true);

            } else if(!isFinishedMove()){
                move(getNextXCoordinate(), Boss.Y_COORDINATE, delta);

            } else if (!isFinishedWait()){
                setFinishedWait( finishedWait(BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]));

            }

            /**
             * resetting the values and getting to the next behaviour
             */
            if (isFinishedWait()){
                resetStatus();
                setNextXCoordinate(getRandomX());
                if (getBehaviourNumber() == 1){
                    setTimeRecord(World.worldTime + BEHAVIOUR_TIME_STAMP
                            [getBehaviourNumber() + 1]);
                }
                setBehaviourNumber(getBehaviourNumber() + 1);
            }
        } else if (getBehaviourNumber() == 2) {
            /**
             * moving and shooting for a certain amount of time
             */
            if (!isFinishedMove() && this.reached(getNextXCoordinate(),
                    Boss.Y_COORDINATE)){
                setFinishedMove(true);

            } else if(!isFinishedMove()){
                move(getNextXCoordinate(), Boss.Y_COORDINATE, delta);
            }

            durationalShoot(delta);

            /**
             * looping the behaviour
             */
            if (isFinishedMove() && isFinishedShoot()){
                setTimeSinceShot(getShootPeriod());
                resetStatus();
                setNextXCoordinate(getX());
                setBehaviourNumber(0);
            }
        }
    }
}
