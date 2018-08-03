import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import java.util.Random;

/**
 * The type Boss The Lobster.
 * @author novan
 */
public class BossTheLobster extends Boss{
    /**
     * behaviour, speed sequences, and laser offset values
     */
    private static final int[] BEHAVIOUR_TIME_STAMP = new int[]
            {3000, 4000, 3000};
    private static final float[] SPEED_SEQUENCE = new float[]
            {(float) 0.05, (float) 0.2, (float) 0.2};
    private static final float[] LASER_X_OFFSET_VALUES = new float[]{-25, 25};
    private static final float LASER_Y_OFFSET_VALUES = -9;

    /**
     * default values
     */
    private static final int NUMBER_OF_GUNS = 2;
    private static final int VALUE = 8000;
    private static final int SHOT_PERIOD = 300;
    private static final float LASER_SPEED = (float) 0.3;
    private static final int SHOT_DURATION = 5000;
    private int health = 60;

    /**
     * maximum x coodinate
     */
    private static float minXCoordinate = (float) 0.25 * App.SCREEN_WIDTH;
    private static float maxXCoordinate = (float) 0.75 * App.SCREEN_WIDTH;
    private static final float MAX_Y_OFFSET = -400;
    private static final int POWER_DOWN_SHOT_PERIOD = 500;
    private float middleX;
    private int time = 0;
    /**
     * sine movement values
     */
    private int amplitude = 150;
    private int period = 1000;
    private int delay = 1;
    private Random rand;
    private int multiplier = 1;
    private World world;

    /**
     * Instantiates a new Boss the lobster.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @param world       the world
     * @throws SlickException the slick exception
     */
    public BossTheLobster(String imageSource, float x, float y, World world)
    throws SlickException {
        super(imageSource, x, y);
        this.world = world;
        setBossStats(VALUE, NUMBER_OF_GUNS, SHOT_PERIOD, SPEED_SEQUENCE[0],
                health);
        setNextXCoordinate(getX());
        middleX = (minXCoordinate + maxXCoordinate)/2;
        setMaxYCoordinate(getMaxYCoordinate() + MAX_Y_OFFSET);
        setLaserSpeed(LASER_SPEED);
        rand = new Random();
        setTimeSinceShot(POWER_DOWN_SHOT_PERIOD);
    }

    /**
     * behaviour sets for this boss
     * @param delta time that has passed
     * @throws SlickException
     */
    @Override
    public void setBehaviour(int delta) throws SlickException {
        setSpeed(aggressionMultiplier() * SPEED_SEQUENCE[getBehaviourNumber()]);

        if (getBehaviourNumber() == 0){
            /**
             * moving to a certain point and then waiting
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
             * getting the next behaviour
             */
            if (isFinishedWait() && isFinishedMove()){
                resetStatus();
                setNextXCoordinate(middleX);
                setNextYCoordinate(getRandomY());
                setBehaviourNumber(getBehaviourNumber() + 1);
            }
        } else if (getBehaviourNumber() == 1){
            /**
             * moving to a certain point and then waiting
             */
            if (!isFinishedMove() & !this.reached(getNextXCoordinate(),
                    getNextYCoordinate())){
                move(getNextXCoordinate(), getNextYCoordinate(), delta);
                setFinishedMove(this.reached(getNextXCoordinate(),
                        getNextYCoordinate()));
                if (isFinishedMove()){
                    setTimeRecord(World.worldTime);
                }
            } else if (isFinishedMove() && !isFinishedWait()){
                setFinishedWait(finishedWait(BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]));
                if(isFinishedWait()){
                    setTimeRecord(World.worldTime + SHOT_DURATION);
                }
            }

            /**
             * shooting a certain amount of time while moving in a sine wave
             */
            if (isFinishedWait()){
                if (World.worldTime < getTimeRecord()){
                    shoot();
                    moveSine(delta);
                } else {
                    setFinishedShoot(true);
                }
            }

            /**
             * getting the next behaviour
             */
            if (isFinishedWait() && isFinishedMove() && isFinishedShoot()){
                resetStatus();
                setBehaviourNumber(getBehaviourNumber() + 1);
                setNextYCoordinate(Boss.Y_COORDINATE);
            }
        } else if (getBehaviourNumber() == 2){
            /**
             * moving and waiting in another position
             */
            if (!isFinishedMove() & !this.reached(getNextXCoordinate(),
                    getNextYCoordinate())){
                move(getNextXCoordinate(), getNextYCoordinate(), delta);
                setFinishedMove(this.reached(getNextXCoordinate(),
                        getNextYCoordinate()));
                if (isFinishedMove()){
                    setTimeRecord(World.worldTime);
                }
            } else if (isFinishedMove() && !isFinishedWait()){
                setFinishedWait(finishedWait(BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]));
                if(isFinishedWait()){
                    setTimeRecord(World.worldTime + SHOT_DURATION);
                }
            }

            /**
             * shooting reversed version of power ups while moving in a sine
             * wave for a certain amount of time
             */
            if (isFinishedWait()){
                setTimeSinceShot(getTimeSinceShot() + delta);
                if (World.worldTime < getTimeRecord() && getTimeSinceShot() >=
                        POWER_DOWN_SHOT_PERIOD){
                    setTimeSinceShot(0);
                    shootPowerDown();
                } else if (World.worldTime > getTimeRecord()){
                    setFinishedShoot(true);
                }
                moveSine(delta);
            }

            /**
             * looping the behaviour
             */
            if (isFinishedShoot() && isFinishedMove() && isFinishedWait()){
                resetStatus();
                setNextXCoordinate(getRandomX());
                setBehaviourNumber(0);
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
        for (int i = getShotCounter() - NUMBER_OF_GUNS; i < getShotCounter();
             i++){
            /**
             * offsets the laser
             */
            getLaser().get(i).offsetX(LASER_X_OFFSET_VALUES
                    [i%LASER_X_OFFSET_VALUES.length]);
            getLaser().get(i).offsetY(LASER_Y_OFFSET_VALUES);
        }
    }

    /**
     * Move in a sine wave.
     *
     * @param delta the delta
     */
    public void moveSine(int delta){
        time += delta;
        float offsetX = (float) (multiplier * amplitude * Math.sin((float) 2 *
                Math.PI/period * (time - delay)));
        setX(middleX + offsetX);
        if (getY() < getMaxYCoordinate()){

            updateY(DOWN, delta);
        }
    }

    /**
     * getting a random x within certain range
     * @return
     */
    @Override
    public float getRandomX() {
        return rand.nextInt((int) (maxXCoordinate - minXCoordinate) + 1)
                + minXCoordinate;
    }

    /**
     * Get multiplier for the sine wave movement.
     *
     * @return the int
     */
    public int getMultiplier(){
        return rand.nextInt()%2 == 1 ? -1 : 1;
    }

    /**
     * Shoots reversed version of powerups.
     *
     * @throws SlickException the slick exception
     */
    public void shootPowerDown() throws SlickException {
        /**
         * generates the powerDown
         */
        for (int i = 0; i < NUMBER_OF_GUNS; i++){
            world.getPowerUps().add(PowerUps.spawnPowerDown(this));
        }
        /**
         * offseting the location
         */
        for (int i = world.getPowerUps().size() - NUMBER_OF_GUNS;
             i < world.getPowerUps().size(); i++){
            PowerUps currentPowerDown = world.getPowerUps().get
                    (world.getPowerUps().size() - 1);
            currentPowerDown.setX(this.getX() + this.getWidth()/2 -
                    currentPowerDown.getWidth()/2);
            currentPowerDown.setX(currentPowerDown.getX() +
                    LASER_X_OFFSET_VALUES[i%LASER_X_OFFSET_VALUES.length]);
            currentPowerDown.setY(currentPowerDown.getY() + this.getHeight());
        }
    }
}
