import org.newdawn.slick.SlickException;

/**
 * The Kamikaze boss.
 * @author novan
 */
public class BossKamikaze extends Boss {
    /**
     * default values for this boss
     */
    private static final int SHOT_PERIOD = 100;
    private static final int NUMBER_OF_GUNS = 4;
    private static final int VALUE = 6000;
    private int health = 80;
    /**
     * behaviour time and speed for each behaviour
     */
    private static final int[] BEHAVIOUR_TIME_STAMP =
            new int[]{5000, 2000, 3000};
    private static final float[] SPEED_SEQUENCE = new float[]{(float) 0.05,
            (float) 0.25, (float) 0.2, (float) 2, (float) 0.2};

    /**
     * laser offset values
     */
    private static final float[] LASER_X_OFFSET_VALUES =
            new float[]{-82, -47, 47, 82};
    private static final float[] LASER_Y_OFFSET_VALUES =
            new float[]{-45, -15, -15, -45};

    /**
     * the duration of the boss for following player location
     */
    private static final int PLAYER_FOLLOW_DURATION = 3000;

    /**
     * how many times it can lunge itself to player
     */
    private static final int MAX_ADVANCE_TIMES = 3;
    private int advanceTimes = 0;
    private Player player;

    /**
     * Instantiates a new Boss kamikaze.
     *
     * @param imageSource the image source
     * @param x           the x coordinate
     * @param y           the y coordinte
     * @param player      the player
     * @throws SlickException the slick exception
     */
    public BossKamikaze(String imageSource, float x, float y, Player player)
            throws SlickException {
        super(imageSource, x, y);
        this.player = player;
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
             * offset the lasers
             */
            getLaser().get(i).offsetX(LASER_X_OFFSET_VALUES
                    [i%LASER_X_OFFSET_VALUES.length]);
            getLaser().get(i).offsetY(LASER_Y_OFFSET_VALUES
                    [i%LASER_Y_OFFSET_VALUES.length]);
        }
    }

    /**
     * behaviour for this boss
     * @param delta the time that has passed
     * @throws SlickException
     */
    @Override
    public void setBehaviour(int delta) throws SlickException {
        /**
         * set the appropriate speed
         */
        setSpeed(SPEED_SEQUENCE[getBehaviourNumber()]);
        if (getBehaviourNumber() == 0) {
            /**
             * moving to a point and waiting for a certain amount of time
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
             * getting to the next behaviour
             */
            if (isFinishedWait()) {
                resetStatus();
                setNextXCoordinate(getRandomX());
                setTimeRecord(World.worldTime + BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]);
                setBehaviourNumber(getBehaviourNumber() + 1);
            }
        }  else if (getBehaviourNumber() == 1){

            /**
             * moving to a random point while shooting
             */
            if (!isFinishedMove() && this.reached(getNextXCoordinate(),
                    Boss.Y_COORDINATE)){
                setFinishedMove(true);

            } else if(!isFinishedMove()){
                move(getNextXCoordinate(), Boss.Y_COORDINATE, delta);
            }

            durationalShoot(delta);

            /**
             * getting to the next behaviour should it finish this behaviour
             */
            if (isFinishedMove() && isFinishedShoot()){
                setTimeSinceShot(getShootPeriod());
                resetStatus();
                setNextXCoordinate(player.getX());
                setNextYCoordinate(Boss.Y_COORDINATE);
                setBehaviourNumber(getBehaviourNumber() + 1);
                setTimeRecord(World.worldTime);
            }
        } else if (getBehaviourNumber() == 2) {
            /**
             * waiting for a certain amount of time
             */
            if(!isFinishedWait()){
                setFinishedWait(finishedWait(BEHAVIOUR_TIME_STAMP
                        [getBehaviourNumber()]));
                if (isFinishedWait()){
                    /**
                     * setting the time for the boss to follow a player
                     */
                    setTimeRecord(World.worldTime + PLAYER_FOLLOW_DURATION);
                }
            } else if (!isFinishedMove() && World.worldTime < getTimeRecord()){
                /**
                 * moving to a certain x coordinate and the prepare to lunge
                 */
                move(getNextXCoordinate(), Boss.Y_COORDINATE, delta);
                setFinishedMove(this.reached(getNextXCoordinate(),
                        Boss.Y_COORDINATE));
                if (isFinishedMove()){
                    setNextYCoordinate(getMaxYCoordinate());
                }
            } else if (isFinishedMove()){

                /**
                 * trying to hit the player certain times
                 */
                if (advanceTimes < MAX_ADVANCE_TIMES){
                    /**
                     * setting the hitting speed
                     */
                    setSpeed(SPEED_SEQUENCE[getBehaviourNumber() + 1]);

                    /**
                     * setting the returning speed
                     */
                    if(getNextYCoordinate() == Boss.Y_COORDINATE){
                        setSpeed(SPEED_SEQUENCE[getBehaviourNumber() + 2]);
                    }

                    /**
                     * moving
                     */
                    move(getNextXCoordinate(), getNextYCoordinate(), delta);

                    /**
                     * trying to hit the player and returning
                     */
                    if (reached(getX(), getMaxYCoordinate())){
                        advanceTimes++;
                        setNextYCoordinate(Boss.Y_COORDINATE);
                    } else if (reached(getX(), Boss.Y_COORDINATE)){
                        setNextXCoordinate(player.getX());
                        setTimeRecord(getTimeRecord() + PLAYER_FOLLOW_DURATION);
                        setFinishedMove(false);
                    }
                } else {
                    /**
                     * resetting the shooting times
                     */
                    advanceTimes = 0;
                    setFinishedShoot(true);
                }

            } else if (World.worldTime >= getTimeRecord()){
                /**
                 * should it finishes following player, it gets ready to hit
                 * the player
                 */
                setNextYCoordinate(getMaxYCoordinate());
                setFinishedMove(true);
            }

            /**
             * resetting the behaviours
             */
            if(isFinishedWait() && isFinishedMove() && isFinishedShoot()){
                resetStatus();
                setBehaviourNumber(0);
            }
        }
    }
}
