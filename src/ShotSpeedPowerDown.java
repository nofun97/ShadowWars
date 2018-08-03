import org.newdawn.slick.SlickException;

/**
 * The type Shot speed power down.
 * @author novan
 */
public class ShotSpeedPowerDown extends PowerUps {
    private static String imageSource =
            "assets/PowerUps/shotspeed-powerdown.png";
    private static final int PLAYER_NORMAL_SHOT_SPEED = 350;
    private static final int PLAYER_HIGH_SHOT_SPEED = 1000;

    /**
     * Instantiates a new Shot speed power down.
     *
     * @param x the x
     * @param y the y
     * @throws SlickException the slick exception
     */
    public ShotSpeedPowerDown (float x, float y) throws SlickException {
        super(imageSource, x, y);
    }

    /**
     * extending the the player shot period
     * @param player the player
     */
    @Override
    public void addPower(Player player) {
        if(World.worldTime <= getTimeLimit()){
            player.setTimePerShot(PLAYER_HIGH_SHOT_SPEED);
        } else {
            player.setTimePerShot(PLAYER_NORMAL_SHOT_SPEED);
            setActive(false);
        }
    }
}

