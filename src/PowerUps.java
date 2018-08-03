import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import java.util.*;

/**
 * The Power ups class.
 * @author novan
 */
public abstract class PowerUps extends Sprite{
    /**
     * setting based values
     */
    private static final int DURATION = 5000;
    private static float speed = (float) 0.1;
    private static final int POWER_UP_TYPE = 4;
    private static final int POWER_DOWN_TYPE = 2;
    private int timeLimit;
    private boolean active = false;


    /**
     * Instantiates a new Power ups.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public PowerUps (String imageSource, float x, float y)
            throws SlickException {
        super(imageSource, x, y);
        setSpeed(speed);
        setBoundary(false);
    }

    @Override
    public void update(Input input, int delta) throws SlickException {
        super.update(input, delta);
        updateY(DOWN, delta);
    }

    /**
     * on contact with player
     *
     * @param player the player
     */
    public void contactSprite(Player player){
        this.setCollided(true);
        active = true;
        timeLimit = World.worldTime + DURATION;
    }

    /**
     * Add power.
     *
     * @param player the player
     */
    public abstract void addPower(Player player);

    /**
     * Spawn power up power ups.
     *
     * @param alien the alien
     * @return the power ups
     * @throws SlickException the slick exception
     */
    public static PowerUps spawnPowerUp(Sprite alien) throws SlickException{
        Random rand = new Random();
        int value = rand.nextInt();
        value = value%POWER_UP_TYPE;
        if(value == 0){
            return new ShieldPowerUp(alien.getX(), alien.getY());
        } else if (value == 1){
            return new ShotSpeedPowerUp(alien.getX(), alien.getY());
        } else if (value == 2){
            return  new ShipPowerUp(alien.getX(), alien.getY());
        } else {
            return new SpeedUpPowerUp(alien.getX(), alien.getY());
        }
    }

    /**
     * Spawn reversed power ups.
     *
     * @param alien the alien
     * @return the power ups
     * @throws SlickException the slick exception
     */
    public static PowerUps spawnPowerDown(Sprite alien) throws SlickException{
        Random rand = new Random();
        int value = rand.nextInt();
        value = value%POWER_DOWN_TYPE;
        if (value == 0) {
            return new ShotSpeedPowerDown(alien.getX(), alien.getY());
        } else {
            return new SpeedDownPowerDown(alien.getX(), alien.getY());
        }

    }

    /**
     * Gets time limit.
     *
     * @return the time limit
     */
    public int getTimeLimit() {
        return timeLimit;
    }

    /**
     * Sets time limit.
     *
     * @param timeLimit the time limit
     */
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    /**
     * Is active boolean.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(boolean active) {
        this.active = active;
    }


}
