import java.io.*;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static java.nio.charset.StandardCharsets.UTF_16;

public class Main {

    public static void main(String[] args) {

        StringBuilder stringBuilder = new StringBuilder();
        Date date = new Date();

        File dir1 = new File("res");
        File dir2 = new File("savegames");
        File dir3 = new File("temp");
        File dir_1 = new File("src/main");
        File dir_2 = new File("src/test");
        File dir1_1 = new File("res/drawables");
        File dir1_2 = new File("res/vectors");
        File dir1_3 = new File("res/icons");

        stringBuilder.append(createDirectory(dir1, date));
        stringBuilder.append(createDirectory(dir2, date));
        stringBuilder.append(createDirectory(dir3, date));
        stringBuilder.append(createDirectory(dir_1, date));
        stringBuilder.append(createDirectory(dir_2, date));
        stringBuilder.append(createDirectory(dir1_1, date));
        stringBuilder.append(createDirectory(dir1_2, date));
        stringBuilder.append(createDirectory(dir1_3, date));


        File file1 = new File(dir_1, "Main.java");
        File file2 = new File(dir_1, "Utils.java");
        File file3 = new File(dir3, "temp.txt");


        stringBuilder.append(createFile(file1, date));
        stringBuilder.append(createFile(file2, date));
        stringBuilder.append(createFile(file3, date));

        GameProgress player1 = new GameProgress(90, 30, 9, 254.32);
        GameProgress player2 = new GameProgress(75, 33, 3, 121.2);
        GameProgress player3 = new GameProgress(60, 40, 5, 480.57);

        File fileSave1 = new File("D:/Game/savegames/savePlayer1.dat");
        File fileSave2 = new File("D:/Game/savegames/savePlayer2.dat");
        File fileSave3 = new File("D:/Game/savegames/savePlayer3.dat");

        GameProgress.saveGame(fileSave1.getPath(), player1);
        GameProgress.saveGame(fileSave2.getPath(), player2);
        GameProgress.saveGame(fileSave3.getPath(), player3);

        File[] files = {fileSave1, fileSave2, fileSave3};
        String[] paths = {fileSave1.getPath(), fileSave2.getPath(), fileSave3.getPath()};


        for (String path : paths) {
           zipFiles("D:/Game/savegames/playersSave.zip", path);
       }

        for (File file : files){
            stringBuilder.append(deleteFile(file));
        }

        String log = stringBuilder.toString();

        writeInFile(file3.getPath(), log);

        openZip("D:/Game/savegames/playersSave.zip",
                "D:/Game/savegames");

    }

    public static void writeInFile(String fileName, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, UTF_16))) {
            writer.write(text);
            writer.flush();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }


    public static String createDirectory(File file, Date date) {
        if (file.mkdir()) {
          return String.format("%s был создан каталог %s \n", date, file.getName());
        } else {
            return String.format("Не получилось создать каталог %s \n", file.getName());
        }
    }


    public static String createFile(File file, Date date) {
        try {
            if (file.createNewFile()) {
                return String.format("%s был создан файл %s в директории %s \n", date, file.getName(), file.getParent());
            } else {
                return String.format("Не получилось создать файл %s \n", file.getName());
            }
        } catch (IOException exception) {
            return exception.getMessage();
        }
    }


    public static void zipFiles(String archiveFile, String pathPlayersFile) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archiveFile));
             FileInputStream fis = new FileInputStream(pathPlayersFile)) {
            ZipEntry entry = new ZipEntry("player_saved.dat");
            zout.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static String deleteFile(File file){
        if (file.delete()){
            return (String.format("Файл %s удалён \n", file.getName()));
        } else {
            return (String.format("Файл %s не получилось удалить\n", file.getName()));
        }
   }

    public static void openZip(String pathSavePlayer, String pathFile){

        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathSavePlayer))){
            ZipEntry zipEntry;
            String name;
            while ((zipEntry = zin.getNextEntry()) != null){
                name = zipEntry.getName();
                FileOutputStream fout = new FileOutputStream(pathFile + "/" + name);
                for(int c = zin.read(); c != -1; c = zin.read()){
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
             } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
