import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.IOException;

/**
 * all the ui elements
 * @author novan
 */
public abstract class UIElements {
    /**
     * The constant buttonImageSource.
     * the image path for all buttons
     */
    public static final String buttonImageSource = "assets/UIAssets/button.png";
    private Image image;
    private float x;
    private float y;
    private boolean active = false;
    private Graphics writing;
    private String text;
    private static App app;

    /**
     * Instantiates a new UI elements.
     *
     * @param x the x
     * @param y the y
     */
    public UIElements(float x, float y) {
        this.x = x;
        this.y = y;
        writing = new Graphics(World.MESSAGE_SIZE, World.MESSAGE_SIZE);
    }

    /**
     * Taking mouse input.
     *
     * @param input the input
     * @param delta the delta
     * @throws SlickException the slick exception
     * @throws IOException    the io exception
     */
    public void update(Input input, int delta) throws SlickException,
            IOException {
        if (isClicked(input) && isActive()){
            onMouseClick();
        }
    }

    /**
     * Rendering the image and message.
     */
    public void render(){
        if (isActive() && image != null){
            image.draw(x, y);
        }

        if(isActive() && getText() != null){
            writing.drawString(text, getX() + getWidth()/2 +
                    World.MESSAGE_OFFSET/2, getY() + getHeight()/2 +
                    World.MESSAGE_OFFSET/2);
        }
    }

    /**
     * On mouse click events.
     *
     * @throws SlickException the slick exception
     * @throws IOException    the io exception
     */
    public abstract void onMouseClick() throws SlickException, IOException;

    /**
     * check if a button is clicked.
     *
     * @param input the input
     * @return the boolean
     */
    public boolean isClicked(Input input){
        return input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) &&
                withinBoundaries(input.getMouseX(), input.getMouseY());
    }

    /**
     * Get width int.
     *
     * @return the int
     */
    public int getWidth(){
        return image.getWidth();
    }

    /**
     * Get height int.
     *
     * @return the int
     */
    public int getHeight(){
        return image.getHeight();
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public float getX() {
        return x;
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
     * Gets y.
     *
     * @return the y
     */
    public float getY() {
        return y;
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

    /**
     * check if a point is within boundaries of the ui elements.
     *
     * @param x the x
     * @param y the y
     * @return the boolean
     */
    public boolean withinBoundaries(float x, float y){
        return (x >= this.x && x <= this.x + getWidth()) && (y >= this.y &&
                y <= this.y + getHeight());
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param imageSource the image source
     * @throws SlickException the slick exception
     */
    public void setImage(String imageSource) throws SlickException {
        image = new Image(imageSource);
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets app.
     *
     * @return the app
     */
    public static App getApp() {
        return app;
    }

    /**
     * Sets app.
     *
     * @param app the app
     */
    public static void setApp(App app) {
        UIElements.app = app;
    }
}
