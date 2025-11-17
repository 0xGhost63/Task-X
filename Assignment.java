import java.time.LocalDate;

class Assignment extends Record
{
    private boolean isLab;

    // //EXTRA CONSTRUCTOR FOR NON-LAB QUIZ DEAL WITH IT LATER
    // Assignment(int numb,String title,String courseName,LocalDate deadline,Priority priority,int totalMarks)
    // {
    //     super(numb,title, courseName, deadline,priority);
    // }

    Assignment(int numb,String title,String courseName,int totalMarks,LocalDate deadline,Priority priority,boolean isLab)
    {
        super(numb, title, courseName, totalMarks, deadline, priority);
        setLab(isLab);
    }


    //Setter


    public void setLab(boolean isLab) {
        this.isLab = isLab;
    }

    //Getter


    public boolean getIsLab()
    {
        return isLab;
    }



    @Override
    public void display()
    {
        System.out.printf("%30s\n","===== Assignment Details =====");
        System.out.println("Number      : " + getnumb());
        System.out.println("Title       : " + getTitle());
        System.out.println("Course      : " + getCourseName());
        System.out.println("Deadline    : " + getDeadline());
        System.out.println("Priority    : " + getPriority());
        System.out.println("Total Marks : " + getTotalMarks());
        System.out.println("Is Lab      : " + (isLab ? "Yes" : "No"));
        System.out.println("==========================");
    }

    @Override
    public String toFileString()
    {
        String lab;
        lab=(isLab) ? "Lab" : "Theory" ;
        return String.join("|",
            "Assignment",
            String.valueOf(getnumb()),
            getTitle(),
            getCourseName(),
            getDeadline().toString(),
            String.valueOf(getTotalMarks()),
            String.valueOf(getIsLab()),
            getPriority().name()

        );
    }


}