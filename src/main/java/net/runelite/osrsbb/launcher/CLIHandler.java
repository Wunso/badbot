package net.runelite.osrsbb.launcher;

import java.util.Arrays;
import java.util.Scanner;

import static net.runelite.osrsbb.launcher.Application.*;

public class CLIHandler {

    /**
     * Starts a new thread which handles the command line arguments passed while the program is running.
     * The switch case provides an easy-to-read implementation in which commands are available for usage.
     */
    public static void handleCLI() {
        Scanner input = new Scanner(System.in);
        new Thread(() -> {
            while(input.hasNext()) {
                BadLiteInterface botInterface;
                String[] command = input.nextLine().split(" ");
                System.out.println(Arrays.toString(command));
                switch (command[0].toLowerCase()) {
                    case "runscript":
                        botInterface = Application.getBots()[Integer.parseInt(command[1])];
                        botInterface.runScript(command[2], command[3]);
                        break;
                    case "stopscript":
                        botInterface = Application.getBots()[Integer.parseInt(command[1])];
                        botInterface.stopScript();
                        break;
                    case "addbot":
                        addBot(true);
                        break;
                    case "checkstate":
                        for (BadLiteInterface botInstance : bots) {
                            System.out.println(botInstance.getClass().getClassLoader());
                        }
                        break;
                    default:
                        System.out.println("Invalid command");
                        break;
                }
            }
        }).start();
    }
}
