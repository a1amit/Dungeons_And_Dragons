package Frontend;

import Frontend.Observers.Observable;
import Frontend.Observers.Observer;
import DAL.RunningGame;
import DAL.MessageHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StartGame implements Observable, MessageHandler {
    private List<Observer> observers;

    public StartGame() {
        this.observers = new ArrayList<>();
    }

    Scanner sc = new Scanner(System.in);

    @Override
    public void addObserver(Observer o) {
        if (!observers.contains(o))
            observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);

    }

    public void getUserInput() {
        Scanner scanner = new Scanner(System.in);
        notifyObserver(sc.next());
    }

    @Override
    public void notifyObserver(int choice) {
        for (Observer o : observers)
            o.update(choice);
    }

    @Override
    public void notifyObserver(String str) {
        for (Observer o : observers)
            o.update(str);
    }

    // sends levels
    @Override
    public void notifyObserver(List<List<String>> level) {
        for (Observer o : observers)
            o.update(level);
    }

    //  sends messages
    @Override
    public void notifyObserver(MessageHandler messageHandler) {
        for (Observer o : observers)
            o.update(messageHandler);
    }

    /**
     * @param message
     */
    @Override
    public void send(String message) {

        System.out.println(message);
    }

    /**
     * players menu screen
     */
    public void choosePlayer() {

        System.out.println("Select player:\n" +
                "1. Jon Snow \t\t\tHealth: 300/300\t\tAttack: 30\t\tDefense: 4\t\tLevel: 1\t\tExperience: 0/50\t\tCooldown: 0/3\n" +
                "2. The Hound\t\t\tHealth: 400/400\t\tAttack: 20\t\tDefense: 6\t\tLevel: 1\t\tExperience: 0/50\t\tCooldown: 0/5\n" +
                "3. Melisandre   \t\tHealth: 100/100\t\tAttack: 5 \t\tDefense: 1\t\tLevel: 1\t\tExperience: 0/50\t\tMana: 75/300\t\tSpell Power: 15\n" +
                "4. Thoros of Myr\t\tHealth: 250/250\t\tAttack: 25\t\tDefense: 4\t\tLevel: 1\t\tExperience: 0/50\t\tMana: 37/150\t\tSpell Power: 20\n" +
                "5. Arya Stark\t\t\tHealth: 150/150\t\tAttack: 40\t\tDefense: 2\t\tLevel: 1\t\tExperience: 0/50\t\tEnergy: 100/100\n" +
                "6. Bronn\t\t\t\tHealth: 250/250\t\tAttack: 35\t\tDefense: 3\t\tLevel: 1\t\tExperience: 0/50\t\tEnergy: 100/100\n" +
                "7. Ygritte      \t\tHealth: 220/220\t\tAttack: 30\t\tDefense: 2\t\tLevel: 1\t\tExperience: 0/50\t\tArrows: 10\t\tRange: 6\n");

        int choice = sc.nextInt();
        while (choice < 1 || choice > 7) {
            System.out.println("Invalid Entry, such player doesn't exist"
                    + "\n" +
                    "please enter a valid number this time");
            choice = sc.nextInt();
        }
        notifyObserver(choice);

    }

    /**
     * read from files and starts the game
     */
    public static void main(String[] args) {
        StartGame startGame = new StartGame();
        startGame.addObserver(new RunningGame());
        startGame.notifyObserver(startGame);
        startGame.choosePlayer();

        File[] files = new File(args[0]).listFiles();
        List<List<String>> levels = new ArrayList<List<String>>();
        assert files != null;
        for (File file : files) {
            if (file.isFile() && file.getName().contains("level")) {
                List<String> lines = new ArrayList<>();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String next;
                    while ((next = reader.readLine()) != null) {
                        lines.add(next);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                } catch (IOException e) {
                    System.out.println(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
                }
                levels.add(lines);
            }
        }
        startGame.notifyObserver(levels);

        while (true) {
            try {
                startGame.getUserInput();
            } catch (Exception e) {
                return;
            }

        }
    }
}
