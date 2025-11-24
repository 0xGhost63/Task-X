import java.time.LocalDate;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class TaskX
{
    //ESSENTIALS !
    static ArrayList<Record> objects = new ArrayList<>();
    static ArrayList<Record> completedTasks = new ArrayList<>();
    static ArrayList<String> loadedData = new ArrayList<>();
    static ArrayList<String> toLoad = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    
    static
    {
        loadedData = loadData();
        parseLoadedData();
        loadCompletedTasks();
    }

    public static void showMenu()
    {
        System.out.println("\n====  Welcome :)  ====");
        System.out.println("Which option would you like to proceed with ?");
        System.out.println("1-Create a new Task");
        System.out.println("2-View Due Tasks For Today");
        System.out.println("3-Search Tasks");
        System.out.println("4-View All Tasks");
        System.out.println("5-Edit a Task");
        System.out.println("6-Sort Tasks");
        System.out.println("7-Mark Task as Done");
        System.out.println("8-View Completed Tasks");
        System.out.println("9-View All Tasks History");
        System.out.println("0-EXIT");
    }

    public static void showProgress(String toPrint)
    {
        java.util.Date date = new Date();
        System.out.printf("[ %s ] ", date.toString());
        System.out.printf("%s ", toPrint);
        for (int i = 0; i < 3; i++)
        {
            System.out.print(".");
            try 
            {
                Thread.sleep(300);   
            } 
            catch (InterruptedException e) 
            {
                System.out.println("Sleep Error: " + e.getMessage());
            }
        }
        System.out.println();
    }

    public static ArrayList<String> loadData()
    {
        ArrayList<String> data = new ArrayList<>();
        boolean isFileFound = true;

        try 
        {
            showProgress("LOADING DATA");
            Scanner fileReader = new Scanner(new File("SCNZ.txt"));   

            while (fileReader.hasNextLine())
            {
                String line = fileReader.nextLine();
                if (!line.trim().isEmpty())
                {
                    data.add(line);
                }
            }
            fileReader.close();
        } 
        catch (IOException e) 
        {
            System.out.println("Unable to load data : " + e.getMessage());
            System.out.println("Creating new file !");
            isFileFound = false;
        }
        finally
        {
            if (!isFileFound)
            {
                try 
                {
                    File newFile = new File("SCNZ.txt");
                    newFile.createNewFile();
                } 
                catch (IOException ex) 
                {
                    System.out.println("Could not create new file either :P");
                }
            }
        }

        return data;
    }

    public static void parseLoadedData()
    {
        for (String line : loadedData)
        {
            parseLineToRecord(line, objects);
        }
    }

    public static void parseLineToRecord(String line, ArrayList<Record> targetList)
    {
        String[] parts = line.split("\\|");
        if (parts.length < 2) return;

        String type = parts[0];
        
        try
        {
            int numb = Integer.parseInt(parts[1]);
            String title = parts[2];
            String courseName = parts[3];
            LocalDate deadline = LocalDate.parse(parts[4]);
            Priority priority = Priority.valueOf(parts[5]);

            if (type.equals("Assignment"))
            {
                int totalMarks = Integer.parseInt(parts[5]);
                boolean isLab = Boolean.parseBoolean(parts[6]);
                priority = Priority.valueOf(parts[7]);
                Assignment a = new Assignment(numb, title, courseName, totalMarks, deadline, priority, isLab);
                targetList.add(a);
            }
            else if (type.equals("Quiz"))
            {
                int totalMarks = Integer.parseInt(parts[5]);
                boolean isLab = Boolean.parseBoolean(parts[6]);
                boolean isViva = Boolean.parseBoolean(parts[7]);
                Quiz q = new Quiz(numb, title, courseName, totalMarks, deadline, priority, isLab);
                targetList.add(q);
            }
            else if (type.equals("Exam"))
            {
                int totalMarks = Integer.parseInt(parts[6]);
                String examType = parts[7];
                Exam e = new Exam(numb, title, courseName, totalMarks, deadline, priority, examType);
                targetList.add(e);
            }
            else if (type.equals("Reminder"))
            {
                String notesStr = parts.length > 6 ? parts[6] : "";
                ArrayList<String> notes = new ArrayList<>();
                if (!notesStr.isEmpty())
                {
                    notes.addAll(Arrays.asList(notesStr.split(";")));
                }
                Reminder r = new Reminder(numb, title, courseName, deadline, priority, notes);
                targetList.add(r);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error parsing line: " + line);
        }
    }

    public static void backUP()
    {
        try 
        {
            FileWriter writer = new FileWriter("SCNZ.txt", false); // Overwrite mode
            showProgress("BACKING UP ACTIVE TASKS");

            for (Record record : objects)
            {
                writer.write(record.toFileString() + "\n");
            }
            writer.close();
            System.out.println("Active tasks backed up successfully!");
        }
        catch (IOException e) 
        {
            System.out.println("Unable to backup data error : " + e.getMessage());
        }
    }

    public static void backupCompletedTasks()
    {
        try 
        {
            FileWriter writer = new FileWriter("COMPLETED_TASKS.txt", false);
            showProgress("BACKING UP COMPLETED TASKS");

            for (Record record : completedTasks)
            {
                writer.write(record.toFileString() + "\n");
            }
            writer.close();
            System.out.println("Completed tasks backed up successfully!");
        }
        catch (IOException e) 
        {
            System.out.println("Unable to backup completed tasks: " + e.getMessage());
        }
    }

    public static void backupAllTasksHistory()
    {
        try 
        {
            FileWriter writer = new FileWriter("ALL_TASKS_HISTORY.txt", false);
            showProgress("BACKING UP ALL TASKS HISTORY");

            writer.write("===== ACTIVE TASKS =====\n\n");
            for (Record record : objects)
            {
                writer.write(record.toFileString() + "\n");
            }

            writer.write("\n\n===== COMPLETED TASKS =====\n\n");
            for (Record record : completedTasks)
            {
                writer.write(record.toFileString() + "\n");
            }

            writer.close();
            System.out.println("All tasks history backed up successfully!");
        }
        catch (IOException e) 
        {
            System.out.println("Unable to backup all tasks history: " + e.getMessage());
        }
    }

    public static void loadCompletedTasks()
    {
        try 
        {
            Scanner fileReader = new Scanner(new File("COMPLETED_TASKS.txt"));

            while (fileReader.hasNextLine())
            {
                String line = fileReader.nextLine();
                if (!line.trim().isEmpty())
                {
                    parseLineToRecord(line, completedTasks);
                }
            }
            fileReader.close();
        } 
        catch (IOException e) 
        {
            System.out.println("No completed tasks file found. Creating new one...");
            try 
            {
                new File("COMPLETED_TASKS.txt").createNewFile();
            } 
            catch (IOException ex) 
            {
                System.out.println("Could not create completed tasks file.");
            }
        }
    }

    public static void createObject(int choice)
    {
        int numb;
        String title;
        String courseName;
        int totalMarks;
        LocalDate deadline;
        Priority priority;

        if (choice >= 1 && choice <= 4)
        {
            boolean isLab = false;
            String name;
            if (choice == 1)
            {
                name = "Assignment";
            }
            else if (choice == 2)
            {
                name = "Quiz";
            }
            else if (choice == 3)
            {
                name = "Note/Reminder";  
            }
            else if (choice == 4)
            {
                name = "Exam";   
            }
            else
            {
                name = "DEFAULT";
            }

            System.out.printf("Enter the %s title : ", name);
            input.nextLine();
            title = input.nextLine();
            System.out.printf("Enter the %s number : ", name);
            numb = input.nextInt();
            input.nextLine();
            
            System.out.println("Available Courses are : ");
            List<String> keys = new ArrayList<>(RecordUtil.coursePriority.keySet());
            for (int i = 0; i < keys.size(); i++) 
            {
                String key = keys.get(i);
                System.out.printf("%d-%s\n", (i + 1), key);             
            }

            System.out.print("\nEnter the Course name : ");
            courseName = input.nextLine();
            
            if (choice != 3) // All except Reminder need total marks
            {
                System.out.print("Enter total marks : ");
                totalMarks = input.nextInt();
            }
            else
            {
                totalMarks = 0;
            }

            System.out.print("Enter deadline (MM-DD): ");
            String md = input.next();   
            deadline = LocalDate.parse("2025-" + md);

            System.out.println("Set priority : ");
            System.out.println("1-Low");
            System.out.println("2-Medium");
            System.out.println("3-High");
            System.out.println("4-Critical");
            System.out.print("> ");
            int pri = input.nextInt();
            if (pri >= 1 && pri <= 4)
            {
                priority = Priority.values()[pri - 1];  
            }
            else 
            {
                System.out.println("Invalid choice, defaulting to LOW.");
                priority = Priority.LOW;
            }

            if (choice != 3 && choice != 4)
            {
                System.out.printf("Select the nature of the %s : ", name);
                System.out.print("1-Theory ");
                System.out.print("2-Lab\n");
                System.out.print("> ");
                int nature = input.nextInt();
                input.nextLine();
                isLab = (nature == 2);
            }

            if (choice == 1)
            {
                Assignment a = new Assignment(numb, title, courseName, totalMarks, deadline, priority, isLab);
                objects.add(a);
            }
            else if (choice == 2)
            {
                Quiz q = new Quiz(numb, title, courseName, totalMarks, deadline, priority, isLab);
                objects.add(q);
            }
            else if (choice == 4)
            {
                String examType;
                System.out.print("Enter the exam type (Mids/Finals): ");
                examType = input.nextLine();
                Exam e = new Exam(numb, title, courseName, totalMarks, deadline, priority, examType);
                objects.add(e);
            }
            else if (choice == 3)
            {
                ArrayList<String> notesData = new ArrayList<>();
                String line;
                System.out.println("Write Your Note (FIN to finish writing): ");
                while (true) 
                {
                    line = input.nextLine();
                    if (line.equalsIgnoreCase("FIN"))
                    {
                        break;    
                    }
                    else
                    {
                        notesData.add(line);
                    }
                }
                Reminder r = new Reminder(numb, title, courseName, deadline, priority, notesData);
                objects.add(r);
            }

            System.out.printf("Successfully Created the %s Task !\n", name);
        }
    }

    public static void viewDueTasks()
    {
        LocalDate today = LocalDate.now();
        boolean found = false;
        
        System.out.println("\n===== Tasks Due Today =====");
        for (Record record : objects)
        {
            if (record.getDeadline().equals(today))
            {
                record.display();
                System.out.println();
                found = true;
            }
        }
        
        if (!found)
        {
            System.out.println("No tasks due today!");
        }
    }

    public static void searchTasks()
    {
        System.out.println("\n=== Search Tasks ===");
        System.out.println("1-Search by Title");
        System.out.println("2-Search by Course");
        System.out.println("3-Search by Priority");
        System.out.print("> ");
        int choice = input.nextInt();
        input.nextLine();

        boolean found = false;

        if (choice == 1)
        {
            System.out.print("Enter title to search: ");
            String searchTitle = input.nextLine();
            for (Record record : objects)
            {
                if (record.getTitle().toLowerCase().contains(searchTitle.toLowerCase()))
                {
                    record.display();
                    System.out.println();
                    found = true;
                }
            }
        }
        else if (choice == 2)
        {
            System.out.print("Enter course name to search: ");
            String searchCourse = input.nextLine();
            for (Record record : objects)
            {
                if (record.getCourseName().equalsIgnoreCase(searchCourse))
                {
                    record.display();
                    System.out.println();
                    found = true;
                }
            }
        }
        else if (choice == 3)
        {
            System.out.println("Select Priority:");
            System.out.println("1-Low");
            System.out.println("2-Medium");
            System.out.println("3-High");
            System.out.println("4-Critical");
            System.out.print("> ");
            int priChoice = input.nextInt();
            Priority searchPriority = Priority.values()[priChoice - 1];
            
            for (Record record : objects)
            {
                if (record.getPriority() == searchPriority)
                {
                    record.display();
                    System.out.println();
                    found = true;
                }
            }
        }

        if (!found)
        {
            System.out.println("No tasks found matching your search!");
        }
    }

    public static void viewAllTasks()
    {
        if (objects.isEmpty())
        {
            System.out.println("\nNo tasks available!");
            return;
        }

        System.out.println("\n===== All Tasks =====");
        for (Record record : objects)
        {
            record.display();
            System.out.println();
        }
    }

    public static void editTask()
    {
        System.out.print("Enter the course name of the task you want to edit: ");
        input.nextLine();
        String courseName = input.nextLine();

        ArrayList<Record> matchingTasks = new ArrayList<>();
        for (Record record : objects)
        {
            if (record.getCourseName().equalsIgnoreCase(courseName))
            {
                matchingTasks.add(record);
            }
        }

        if (matchingTasks.isEmpty())
        {
            System.out.println("No tasks found for course: " + courseName);
            return;
        }

        System.out.println("\n===== Tasks for " + courseName + " =====");
        for (int i = 0; i < matchingTasks.size(); i++)
        {
            System.out.println("\n[" + (i + 1) + "]");
            matchingTasks.get(i).display();
        }

        System.out.print("\nSelect the task number to edit (1-" + matchingTasks.size() + "): ");
        int taskChoice = input.nextInt();
        input.nextLine();

        if (taskChoice < 1 || taskChoice > matchingTasks.size())
        {
            System.out.println("Invalid choice!");
            return;
        }

        Record toEdit = matchingTasks.get(taskChoice - 1);

        System.out.println("\nWhat would you like to edit?");
        System.out.println("1-Title");
        System.out.println("2-Course Name");
        System.out.println("3-Deadline");
        System.out.println("4-Priority");
        System.out.print("> ");
        int editChoice = input.nextInt();
        input.nextLine();

        switch (editChoice)
        {
            case 1:
                System.out.print("Enter new title: ");
                String newTitle = input.nextLine();
                toEdit.setTitle(newTitle);
                System.out.println("Title updated successfully!");
                break;
            case 2:
                System.out.print("Enter new course name: ");
                String newCourse = input.nextLine();
                toEdit.setCourseName(newCourse);
                System.out.println("Course name updated successfully!");
                break;
            case 3:
                System.out.print("Enter new deadline (MM-DD): ");
                String md = input.next();
                LocalDate newDeadline = LocalDate.parse("2025-" + md);
                toEdit.setDeadline(newDeadline);
                System.out.println("Deadline updated successfully!");
                break;
            case 4:
                System.out.println("Select new priority:");
                System.out.println("1-Low");
                System.out.println("2-Medium");
                System.out.println("3-High");
                System.out.println("4-Critical");
                System.out.print("> ");
                int pri = input.nextInt();
                Priority newPriority = Priority.values()[pri - 1];
                toEdit.setPriority(newPriority);
                System.out.println("Priority updated successfully!");
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    public static void sortTasks()
    {
        System.out.println("\n=== Sort Tasks ===");
        System.out.println("1-Sort by Deadline");
        System.out.println("2-Sort by Class Type (Assignment/Quiz)");
        System.out.println("3-Sort by Course Priority");
        System.out.print("> ");
        int choice = input.nextInt();

        RecordUtil util = new RecordUtil();

        switch (choice)
        {
            case 1:
                Collections.sort(objects, util.deadlineComparator);
                System.out.println("Tasks sorted by deadline!");
                viewAllTasks();
                break;
            case 2:
                Collections.sort(objects, util.classComparator);
                System.out.println("Tasks sorted by class type!");
                viewAllTasks();
                break;
            case 3:
                Collections.sort(objects, new Comparator<Record>()
                {
                    @Override
                    public int compare(Record r1, Record r2)
                    {
                        Integer p1 = RecordUtil.coursePriority.get(r1.getCourseName());
                        Integer p2 = RecordUtil.coursePriority.get(r2.getCourseName());
                        if (p1 == null) p1 = 999;
                        if (p2 == null) p2 = 999;
                        return p1.compareTo(p2);
                    }
                });
                System.out.println("Tasks sorted by course priority!");
                viewAllTasks();
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    public static void markTaskAsDone()
    {
        System.out.print("Enter the course name of the task you want to mark as done: ");
        input.nextLine();
        String courseName = input.nextLine();

        ArrayList<Record> matchingTasks = new ArrayList<>();
        for (Record record : objects)
        {
            if (record.getCourseName().equalsIgnoreCase(courseName))
            {
                matchingTasks.add(record);
            }
        }

        if (matchingTasks.isEmpty())
        {
            System.out.println("No tasks found for course: " + courseName);
            return;
        }

        System.out.println("\n===== Tasks for " + courseName + " =====");
        for (int i = 0; i < matchingTasks.size(); i++)
        {
            System.out.println("\n[" + (i + 1) + "]");
            matchingTasks.get(i).display();
        }

        System.out.print("\nSelect the task number to mark as done (1-" + matchingTasks.size() + "): ");
        int taskChoice = input.nextInt();
        input.nextLine();

        if (taskChoice < 1 || taskChoice > matchingTasks.size())
        {
            System.out.println("Invalid choice!");
            return;
        }

        Record toComplete = matchingTasks.get(taskChoice - 1);
        
        // Move from active to completed
        objects.remove(toComplete);
        completedTasks.add(toComplete);
        
        System.out.println("\n✓ Task marked as done!");
        System.out.println("Task moved to completed tasks list.");
    }

    public static void viewCompletedTasks()
    {
        if (completedTasks.isEmpty())
        {
            System.out.println("\nNo completed tasks yet!");
            return;
        }

        System.out.println("\n===== Completed Tasks =====");
        for (Record record : completedTasks)
        {
            record.display();
            System.out.println();
        }
    }

    public static void viewAllTasksHistory()
    {
        System.out.println("\n========== ALL TASKS HISTORY ==========");
        
        System.out.println("\n----- ACTIVE TASKS (" + objects.size() + ") -----");
        if (objects.isEmpty())
        {
            System.out.println("No active tasks.");
        }
        else
        {
            for (Record record : objects)
            {
                record.display();
                System.out.println();
            }
        }

        System.out.println("\n----- COMPLETED TASKS (" + completedTasks.size() + ") -----");
        if (completedTasks.isEmpty())
        {
            System.out.println("No completed tasks.");
        }
        else
        {
            for (Record record : completedTasks)
            {
                record.display();
                System.out.println();
            }
        }

        System.out.println("\n========== TOTAL: " + (objects.size() + completedTasks.size()) + " tasks ==========");
    }

    public static void main(String[] args) 
    {
        int choice = 0;

        do
        {
            showMenu();    
            System.out.print("Enter the choice (0-6)\n> ");
            choice = input.nextInt();

            switch (choice)
            {
                case 1:
                    int taskType = 0;
                    System.out.print("Which type of task would you like to create ?\n");
                    System.out.println("1-Assignment(Theory/Lab)");
                    System.out.println("2-Quiz");
                    System.out.println("3-Note/Reminder");
                    System.out.println("4-Exam");
                    System.out.print("> ");
                    taskType = input.nextInt();
                    if (taskType >= 1 && taskType <= 4)
                    {
                        createObject(taskType);
                    }
                    else
                    {
                        System.out.println("Invalid Option Selected !");
                        showProgress("Falling Back");
                    }
                    break;
                    
                case 2:
                    viewDueTasks();
                    break;

                case 3:
                    searchTasks();
                    break;

                case 4:
                    viewAllTasks();
                    break;

                case 5:
                    editTask();
                    break;

                case 6:
                    sortTasks();
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 0);

        backUP();
        showProgress("SIGNING OUT");
        input.close();
    }
}