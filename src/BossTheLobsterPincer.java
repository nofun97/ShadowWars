import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.Random;

/**
 * The type Boss the lobster pincer. Part of the lobster boss
 * @author novan
 */
public class BossTheLobsterPincer extends Boss {
    /**
     * The constant LEFT_PINCER.
     */
    public static final int LEFT_PINCER = 1;
    /**
     * The constant RIGHT_PINCER.
     */
    public static final int RIGHT_PINCER = -1;
    private static final int SHOT_PERIOD = 100;
    private static final int NUMBER_OF_GUNS = 1;
    private static final int VALUE = 2000;
    private static final int[] BEHAVIOUR_TIME_STAMP = new int[]
            {3000, 2000, 3000};
    private static final float[] SPEED_SEQUENCE = new float[]
            {(float) 0.05, (float) 0.2, (float) 0.1};
    private static final float LASER_X_OFFSET_VALUES = 11;
    private static final float LASER_Y_OFFSET_VALUES = -35;
    private static final int SHOT_DURATION = 5000;
    /**
     * The constant headDistance.
     */
    public static final int headDistance = 30;
    private BossTheLobster head;
    private int type;
    private float maxXCoordinate;
    private float minXCoordinate;
    private int health = 75;
    private Random rand;

    /**
     * sine movement values
     */
    private int amplitude = 100;
    private int period = 1000;
    private int delay = 1;
    private int time = 0;
    private float middleX;
    private boolean activateLaserbeam = false;

    /**
     * Instantiates a new Boss the lobster pincer.
     *
     * @param imageSource the image source
     * @param head        the head
     * @param type        the type
     * @throws SlickException the slick exception
     */
    public BossTheLobsterPincer(String imageSource, BossTheLobster head,
                                int type) throws SlickException {
        super(imageSource, head.getX(), head.getY());
        this.type = type;
        maxXCoordinate = App.SCREEN_WIDTH - this.getWidth();
        this.head = head;
        minXCoordinate = 0;

        /**
         * setting its location and max x coordinate based on the type
         */
        if (type == LEFT_PINCER){
            this.setX(this.getX() + head.getWidth() + 2*headDistance);
            minXCoordinate = getX();
        } else if (type == RIGHT_PINCER){
            this.setX(this.getX() - this.getWidth()/4 - headDistance);
            maxXCoordinate = getX();
        }
        setBossStats(VALUE, NUMBER_OF_GUNS, SHOT_PERIOD, SPEED_SEQUENCE[0],
                health);
        rand = new Random();
        middleX = (minXCoordinate + maxXCoordinate)/2;
        setNextXCoordinate(getX());
    }

    /**
     * update method for this boss
     * @param input
     * @param delta
     * @throws SlickException
     */
    @Override
    public void update(Input input, int delta) throws SlickException {
        /**
         * activates once the lobster head is active
         */
        setActive(head.isActive());
        super.update(input, delta);
    }

    @Override
    public void setBehaviour(int delta) throws SlickException {
        setSpeed(aggressionMultiplier() * SPEED_SEQUENCE[getBehaviourNumber()]);

        if (getBehaviourNumber() == 0){
            /**
             * moving to a point and waiting for a certain amount of time
             */
            if (!isFinishedMove() && this.reached(getNextXCoordinate(),
                    Boss.Y_COORDINATE)){
                setTimeRecord(World.worldTime);
                setFinishedMove(true);

            } else if (!isFinishedMove()) {
                move(getNextXCoordinate(), Boss.Y_COORDINATE, delta);
            } else if (!isFinishedWait()){
                setFinishedWait(finishedWait(BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]));
            }
            /**
             * getting to the next behaviour
             */
            if (isFinishedWait() && isFinishedMove()){
                resetStatus();
                setNextXCoordinate(getRandomX());
                setBehaviourNumber(getBehaviourNumber() + 1);
            }
        } else if (getBehaviourNumber() == 1){
            /**
             * moving to a random x coordinate and waiting
             */
            if (!isFinishedMove() && !this.reached(getNextXCoordinate(),
                    Boss.Y_COORDINATE)){
                move(getNextXCoordinate(), Boss.Y_COORDINATE, delta);
                setFinishedMove(reached(getNextXCoordinate(),
                        Boss.Y_COORDINATE));
                if (isFinishedMove()){
                    setTimeRecord(World.worldTime);
                }
            } else if (isFinishedMove() && !isFinishedWait()){
                setFinishedWait(finishedWait(BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]));
                if (isFinishedWait()){
                    setTimeRecord(World.worldTime + SHOT_DURATION);
                }
            }

            /**
             * shooting the laser beam for a certain amount of time
             */
            if (isFinishedWait() && isFinishedMove()){
                activateLaserbeam = true;
                durationalShoot(delta);
            }

            /**
             * getting to the next behaviour
             */
            if (isFinishedWait() && isFinishedShoot() && isFinishedMove()){
                resetStatus();
                activateLaserbeam = false;
                setBehaviourNumber(getBehaviourNumber() + 1);
                setNextXCoordinate(middleX);
                setNextYCoordinate(Boss.Y_COORDINATE);
            }
        } else if (getBehaviourNumber() == 2){
            /**
             * moving to the middle and shooting in a sine wave
             */
            if (!this.reached(getNextXCoordinate(), getNextYCoordinate())){
                move(getNextXCoordinate(), getNextYCoordinate(), delta);
                setFinishedMove(reached(getNextXCoordinate(),
                        getNextYCoordinate()));
                if (isFinishedMove()){
                    setTimeRecord(World.worldTime);
                }
            } else if (isFinishedMove()){
                setFinishedWait(finishedWait(BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]));
                if (isFinishedWait()){
                    setTimeRecord(World.worldTime + SHOT_DURATION);
                }
            }

            /**
             * shooting in a sine wave
             */
            if (isFinishedWait()){
                if (World.worldTime < getTimeRecord()){
                    shoot();
                    moveSine(delta);
                } else {
                    setFinishedShoot(true);
                    time = 0;
                }
            }

            /**
             * looping the behaviour
             */
            if (isFinishedWait() && isFinishedMove() && isFinishedShoot()){
                resetStatus();
                setBehaviourNumber(0);
                setNextXCoordinate(getRandomX());
            }
        }

    }

    /**
     * shooting method for this boss
     * @throws SlickException
     */
    @Override
    public void shoot() throws SlickException {
        super.shoot();
        /**
         * offsets the laser
         */
        for (int i = getShotCounter() - NUMBER_OF_GUNS; i < getShotCounter();
             i++){
            /**
             * activates laser beam
             */
            if (activateLaserbeam){
                getLaser().get(i).setImageSource(new Image(LASERBEAM_PATH));
            }
            getLaser().get(i).offsetX(type * LASER_X_OFFSET_VALUES);
            getLaser().get(i).offsetY(LASER_Y_OFFSET_VALUES);
        }
    }

    /**
     * getting random x coordinate bsaed on certain range
     * @return
     */
    @Override
    public float getRandomX() {
        return rand.nextInt((int) (maxXCoordinate - minXCoordinate) + 1)
                + minXCoordinate;
    }

    /**
     * Move in a sine wave.
     *
     * @param delta the delta
     */
    public void moveSine(int delta){
        time += delta;
        float offsetX = (float) (type * (amplitude * Math.sin((float) 2 *
                Math.PI/period * (time - delay))));
        setX(middleX + offsetX);
        updateY(DOWN, delta);
    }
}
