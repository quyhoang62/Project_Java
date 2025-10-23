package university.registration.model;

public class Student {
    public final String studentId, fullName, dob, address, email;
    public final String program;
    public String password;

    public Student(String id, String name, String dob, String addr, String email, String program){
        this.studentId=id; this.fullName=name; this.dob=dob; this.address=addr; this.email=email; this.program=program;
    }
}
