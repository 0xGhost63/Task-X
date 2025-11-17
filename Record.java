import java.time.LocalDate;
import java.util.*;

abstract class Record implements Comparable<Record>
{
    private int numb;
    private String title;
    private String courseName;
    private LocalDate deadline;
    private Priority priority;
    private int totalMarks;


    Record(int numb,String title,String courseName,int totalMarks,LocalDate deadline,Priority priority)
    {
        setnumb(numb);
        setTitle(title);
        setCourseName(courseName);
        setTotalMarks(totalMarks);
        setDeadline(deadline);
        setPriority(priority);

    }

    //Exclusively for Note/Reminder Class
    Record(int numb,String title,String courseName,LocalDate deadline,Priority priority)
    {
        setnumb(numb);
        setTitle(title);
        setCourseName(courseName);
        setDeadline(deadline);
        setPriority(priority);
    }

    //setters
    public void setnumb(int numb) {
        this.numb = numb;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;

    }

    //Getters
    public String getCourseName() {
        return courseName;
    }
    public LocalDate getDeadline() {
        return deadline;
    }
    public int getnumb() {
        return numb;
    }
    public Priority getPriority() {
        return priority;
    }
    public String getTitle() {
        return title;
    }
        public int getTotalMarks() {
        return totalMarks;
    }

    //Compare to

    @Override
    public int compareTo(Record that)
    {
        return this.getDeadline().compareTo(that.getDeadline());
    }

    //Abstract Methods !
    public abstract void display();
    public abstract String toFileString();


}