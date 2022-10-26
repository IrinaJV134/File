import java.io.*;

public class GameProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }

    public static void saveGame(String pathFile, GameProgress progress) {
        try (FileOutputStream fos = new FileOutputStream(pathFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String pathSavePlayer){
        try (FileInputStream fis = new FileInputStream(pathSavePlayer);
             ObjectInputStream ois = new ObjectInputStream(fis)){
            return (GameProgress) ois.readObject();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
}