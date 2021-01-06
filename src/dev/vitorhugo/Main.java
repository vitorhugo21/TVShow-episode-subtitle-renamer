package dev.vitorhugo;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.println("--- Rename Episodes and subtitles ---\n");
        System.out.print("Name of the show: ");
        var showName = scanner.nextLine().trim();
        System.out.print("The files are in the current folder (Yes) (No) ?: ");
        String path = null;
        while (true) {
            var option = scanner.nextLine().trim().toLowerCase();

            if (!(option.equals("yes")) && !(option.equals("no"))) {
                System.out.print("\nPlease enter (Yes) (No): ");
                continue;
            }

            if (option.equals("yes")) {
                path = System.getProperty("user.dir").replace("\\", "\\\\");
            }

            if (path == null) {
                System.out.print("Enter absolute path: ");
                path = scanner.nextLine().trim().replace("\\", "\\\\");
            }

            renameFiles(path, convertToTitleCase(showName));
            break;
        }
    }

    public static void renameFiles(String path, String showName) {
        var files = new File(path).listFiles();

        if (files != null) {
            for (var file : files) {
                var parts = file.getName().split("\\.");
                var extension = parts[parts.length - 1];
                var seasonEpisode = "";
                for (var part : parts) {
                    if (part.matches("S[0-9][0-9]E[0-9][0-9]")) {
                        seasonEpisode = part;
                        break;
                    }
                }

                file.renameTo(new File(path + "\\" + showName.replace(" ", ".") +
                        "." + seasonEpisode + "." + extension));
            }
            System.out.println("\nProcess Complete.");
        }
    }

    // https://www.baeldung.com/java-string-title-case
    public static String convertToTitleCase(String showName) {
        if (showName == null || showName.isEmpty()) {
            return showName;
        }

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;
        for (char ch : showName.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }
}
