import org.newdawn.slick.SlickException;
import java.util.*;

/**
 * The abstract class Boss to set default values for all boss.
 * @author novan
 */
public abstract class Boss extends ShootingAliens {
    /**
     * The constant Y_COORDINATE.
     */
    public static final float Y_COORDINATE = 72;
    private static final int MAX_SHOTS = Integer.MAX_VALUE;
    private static final float X_RANGE_MIN = 128;
    private static final float X_RANGE_MAX = 896;
    private int health;
    private float nextXCoordinate;
    private float nextYCoordinate;
    private float maxXCoordinate = X_RANGE_MAX;
    private float maxYCoordinate;
    private Random rand;

    /**
     * Instantiates a new Boss.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public Boss(String imageSource, float x, float y) throws SlickException {
        super(imageSource, x, y);
        setLaser(new ArrayList<>());
        setMaxShots(MAX_SHOTS);
        setY(getY() - getHeight());
        nextXCoordinate = getX();
        maxYCoordinate = App.SCREEN_WIDTH - getHeight();
        setBoundary(false);
        rand = new Random();
    }

    /**
     * should it have contact with certain sprites, it will loses its health
     * @param other the certain sprite
     */
    @Override
    public void contactSprite(Sprite other){
        super.contactSprite(other);
        if (health > 0){
            setCollided(false);
        }
        health--;
    }

    /**
     * Set boss stats.
     *
     * @param value        the value
     * @param numberOfGuns the number of guns
     * @param shotPeriod   the shot period
     * @param speed        the speed
     * @param health       the health
     */
    public void setBossStats(int value, int numberOfGuns, int shotPeriod,
                             float speed, int health){
        setValue(value);
        setNumberOfGuns(numberOfGuns);
        setShootPeriod(shotPeriod);
        setSpeed(speed);
        setHealth(health);
    }

    /**
     * Gets health.
     *
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets health.
     *
     * @param health the health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Gets next x coordinate.
     *
     * @return the next x coordinate
     */
    public float getNextXCoordinate() {
        return nextXCoordinate;
    }

    /**
     * Sets next x coordinate.
     *
     * @param nextXCoordinate the next x coordinate
     */
    public void setNextXCoordinate(float nextXCoordinate) {
        this.nextXCoordinate = nextXCoordinate;
    }

    /**
     * Gets next y coordinate.
     *
     * @return the next y coordinate
     */
    public float getNextYCoordinate() {
        return nextYCoordinate;
    }

    /**
     * Sets next y coordinate.
     *
     * @param nextYCoordinate the next y coordinate
     */
    public void setNextYCoordinate(float nextYCoordinate) {
        this.nextYCoordinate = nextYCoordinate;
    }

    /**
     * Reset all event conditions.
     */
    public void resetStatus(){
        setFinishedWait(false);
        setFinishedShoot(false);
        setFinishedMove(false);
    }

    /**
     * Gets max y coordinate.
     *
     * @return the max y coordinate
     */
    public float getMaxYCoordinate() {
        return maxYCoordinate;
    }

    /**
     * Sets max y coordinate.
     *
     * @param maxYCoordinate the max y coordinate
     */
    public void setMaxYCoordinate(float maxYCoordinate) {
        this.maxYCoordinate = maxYCoordinate;
    }

    /**
     * Get random x float.
     *
     * @return the float
     */
    public float getRandomX(){
        return rand.nextInt((int) (maxXCoordinate - X_RANGE_MIN) + 1)
                + X_RANGE_MIN - getWidth() / 2;
    }

    /**
     * Get random y float.
     *
     * @return the float
     */
    public float getRandomY(){
        return rand.nextInt((int) (maxYCoordinate - Y_COORDINATE) + 1)
                + Y_COORDINATE;
    }

    /**
     * Aggression multiplier returns the multiplier to multiply values if
     * certain health point is reached.
     *
     * @return the int the multiplier
     */
    public int aggressionMultiplier(){
        if (getHealth() < 0.25 * health) {
            return 4;
        }
        if (getHealth() < 0.5 * health) return 2;
        return 1;
    }

    /**
     * Gets max x coordinate.
     *
     * @return the max x coordinate
     */
    public float getMaxXCoordinate() {
        return maxXCoordinate;
    }

    /**
     * Sets max x coordinate.
     *
     * @param maxXCoordinate the max x coordinate
     */
    public void setMaxXCoordinate(float maxXCoordinate) {
        this.maxXCoordinate = maxXCoordinate;
    }
}
