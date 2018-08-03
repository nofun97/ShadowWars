import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * The laser for all shooters.
 * @author novan
 */
public class Laser extends Sprite{
    private float speed = 3;
    private int verticalShotDirection;
    private int horizontalShotDirection = NO_MOVE;
    private boolean active = true;

    /**
     * Instantiates a new Laser.
     *
     * @param imageSource           the image source
     * @param character             the character
     * @param verticalShotDirection the vertical shot direction
     * @throws SlickException the slick exception
     */
    public Laser(String imageSource, Sprite character, int verticalShotDirection) throws SlickException{
        super(imageSource, character);

        // setting the lasers direction
        this.verticalShotDirection = verticalShotDirection;
        // laser can move out of the screen
        setBoundary(false);

        // setting the speed
        setSpeed(speed);
    }

    /**
     * updating if the laser is active
     * @param input the input
     * @param delta the time that has passed
     * @throws SlickException
     */
    @Override
    public void update(Input input, int delta) throws SlickException {
        // moving the laser and the boundingBox

        if(isActive()){
            super.update(input, delta);
            updateY(verticalShotDirection, delta);
            updateX(horizontalShotDirection, delta);
        }
    }

    /**
     * rendering if the laser is active
     */
    @Override
    public void render() {
        if(isActive()){
            super.render();
        }
    }

    /**
     * Offsets x coordinate.
     *
     * @param offset the offset
     */
    public void offsetX(float offset){
        setX(getX() + offset);
    }

    /**
     * Offsets y coordinate.
     *
     * @param offset the offset
     */
    public void offsetY(float offset){
        setY(getY() + offset);
    }

    /**
     * Gets horizontal shot direction.
     *
     * @return the horizontal shot direction
     */
    public int getHorizontalShotDirection() {
        return horizontalShotDirection;
    }

    /**
     * Sets horizontal shot direction.
     *
     * @param horizontalShotDirection the horizontal shot direction
     */
    public void setHorizontalShotDirection(int horizontalShotDirection) {
        this.horizontalShotDirection = horizontalShotDirection;
    }

    /**
     * Gets vertical shot direction.
     *
     * @return the vertical shot direction
     */
    public int getVerticalShotDirection() {
        return verticalShotDirection;
    }

    /**
     * Sets vertical shot direction.
     *
     * @param verticalShotDirection the vertical shot direction
     */
    public void setVerticalShotDirection(int verticalShotDirection) {
        this.verticalShotDirection = verticalShotDirection;
    }

    /**
     * checking activation status.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets active status.
     *
     * @param active the active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * to check if laser is laser beam
     *
     * @return the boolean
     */
    public boolean isLaserBeam(){
        return this.getImageSource().equals(ActionCharacters.LASERBEAM_PATH);
    }
}
