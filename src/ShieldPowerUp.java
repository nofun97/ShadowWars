import org.newdawn.slick.SlickException;

/**
 * The type Shield power up.
 * @author novan
 */
public class ShieldPowerUp extends PowerUps {
    private static String imageSource = "/assets/PowerUps/shield-powerup.png";

    /**
     * Instantiates a new Shield power up.
     *
     * @param x the x
     * @param y the y
     * @throws SlickException the slick exception
     */
    public ShieldPowerUp (float x, float y) throws SlickException {
        super(imageSource, x, y);
    }

    /**
     * adding shield
     * @param player the player
     */
    @Override
    public void addPower(Player player) {
        if(World.worldTime <= getTimeLimit()){
            player.setShieldUp(true);
        } else {
            player.setShieldUp(false);
            setActive(false);
        }
    }
}
