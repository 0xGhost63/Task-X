import java.time.LocalDate;
class Exam extends Record
{
    private String examType;

    Exam(int numb,String title,String courseName,int totalMarks,LocalDate deadline,Priority priority,String examType)
    {
        super(numb, title, courseName, totalMarks, deadline, priority);
        setExamType(examType);

    }

    public void setExamType(String examType) {
        this.examType = examType;
    }
    public String getExamType() {
        return examType;
    }

    @Override
    public void display()
    {
        System.out.printf("%30s\n", "===== Exam Details =====");
        System.out.println("Number      : " + getnumb());
        System.out.println("Title       : " + getTitle());
        System.out.println("Course      : " + getCourseName());
        System.out.println("Deadline    : " + getDeadline());
        System.out.println("Priority    : " + getPriority());
        System.out.println("Total Marks : " + getTotalMarks());
        System.out.println("Exam Type   : " + (examType != null ? examType : "N/A"));
        System.out.println("==========================");
    }

    @Override
    public String toFileString()
    {
        return String.join("|",
            "Exam",
            String.valueOf(getnumb()),
            getTitle(),
            getCourseName(),
            getDeadline().toString(),
            getPriority().name(),
            String.valueOf(getTotalMarks()),
            (examType != null ? examType : "NOT-DEFINED")
        );
    }
}