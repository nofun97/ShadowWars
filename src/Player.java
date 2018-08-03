import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * The type Player.
 * @author novan
 */
public class Player extends ActionCharacters {
    private static final int SHIELD_UP_DURATION = 3000;
    private static final float LIVES_DELTA = 40;
    /**
     * The constant STUN_DURATION.
     */
    public static final int STUN_DURATION = 2000;
    private static final float[] LASER_OFFSET_X_VALUES = new
            float[]{0, -21, 21};
    private static final float[] LASER_OFFSET_Y_VALUES = new float[]{0, 13, 13};
    /**
     * values for the player
     */
    private int lives = 3;
    private int numberOfGuns = 1;
    private Image livesImage = new Image("assets/lives.png");
    private int score = 0;
    private float LASER_SPEED = 3;
    private int timePerShot = 350;
    private int timeSinceShot = timePerShot;
    private float livesX = 20;
    private float livesY = 696;
    /**
     * dead condition
     */
    private boolean dead = false;
    private float speed = (float) 0.5;
    private boolean shieldUp = false;
    private static Image shieldImage;
    private boolean justDied = false;
    private int shieldUpLimit;
    private Graphics scoreImage;
    private boolean stunned = false;
    /**
     * powered up and regular sprites
     */
    private Image powerShip = new Image("/assets/PowerShip.png");
    private Image regularShip = new Image("/assets/spaceship.png");
    private boolean shipPowerUp = false;

    /**
     * Instantiates a new Player.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public Player(String imageSource, float x, float y) throws SlickException {
        super(imageSource, x, y);
        setShooter(true);
        setActive(true);
        setLaser(new ArrayList<>());
        setShotDirection(UP);
        setLaserSpeed(LASER_SPEED);
        setLaserImagePath("assets/shot.png");
        setSpeed(speed);
        scoreImage = new Graphics(World.MESSAGE_SIZE, World.MESSAGE_SIZE);
        shieldImage = new Image("assets/shield.png");
        setNumberOfGuns(numberOfGuns);
    }

    @Override
    public void update(Input input, int delta) throws SlickException {
        // to handle overflow, it resets the value
        if (timeSinceShot >= Integer.MAX_VALUE) {
            timeSinceShot = timePerShot;
        }
        // updating the time since shot
        timeSinceShot += delta;

        if (justDied && World.worldTime <= shieldUpLimit){
            shieldUp = true;
        } else if (justDied){
            justDied = false;
            shieldUp = false;
        }
        /**
         * getting input and moving the sprite to appropriate locations or doing
         * the specified action if it is not stunned
         */
        if (!getCollided() && !isStunned()) {
            if (input.isKeyDown(Input.KEY_UP)) {
                updateY(UP, delta);
            }

            if (input.isKeyDown(Input.KEY_DOWN)) {
                updateY(DOWN, delta);
            }

            if (input.isKeyDown(Input.KEY_LEFT)) {
                updateX(LEFT, delta);
            }

            if (input.isKeyDown(Input.KEY_RIGHT)) {
                updateX(RIGHT, delta);
            }

            if (input.isKeyPressed(Input.KEY_SPACE)) {
                if (timeSinceShot >= timePerShot) {
                    timeSinceShot = 0;
                    shoot();
                }
            }
        }

        // laser and boundingbox updating
        super.update(input, delta);
    }

    /**
     * render the player
     */
    @Override
    public void render() {
        /**
         * rendering powered up ship
         */
        if (shipPowerUp){
            this.setImageSource(powerShip);
        } else {
            this.setImageSource(regularShip);
        }

        super.render();
        /**
         * rendering the lives image
         */
        for (int i = 0; i < lives; i++) {
            livesImage.draw(livesX + i * LIVES_DELTA, livesY);
        }
        scoreImage.drawString(Integer.toString(getScore()), 20,738);

        /**
         * if player is dead remove shield
         */
        if (dead) {
            shieldUp = false;
        }

        /**
         * rendering the shield
         */
        if (shieldUp){
            float playerCenterX = getX() + getWidth()/2;
            float playerCenterY = getY() + getHeight()/2;
            float shieldXCoordinate = playerCenterX - shieldImage.getWidth()/2;
            float shieldYCoordinate = playerCenterY - shieldImage.getHeight()/2;
            shieldImage.draw(shieldXCoordinate, shieldYCoordinate);
        }
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * actions on contact sprite
     * @param other the other sprite
     */
    @Override
    public void contactSprite(Sprite other) {

        super.contactSprite(other);
        other.contactSprite(this);
        this.setCollided(false);

        /**
         * removing lives if shield is not up
         */
        if(!isShieldUp()){
            this.lives--;
            if(lives > 0){
                justDied = true;
                shieldUpLimit = World.worldTime + SHIELD_UP_DURATION;
            }
        }

        if (lives <= 0) {
            this.setCollided(true);
            dead = true;
        }
    }

    /**
     * Is shield up boolean.
     *
     * @return the boolean
     */
    public boolean isShieldUp() {
        return shieldUp;
    }

    /**
     * Sets shield up.
     *
     * @param shieldUp the shield up
     */
    public void setShieldUp(boolean shieldUp) {
        this.shieldUp = shieldUp;
    }

    /**
     * Gets time per shot.
     *
     * @return the time per shot
     */
    public int getTimePerShot() {
        return timePerShot;
    }

    /**
     * Sets time per shot.
     *
     * @param timePerShot the time per shot
     */
    public void setTimePerShot(int timePerShot) {
        this.timePerShot = timePerShot;
    }

    /**
     * Is dead boolean.
     *
     * @return the boolean
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Is stunned boolean.
     *
     * @return the boolean
     */
    public boolean isStunned() {
        return stunned;
    }

    /**
     * Sets stunned.
     *
     * @param stunned the stunned
     */
    public void setStunned(boolean stunned) {

        this.stunned = stunned;
    }

    @Override
    public void shoot() throws SlickException {
        super.shoot();
        /**
         * offsetting the laser
         */
        for (int i = getShotCounter() - getNumberOfGuns(); i < getShotCounter();
             i++){
            getLaser().get(i).offsetY(LASER_OFFSET_Y_VALUES
                    [i%LASER_OFFSET_Y_VALUES.length]);
            getLaser().get(i).offsetX(LASER_OFFSET_X_VALUES[
                    i%LASER_OFFSET_X_VALUES.length]);
        }
    }

    /**
     * Sets ship power up.
     *
     * @param shipPowerUp the ship power up
     */
    public void setShipPowerUp(boolean shipPowerUp) {
        this.shipPowerUp = shipPowerUp;
    }


}
