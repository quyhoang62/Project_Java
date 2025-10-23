package university.registration.model;

public class RegItem {
    public final Course course;
    public String date, status;
    public RegItem(Course c, String d, String s){ course=c; date=d; status=s; }
}
