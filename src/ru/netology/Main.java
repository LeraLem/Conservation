package ru.netology;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void saveGame(String str, GameProgress gameProgress) {
        File myFile = new File(str);

        try {
            if (myFile.createNewFile())
                System.out.println("File " + str +  " was created.");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        try (FileOutputStream fos = new FileOutputStream(str);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            System.out.println("File " + str + " was serialized");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String[] massiveOfString, String strZip) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(strZip))) {
            byte[] buffer;

            for (int i = 0; i < massiveOfString.length; i++) {
                try (FileInputStream fis = new FileInputStream(massiveOfString[i])) {
                    ZipEntry entry = new ZipEntry("packedSave" + (i + 1) + ".dat");
                    zout.putNextEntry(entry);
                    buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteFiles(String[] massiveOfString) {

        for (int i = 0; i < massiveOfString.length; i++){

            if(new File(massiveOfString[i]).exists()) {
                if (new File(massiveOfString[i]).delete()) {
                    System.out.println("File " + massiveOfString[i] + " was deleted.");
                } else {
                    System.out.println("File " + massiveOfString[i] + " was not deleted.");
                }
            } else {
                System.out.println("File " + massiveOfString[i] + " does not exists.");
            }
        }
    }

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(13, 14, 2, 2.4);
        GameProgress gameProgress2 = new GameProgress(22, 10, 1, 5.3);
        GameProgress gameProgress3 = new GameProgress(32, 15, 3, 6.8);

        String[] massiveOfString = new String[3];
        String str = "C:/Valerya/Folder/Games/savegames/save1.dat";
        saveGame(str, gameProgress1);
        massiveOfString[0] = str;

        str = "C:/Valerya/Folder/Games/savegames/save2.dat";
        saveGame(str, gameProgress2);
        massiveOfString[1] = str;

        str = "C:/Valerya/Folder/Games/savegames/save3.dat";
        saveGame(str, gameProgress3);
        massiveOfString[2] = str;

        String strZip = "C:/Valerya/Folder/Games/savegames/zip.zip";

        zipFiles(massiveOfString, strZip);
        deleteFiles(massiveOfString);
    }
}
