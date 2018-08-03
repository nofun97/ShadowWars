import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

import java.math.BigDecimal;

/**
 * The type Sprite.
 * @author novan
 */
public abstract class Sprite {

    /**
     * The constant UP.
     */
    public static final int UP = -1;
    /**
     * The constant RIGHT.
     */
    public static final int RIGHT = 1;
    /**
     * The constant LEFT.
     */
    public static final int LEFT = -1;
    /**
     * The constant DOWN.
     */
    public static final int DOWN = 1;
    /**
     * The constant NO_MOVE.
     */
    public static final int NO_MOVE = 0;

    // Sprite properties
    private Image imageSource;
    private float x, y, speed;
    private boolean boundary = true;
    private BoundingBox boundingBox;
    private boolean collided = false;

    /**
     * Instantiates a new Sprite.
     *
     * @param imageSrc the image src
     * @param x        the x
     * @param y        the y
     * @throws SlickException the slick exception
     */
    public Sprite(String imageSrc, float x, float y) throws SlickException {
        // constructor for sprites other than lasers
        imageSource = new Image(imageSrc);
        this.x = x;
        this.y = y;
        boundingBox = new BoundingBox(imageSource, x, y);
    }

    /**
     * Instantiates a new Sprite.
     *
     * @param laserImage the laser image
     * @param sprite     the sprite
     * @throws SlickException the slick exception
     */
    public Sprite(String laserImage, Sprite sprite) throws SlickException {

        /**
         * constructor for laser so that it shoots out right in the middle of
         * the sprite
         */
        imageSource = new Image(laserImage);
        this.x = sprite.getX() + sprite.getWidth() / 2 - this.getWidth() / 2;
        this.y = sprite.getY() - this.getHeight();
        boundingBox = new BoundingBox(imageSource, x, y);
    }

    /**
     * Update the bounding box.
     *
     * @param input the input
     * @param delta the delta
     * @throws SlickException the slick exception
     */
    public void update(Input input, int delta) throws SlickException {

        // update location and boundingBox location
        getBoundingBox().setY(this.getY());
        getBoundingBox().setX(this.getX());

    }

    /**
     * Render the sprite.
     */
    public void render() {
        /**
         * rendering only if it is in screen and has not collided with certain
         * sprites
         */
        if (inScreen()) {

            this.imageSource.draw(x, y);
        }
    }

    /**
     * Contact sprite.
     *
     * @param other the other
     */
    public void contactSprite(Sprite other) {
        // Should two sprites collide, they will not be rendered
        collided = true;
        other.collided = true;
    }

    /**
     * Sets speed.
     *
     * @param speed the speed
     */
    public void setSpeed(float speed) {

        // setting movement speed for sprites
        if (speed > 0) {
            this.speed = speed;
        }
    }

    /**
     * Sets boundary.
     *
     * @param boundary the boundary
     */
    public void setBoundary(boolean boundary) {
        // setting whether a sprite can go through a boundary
        this.boundary = boundary;
    }

    /**
     * Update x position.
     *
     * @param direction the direction
     * @param delta     the delta
     */
    public void updateX(float direction, int delta) {
        /**
         * setting new x axis should it be within boundaries of the screen
         * should the sprite have no boundaries, it will be allowed to move
         * anywhere
         */
        BigDecimal preciseDirection = new BigDecimal(Float.toString(direction));
        BigDecimal preciseSpeed = new BigDecimal(Float.toString(speed));
        BigDecimal move = preciseDirection.multiply(preciseSpeed);
        move = move.multiply(new BigDecimal(delta));
        BigDecimal initialX = new BigDecimal(Float.toString(x));
        float updatedX = move.add(initialX).floatValue();
        if (!boundary || (updatedX >= 0 && updatedX <= App.SCREEN_WIDTH -
                imageSource.getWidth())) {
            setX(updatedX);
        }
    }

    /**
     * Update y position.
     *
     * @param direction the direction
     * @param delta     the delta
     */
    public void updateY(float direction, int delta) {
        /**
         * setting new y axis should it be within boundaries of the screen
         * should the sprite have no boundaries, it will be allowed to move
         * anywhere
         */

        BigDecimal preciseDirection = new BigDecimal(Float.toString(direction));
        BigDecimal preciseSpeed = new BigDecimal(Float.toString(speed));
        BigDecimal move = preciseDirection.multiply(preciseSpeed);
        move = move.multiply(new BigDecimal(delta));
        BigDecimal initialY = new BigDecimal(Float.toString(y));
        float updatedY = move.add(initialY).floatValue();
        if (!boundary || (updatedY >= 0 && updatedY <= App.SCREEN_HEIGHT -
                imageSource.getHeight())) {
            setY(updatedY);
        }
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public float getX() {
        // returning sprite's x axis
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public float getY() {
        // returning sprite's y axis
        return y;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public float getWidth() {
        // returning sprite's width
        return imageSource.getWidth();
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public float getHeight() {
        // returning sprite's height
        return imageSource.getHeight();
    }

    /**
     * checking if a sprite is in screen.
     *
     * @return the boolean
     */
    public boolean inScreen() {
        // checking if sprite is in the screen
        return getY() >= (-1) * getHeight() && getY() <= App.SCREEN_HEIGHT
                && getX() <= App.SCREEN_WIDTH && getX() >= (-1) * getWidth() &&
                !collided;
    }

    /**
     * Gets bounding box.
     *
     * @return the bounding box
     */
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    /**
     * Ichecking if sprite intersects with other sprite.
     *
     * @param other the other
     * @return the boolean
     */
    public boolean intersects(Sprite other) {
        if (other.collided) {
            return false;
        }
        return this.boundingBox.intersects(other.getBoundingBox());
    }

    /**
     * getting collision status of a sprite.
     *
     * @return the collided
     */
    public boolean getCollided() {
        return collided;
    }

    /**
     * Sets collided.
     *
     * @param collided the collided
     */
    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Gets image source.
     *
     * @return the image source
     */
    public Image getImageSource() {
        return imageSource;
    }

    /**
     * Sets image source.
     *
     * @param imageSource the image source
     */
    public void setImageSource(Image imageSource) {
        this.imageSource = imageSource;
    }
}
