import org.newdawn.slick.SlickException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The type Wave file handler handles waves file.
 * @author novan
 */
public class WaveFileHandler {
    /**
     * The constant INITIAL_Y_COORDINATE for all aliens
     */
    public static float INITIAL_Y_COORDINATE = -64;
    private static ArrayList<Enemy> aliens;
    private static World world;
    private static Random rand = new Random();
    private static float MAX_SWARM_Y = 486;
    private static float MIN_SWARM_Y = 200;

    /**
     * Initialize commands.
     *
     * @param world    the world
     * @param filePath the file path
     * @throws IOException    the io exception
     * @throws SlickException the slick exception
     */
    public static void initializeCommands(World world, String filePath) throws
            IOException, SlickException {

        aliens = world.getAliens();
        File file = new File(filePath);
        WaveFileHandler.world = world;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String text;

            while ((text = br.readLine()) != null) {
                if (!text.contains("#") && text.contains(",")){
                    /**
                     * splitting the text information
                     */
                    String[] information = text.split(",");
                    String name = information[0];
                    float x = Float.parseFloat(information[1]);
                    int delay = Integer.parseInt(information[2]);

                    addEnemies(name, x, delay);
                }
            }
        } catch (Exception e){
            throw e;
        }

        /**
         * counting the bosses
         */
        for(Enemy alien: aliens){
            if (alien instanceof Boss){
                world.setBossCounter(world.getBossCounter() + 1);
            }
        }
    }

    /**
     * Add enemies based on the name and sets its delay value.
     *
     * @param name  the name enemy type
     * @param x     the x coordinate
     * @param delay the delay the time before it is activated
     * @throws SlickException the slick exception
     */
    public static void addEnemies(String name, float x, int delay)
            throws SlickException{


        if (name.equals("BasicEnemy")){
            aliens.add(new DownwardMovingAliens
                    ("/assets/enemy/basic-enemy.png", x,
                            INITIAL_Y_COORDINATE));

        } else if (name.equals("SineEnemy")){
            aliens.add(new SineMovingAliens
                    ("/assets/enemy/sine-enemy.png", x,
                            INITIAL_Y_COORDINATE));

        } else if (name.equals("BasicShooter")){
            aliens.add(new BasicShooter
                    ("/assets/enemy/basic-shooter.png", x,
                            INITIAL_Y_COORDINATE));

        } else if (name.equals("MinionShooter")){
            aliens.add(new MinionShooter
                    ("/assets/enemy/minion-shooter.png", x,
                            INITIAL_Y_COORDINATE));

        } else if (name.equals("MiniSwarm")){
            float ySwarm = getRandomYSwarm();
            for (int i = 0; i < 4; i++){
                aliens.add
                        (new MiniSwarm("/assets/enemy/Swarm.png", x,
                                INITIAL_Y_COORDINATE, i, ySwarm));
                aliens.get(aliens.size()-1).setTimeActive(delay);
            }

        } else if (name.equals("SineShooter")){
          aliens.add(new SineShooter
                  ("/assets/enemy/sine-shooter.png", x,
                          INITIAL_Y_COORDINATE));

        } else if (name.equals("Boss") || name.equals("BossOne")){
            aliens.add(new BossOne ("/assets/Bosses/BossOne.png", x,
                    INITIAL_Y_COORDINATE));

        } else if (name.equals("PurpleSplitter")){
            aliens.add(new BossPurpleSplitter
                    ("/assets/Bosses/BossPurpleSplitter.png", x,
                            INITIAL_Y_COORDINATE));

        } else if (name.equals("Kamikaze")){
            aliens.add(new BossKamikaze(
                    "/assets/Bosses/BossKamikaze.png", x,
                    INITIAL_Y_COORDINATE, world.getPlayer()));

        } else if (name.equals("SpaceHunter")){
            aliens.add(new BossSpaceHunter
                    ("/assets/Bosses/BossSpaceHunter.png", x,
                            INITIAL_Y_COORDINATE));

        } else if (name.equals("TheLobster")){
            BossTheLobster head = new BossTheLobster
                    ("/assets/Bosses/lobsterHead.png", x,
                            INITIAL_Y_COORDINATE, world);
            BossTheLobsterPincer leftPincer = new BossTheLobsterPincer
                    ("/assets/Bosses/leftPincer.png", head,
                    BossTheLobsterPincer.LEFT_PINCER);
            BossTheLobsterPincer rightPincer = new BossTheLobsterPincer
                    ("/assets/Bosses/rightPincer.png", head,
                    BossTheLobsterPincer.RIGHT_PINCER);
            aliens.add(leftPincer);
            aliens.add(rightPincer);
            aliens.add(head);

        } else if (name.equals("FinalBoss")){
            aliens.add(new BossFinal
                    ("/assets/Bosses/BossFinal.png", x,
                            INITIAL_Y_COORDINATE));
        }
        aliens.get(aliens.size()-1).setTimeActive(delay);
    }

    /**
     * Get random y swarm float.
     *
     * @return the float
     */
    public static float getRandomYSwarm(){
        return rand.nextInt((int) (MAX_SWARM_Y - MIN_SWARM_Y) + 1) +
                MIN_SWARM_Y;
    }
}
