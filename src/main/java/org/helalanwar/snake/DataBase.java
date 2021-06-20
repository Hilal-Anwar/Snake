package org.helalanwar.snake;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class DataBase {

    File file = new File(System.getProperty("user.home") + "//snakeData.txt");
    TreeMap<Integer, HighScore> temporaryData = new TreeMap<>(Collections.reverseOrder());
    void readFile() throws IOException {
        file.createNewFile();
        var list = decryptFile();
        for (String line : list) {
            int number = Integer.parseInt(line.substring(line.indexOf(':') + 1));
            String PLAYER_NAME = line.substring(0, line.indexOf(':'));
            temporaryData.put(number, new HighScore(PLAYER_NAME, number));
        }
    }

    List<String> decryptFile() throws IOException {
        FileReader fileReader;
        fileReader = new FileReader(file);
        var bufferReader = new BufferedReader(fileReader);
        try {
            StringBuilder line = new StringBuilder();
            String l;
            while ((l = bufferReader.readLine()) != null) {
                line.append(l);
            }
            fileReader.close();
            bufferReader.close();
            return decryptedMessage(line.toString()).lines().toList();

        } catch (IOException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            file.delete();
            file.createNewFile();
        }
        return List.of();
    }

    void fileWriter(int score, String name) throws IOException {
        temporaryData.put(score, new HighScore(name, score));
        BufferedWriter out;
        FileWriter fw = new FileWriter(file);
        out = new BufferedWriter(fw);
        List<HighScore> value = new ArrayList<>(temporaryData.values());
        int end = (temporaryData.keySet().size() <= 5) ? temporaryData.size() : 5;
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < end; i++) {
            message.append(value.get(i).getName()).append(":").append(value.get(i).getScore()).append('\n');
        }
        try {
            out.write(encryptedMessage(message.toString()));
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }

    String encryptedMessage(String x) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return AES.encrypt(x, "SnakeGame");
    }

    String decryptedMessage(String x) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return AES.decrypt(x, "SnakeGame");
    }

}
