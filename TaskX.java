import java.time.LocalDate;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class TaskX
{
    //ESSENTIALS !
    static ArrayList <Record> objects = new ArrayList<>();
    static ArrayList<String> loadedData = new ArrayList<>();
    static ArrayList<String> toLoad = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    
    static
    {
        loadedData=loadData();
    }

    public static void showMenu()
    {
        System.out.println("\n====  Welcome :)  ====");
        System.out.println("Which option would you like to proceede with ?");
        System.out.println("1-Create a new Task");
        System.out.println("2-View Due Tasks For Today");
        System.out.println("3-Search Tasks");
        System.out.println("4-View All Tasks");
        System.out.println("5-Edit a Task");
        System.out.println("0-EXIT");
    }

    public static void showProgress (String toPrint)
    {
        java.util.Date date= new Date();
        System.out.printf("[ %s ] ",date.toString());
        System.out.printf("%s ",toPrint);
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
        ArrayList <String> data = new ArrayList<>();
        boolean isFileFound=true;

        try 
        {
            showProgress("LOADING DATA");
            Scanner fileReader = (new Scanner(new File("SCNZ.txt")));   

            while (fileReader.hasNextLine())
            {
                String line;
                line=fileReader.nextLine();
                data.add(line);
                System.out.println();
            }
            fileReader.close();
        } 
        catch (IOException e) 
        {
            System.out.println("Unable to load data : "+e.getMessage());
            System.out.println("Creating new file !");
            isFileFound=false;
        }
        finally
        {
            if(!isFileFound)
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

        return data;

    }

    public static void backUP(ArrayList<String> newData)
    {
        
        try 
        {
            FileWriter writer = new FileWriter("SCNZ.txt",true);
            showProgress("BACKING UP");

            for (String lines : newData)
            {
                writer.append(lines);
            }
            writer.close();
        }
        catch (IOException e) 
        {
            System.out.println("Unable to backup data error : "+e.getMessage());
        }
    }

    public static void createObject (int choice)
    {
        //Base Variables !
        int numb;
        String title;
        String courseName;
        int totalMarks;
        LocalDate deadline;
        Priority priority;

        


        // For all !
        if (choice>=1 && choice <=4)
        {

            boolean isLab=false;
            // String name = (choice==1) ? "Assignment" : "Quiz" ;
            String name;
            if (choice==1)
            {
                name="Assignment";
            }
            else if(choice==2)
            {
                name="Quiz";
            }
            else if(choice==3)
            {
                name="Note/Reminder";  
            }
            else if(choice==4)
            {
                name="Exam";   
            }
            else
            {
                name="DEFAULT";
            }
            System.out.printf("Enter the %s title : ",name);
            TaskX.input.nextLine(); // <--- NOTE: TaskX.input is used here
            title=TaskX.input.nextLine();
            System.out.printf("Enter the %s number : ",name);
            numb=TaskX.input.nextInt();
            TaskX.input.nextLine();
            System.out.println("Available Course are : ");

            List<String> keys = new ArrayList<>(RecordUtil.coursePriority.keySet());
            for (int i = 0; i < keys.size(); i++) 
            {
                String key = keys.get(i);
                System.out.printf("%d-%s\n", (i + 1), key);             
            }

            System.out.print("\nEnter the Course name : ");
            courseName=TaskX.input.nextLine();
            System.out.print("Enter total marks : ");
            totalMarks=TaskX.input.nextInt();
            // System.out.println("Enter the deadline date : ");
            // deadline=input.next();
            // QUESTIONABLE
            //FIXED :
            System.out.print("Enter deadline (MM-DD): ");
            String md = TaskX.input.next();   
            deadline = LocalDate.parse("2025-" + md);

            System.out.println("Set priority : ");
            System.out.println("1-Low");
            System.out.println("2-Medium");
            System.out.println("3-High");
            System.out.println("4-Critical");
            System.out.print("> ");
            int pri;
            pri=TaskX.input.nextInt();
            if (pri >= 1 && pri <= 4)
            {
                priority = Priority.values()[pri - 1];  
            }
            else 
            {
                System.out.println("Invalid choice, defaulting to LOW.");
                priority = Priority.LOW;
            }

            if (choice!=3 && choice!=4) //Only for Quiz & Assignment !
            {
                
                System.out.printf("Select the nature of the %s : ",name);
                System.out.print("1-Theory ");
                System.out.print("2-Lab\n");
                System.out.print("> ");
                int nature;
                nature=TaskX.input.nextInt();
                TaskX.input.nextLine();
                if (nature==1)
                {
                    isLab=false;    
                }
                else
                {
                    isLab=true;
                }
            }

            if (choice ==1) // Assignment
            {
                Assignment a = new Assignment(numb, title, courseName, totalMarks, deadline, priority, isLab);
                objects.add(a);
                toLoad.add(a.toFileString());
            }
            else if (choice == 2) // Quiz
            {
                Quiz q = new Quiz(numb, title, courseName, totalMarks, deadline, priority, isLab);
                objects.add(q);
                toLoad.add(q.toFileString());
            }
            else if(choice == 4) //Exam
            {
                String examType;
                System.out.print("Enter the exam type (Mids/Finals)");
                examType=TaskX.input.nextLine();

                Exam e = new Exam(numb, title, courseName, totalMarks, deadline, priority, examType);
                objects.add(e);
                toLoad.add(e.toFileString());
            }
            else if (choice==3)
            {
                ArrayList <String> notesData = new ArrayList<>();
                int counter=0;
                String line;
                System.out.print("Write Your Note :(FIN to finish writing) ");
                while (true) 
                {
                    line=TaskX.input.nextLine();
                    if (line.equalsIgnoreCase("FIN"))
                    {
                        break;    
                    }
                    else
                    {
                        notesData.add(line);
                    }
                }

                Reminder r = new Reminder(numb, title, courseName,deadline, priority, notesData);
                objects.add(r);
                toLoad.add(r.toFileString());
            }

            System.out.printf("Successfully Created the %s Task !\n",name);
        
        }

        
    }

    public static void main(String[] args) 
    {

        int choice=0;

        do
        {
            showMenu();    
            System.out.print("Enter the choice (0-10)\n> ");
            choice=input.nextInt();

            switch(choice)
            {
                case 1:
                    int taskType=0;
                    System.out.print("Which type of task would you like to create ?\n");
                    System.out.println("1-Assignment(Theory/Lab)");
                    System.out.println("2-Quiz");
                    System.out.println("3-Note/Reminder");
                    System.out.println("4-Exam");
                    System.out.print("> ");
                    taskType=input.nextInt();
                    if (taskType==1)
                    {
                        createObject(1);
                        break;
                    }
                    else if(taskType==2) 
                    {
                        createObject(2);
                        break;
                    }
                    else if(taskType==3)
                    {
                        createObject(3);
                        break;
                    }
                    else if(taskType==4)
                    {
                        createObject(4);
                        break;
                    }
                    else
                    {
                        System.out.println("Invalid Option Selected !");
                        showProgress("Falling Back");
                        break;
                    }
                    
                    case 2:
                    {

                        

                        break;
                    }


            }
            


        }while(choice!=0);


        backUP(toLoad);
        showProgress("SIGNING OUT");



    }
    
}
 


// EDITING AVAILABILTY
// OVERALL BACKUP
// SORT BY CLASS
// NEW_Line in file for new task
// Remove total marks from the notes ! (DONE)