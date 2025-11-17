import java.time.LocalDate;;
class Quiz extends Assignment
{

    private boolean isViva;

    Quiz(int numb,String title,String courseName,int totalMarks,LocalDate deadline,Priority priority,boolean isLab)

    {
        super(numb, title, courseName,totalMarks,deadline, priority,isLab);
        setViva(isViva);

    }

    //Setter
    public void setViva(boolean isViva) {
        this.isViva = isViva;
    }

    //Getter
    public boolean getViva()
    {
        return isViva;
    }

    @Override
    public void display()
    {
        System.out.printf("%30s\n", "===== Quiz Details =====");
        System.out.println("Number      : " + getnumb());
        System.out.println("Title       : " + getTitle());
        System.out.println("Course      : " + getCourseName());
        System.out.println("Deadline    : " + getDeadline());
        System.out.println("Priority    : " + getPriority());
        System.out.println("Total Marks : " + getTotalMarks());
        System.out.println("Lab/Theory  : " + (getIsLab() ? "Lab" : "Theory"));
        System.out.println("Is Viva     : " + (isViva ? "Yes" : "No"));
        System.out.println("==========================");
    }

    @Override
    public String toFileString()
    {
        return String.join("|",
            "Quiz",
            String.valueOf(getnumb()),
            getTitle(),
            getCourseName(),
            getDeadline().toString(),
            getPriority().name(),
            String.valueOf(getTotalMarks()),
            String.valueOf(getIsLab()),
            String.valueOf(isViva)
        );
    }

    

}