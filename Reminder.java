import java.util.ArrayList;
import java.time.LocalDate;

class Reminder extends Record
{
    ArrayList <String> note;

    Reminder(int numb,String title,String courseName,LocalDate deadline,Priority priority,ArrayList<String> note)
    {
        super(numb, title, courseName,deadline, priority);
        setNote(note);
    }

    //Getters & Setters

    public void setNote( ArrayList<String> note) {
        this.note = note;
    }
    public ArrayList<String> getNote() {
        return note;
    }

    @Override
    public void display()
    {
        System.out.printf("%30s\n", "===== Reminder Details =====");
        System.out.println("Number      : " + getnumb());
        System.out.println("Title       : " + getTitle());
        System.out.println("Course      : " + getCourseName());
        System.out.println("Deadline    : " + getDeadline());
        System.out.println("Priority    : " + getPriority());
        System.out.println("Notes       :");
        if (note != null && note.size() > 0) {
            for (String n : note) 
            {
                System.out.println("  - " + n);
            }
        } else {
            System.out.println("  (No notes)");
        }
        System.out.println("============================");
    }

    @Override
    public String toFileString()
    {
        // Join multiple note lines into one string separated by semicolon
        String notesStr = (note != null && note.size() > 0) ? String.join(";", note) : "";

        return String.join("|",
            "Reminder",
            String.valueOf(getnumb()),
            getTitle(),
            getCourseName(),
            getDeadline().toString(),
            getPriority().name(),
            notesStr
        );
    }

    
}