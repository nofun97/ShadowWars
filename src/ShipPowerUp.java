import org.newdawn.slick.SlickException;

/**
 * The type Ship power up.
 * @author novan
 */
public class ShipPowerUp extends PowerUps {
    private static String imageSource = "assets/PowerUps/ship-powerup.png";
    private static final int PLAYER_NORMAL_GUNS = 1;
    private static final int PLAYER_POWER_GUNS = 3;

    /**
     * Instantiates a new Ship power up.
     *
     * @param x the x
     * @param y the y
     * @throws SlickException the slick exception
     */
    public ShipPowerUp(float x, float y) throws SlickException {
        super(imageSource, x, y);
    }

    /**
     * upgrades the ship
     * @param player the player
     */
    @Override
    public void addPower(Player player) {
        if(World.worldTime <= getTimeLimit()){
            player.setNumberOfGuns(PLAYER_POWER_GUNS);
            player.setShipPowerUp(true);
        } else {
            player.setNumberOfGuns(PLAYER_NORMAL_GUNS);
            player.setShipPowerUp(false);
            setActive(false);
        }
    }
}
