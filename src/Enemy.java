import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * The Enemy class acts as based values for all enemies.
 * @author novan
 */
public abstract class Enemy extends ActionCharacters {
    private int value;
    private int behaviourNumber = 0;
    private boolean finishedWait = false;
    private boolean finishedMove = false;
    private boolean finishedShoot = false;
    private boolean activated = false;
    private int timeSinceShot;
    private int shootPeriod;
    private int timeActive = Integer.MAX_VALUE;

    /**
     * Instantiates a new Enemy.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public Enemy(String imageSource, float x, float y) throws SlickException {
        super(imageSource, x, y);
        setX(getX() - getWidth()/2);
        setBoundary(false);
    }

    /**
     * update the enemy
     * @param input the input
     * @param delta the time that has passed
     * @throws SlickException
     */
    @Override
    public void update(Input input, int delta) throws SlickException {
        if (!activated && World.worldTime >= getTimeActive()){
            setActive(true);
            activated = true;
        }

        if (isActive()){
            super.update(input, delta);
            setBehaviour(delta);
        }
    }


    /**
     * behaviour sets based on the behaviour number.
     *
     * @param delta the delta
     * @throws SlickException the slick exception
     */
    public abstract void setBehaviour(int delta) throws SlickException;

    /**
     * Gets behaviour number.
     *
     * @return the behaviour number
     */
    public int getBehaviourNumber() {
        return behaviourNumber;
    }

    /**
     * Sets behaviour number.
     *
     * @param behaviourNumber the behaviour number
     */
    public void setBehaviourNumber(int behaviourNumber) {
        this.behaviourNumber = behaviourNumber;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * checking finished waiting status.
     *
     * @return the boolean
     */
    public boolean isFinishedWait() {
        return finishedWait;
    }

    /**
     * sets the waiting status
     *
     * @param finishedWait the finished wait
     */
    public void setFinishedWait(boolean finishedWait) {
        this.finishedWait = finishedWait;
    }

    /**
     * getting the finished move status.
     *
     * @return the boolean
     */
    public boolean isFinishedMove() {
        return finishedMove;
    }

    /**
     * Sets finished move status.
     *
     * @param finishedMove the finished move
     */
    public void setFinishedMove(boolean finishedMove) {
        this.finishedMove = finishedMove;
    }

    /**
     * getting the finish shooting status.
     *
     * @return the boolean
     */
    public boolean isFinishedShoot() {
        return finishedShoot;
    }

    /**
     * Sets finished shoot status.
     *
     * @param finishedShoot the finished shoot
     */
    public void setFinishedShoot(boolean finishedShoot) {
        this.finishedShoot = finishedShoot;
    }

    /**
     * Gets time since shot.
     *
     * @return the time since shot
     */
    public int getTimeSinceShot() {
        return timeSinceShot;
    }

    /**
     * Sets time since shot.
     *
     * @param timeSinceShot the time since shot
     */
    public void setTimeSinceShot(int timeSinceShot) {
        this.timeSinceShot = timeSinceShot;
    }

    /**
     * Gets shoot period.
     *
     * @return the shoot period
     */
    public int getShootPeriod() {
        return shootPeriod;
    }

    /**
     * Sets shoot period.
     *
     * @param shootPeriod the shoot period
     */
    public void setShootPeriod(int shootPeriod) {
        this.shootPeriod = shootPeriod;
    }

    /**
     * Gets time active.
     *
     * @return the time active
     */
    public int getTimeActive() {
        return timeActive;
    }

    /**
     * Sets time active.
     *
     * @param timeActive the time active
     */
    public void setTimeActive(int timeActive) {
        this.timeActive = timeActive;
    }
}
