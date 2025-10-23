package university.registration.store;

import university.registration.model.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class Memory {
    /** PĐT accounts */
    public static final Map<String, String> adminPasswords = new HashMap<>();
    /** students & email index */
    public static final Map<String, Student> studentsById   = new HashMap<>();
    public static final Map<String, String> emailIndex      = new HashMap<>();
    /** Master courses (không phụ thuộc kỳ) */
    public static final Map<String, Course> courses         = new LinkedHashMap<>();
    /** terms, programs */
    public static final List<String> terms                  = new ArrayList<>();
    public static final List<String> programs               = new ArrayList<>();

    /** regs: studentId -> term -> list */
    public static final Map<String, Map<String, List<RegItem>>> regs = new HashMap<>();

    /** Cấu hình học kỳ: mở/khóa đăng ký */
    public static final Map<String, TermSetting> termSettings = new HashMap<>();

    /** Offering theo học kỳ */
    public static final Map<String, Map<String, Offering>> offerings = new HashMap<>();

    public static void init() {
        // PĐT mặc định
        adminPasswords.put("pdt", "pdt123");

        // Chương trình học
        programs.addAll(Arrays.asList(
                "Kỹ thuật Điện tử - Viễn thông 2021",
                "Công nghệ Thông tin 2021",
                "Kỹ thuật Cơ khí 2021"
        ));

        // Học kỳ demo
        terms.addAll(Arrays.asList("20252","20251","20242"));
        for (String t : terms) termSettings.put(t, new TermSetting(true)); // mặc định mở

        // Học phần demo
        addCourse(new Course("CT101","Lập trình cơ bản",3));
        addCourse(new Course("CT102","Cấu trúc dữ liệu",3));
        addCourse(new Course("EE201","Mạch điện 1",3));
        addCourse(new Course("MA101","Giải tích 1",4));
        addCourse(new Course("PE101","Giáo dục thể chất",2));

        // Open offerings for latest term – cho tất cả CTĐT
        String latest = terms.get(0);
        for (String code : courses.keySet()) setOffering(latest, code, true, "Tất cả");

        // Sinh viên mặc định
        Student demo = new Student(
                "SV001","Sinh Viên Mặc Định","2004-01-01","Hà Nội",
                "sv001@university.edu","Kỹ thuật Điện tử - Viễn thông 2021"
        );
        addStudent(demo, "sv123");
    }

    /* ---------- helpers data ---------- */
    public static void addCourse(Course c){ courses.put(c.code,c); }
    public static boolean verifyAdmin(String u,String p){ return p.equals(adminPasswords.get(u)); }
    public static boolean verifyStudent(String id,String p){
        Student s=studentsById.get(id); return s!=null && Objects.equals(s.password,p);
    }
    public static void addStudent(Student s,String pass){
        if(studentsById.containsKey(s.studentId)) throw new RuntimeException("Mỗi MSSV chỉ có 1 tài khoản!");
        if(s.email==null||s.email.isBlank()) throw new RuntimeException("Email không được để trống!");
        if(s.program==null||s.program.isBlank()) throw new RuntimeException("Vui lòng chọn chương trình học!");
        String key=s.email.toLowerCase(Locale.ROOT);
        if(emailIndex.containsKey(key)) throw new RuntimeException("Email đã được dùng bởi MSSV: "+emailIndex.get(key));
        s.password=pass; studentsById.put(s.studentId,s); emailIndex.put(key,s.studentId);
    }
    public static List<String> loadTerms(){ return new ArrayList<>(terms); }
    public static List<RegItem> loadReg(String sid,String term){
        return regs.computeIfAbsent(sid,k->new HashMap<>()).computeIfAbsent(term,k->new ArrayList<>());
    }
    public static boolean addReg(String sid,String term,RegItem item){
        var list=loadReg(sid,term);
        if(list.stream().anyMatch(x->x.course.code.equals(item.course.code))) return false;
        list.add(item); return true;
    }
    public static void deleteByCourseCodes(String sid,String term,Set<String> codes){
        var list=loadReg(sid,term); list.removeIf(it->codes.contains(it.course.code));
    }

    /* ---------- term setting ---------- */
    public static boolean isTermOpen(String term){ return termSettings.getOrDefault(term,new TermSetting(false)).registrationOpen; }
    public static void setTermOpen(String term, boolean open){ termSettings.computeIfAbsent(term,t->new TermSetting(open)).registrationOpen=open; }

    /* ---------- offerings per term ---------- */
    public static Offering getOffering(String term, String code){
        return offerings.computeIfAbsent(term,t->new HashMap<>()).get(code);
    }
    public static void setOffering(String term, String code, boolean open, String allowedProgram){
        offerings.computeIfAbsent(term,t->new HashMap<>()).put(code,new Offering(open, allowedProgram));
    }

    /* ---------- Stat & delete course ---------- */
    public static int countRegByCourse(String term, String courseCode){
        int cnt = 0;
        for (var byTerm : regs.values()) {
            var list = byTerm.get(term);
            if (list == null) continue;
            for (RegItem it : list) if (it.course.code.equals(courseCode)) cnt++;
        }
        return cnt;
    }
    public static boolean canDeleteCourse(String courseCode){
        for (var byStudent : regs.values()) {
            for (var list : byStudent.values()) {
                for (RegItem it : list) if (it.course.code.equals(courseCode)) return false;
            }
        }
        return true;
    }
    public static void deleteCourse(String courseCode){
        courses.remove(courseCode);
        for (var m : offerings.values()) m.remove(courseCode);
    }

    /* ---------- small utils for other classes ---------- */
    public static String today(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
