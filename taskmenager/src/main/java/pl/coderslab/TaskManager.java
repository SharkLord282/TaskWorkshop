package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class TaskManager {

    static final String fileName = "tasks.csv";
    static final String[] menu = {"list","add","remove","exit"};
    static String[][] taskList;

    public static void main(String[] args)  {
        taskList = loadFile(fileName);
        printMenuOptions(menu);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String scan = scanner.nextLine();
                switch (scan) {
                    case "exit":
                        exit(fileName, taskList);
                        System.out.println(ConsoleColors.RED + " narra");
                        System.exit(0);
                        break;
                    case "add":
                        addTaskToList();
                        break;
                    case "remove":
                        removeTask(taskList,getTheNumber());
                        break;
                    case "list":
                        printtasklist(taskList);
                        break;
                    default:
                        System.out.println("Please select corect option");
                }
            printMenuOptions(menu);
            }
        }



    public static void printMenuOptions(String[] menu ){
        System.out.println(ConsoleColors.BLUE + "Plese select an option:" + ConsoleColors.RESET);
        for (int i = 0;i < menu.length; ++i) {
            System.out.println(menu[i]);
        }

    }

    public static String[][] loadFile(String file) {
        Path path = Paths.get(file);
        if (!Files.exists(path)) {
            System.out.println("plik nie istnieje");
        }

        String[][] tasklistfile = null;

        try {
            List<String> list = Files.readAllLines(path);
            tasklistfile = new String[list.size()][list.get(0).split(",").length];
            for (int i = 0; i < list.size(); i++) {
                String[] split = list.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    tasklistfile[i][j] = split[j];
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return   tasklistfile;
    }

    public static void printtasklist(String[][] list) {
        for (int i = 0; i < list.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < list[i].length; j++) {
                System.out.print(list[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void addTaskToList() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add task due date");
        String date = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        String isImportant = scanner.nextLine();
        taskList = Arrays.copyOf(taskList, taskList.length +1);
        taskList[taskList.length -1] = new String[3];
        taskList[taskList.length -1][0] = description;
        taskList[taskList.length-1][1] = date;
        taskList[taskList.length-1][2] = isImportant;

    }




    public static int getTheNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove.");
        String n = scanner.nextLine();
        while (!isNumberGreaterEqualZero(n)) {
            System.out.println("Inncorrect argument passed. Please give number gr");
            n = scanner.nextLine();
        }
        return Integer.parseInt(n);
    }

    public static boolean isNumberGreaterEqualZero(String input) {
        if (NumberUtils.isParsable(input)) {
            return  Integer.parseInt(input) >= 0;
        }
        return false;
    }


    private static void removeTask(String[][] list, int listnumber) {
        try {
            if (listnumber < list.length) {
                taskList = ArrayUtils.remove(list,listnumber);
            }
        }catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Element not exist");
        }
    }

    public static void  exit(String filename, String[][] list) {
        Path dir = Paths.get(filename);

        String[] lines = new String[taskList.length];
        for (int i = 0; i < list.length; i++) {
            lines[i] = String.join(",", list[i]);
        }

        try {
            Files.write(dir, Arrays.asList(lines));
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }







}


