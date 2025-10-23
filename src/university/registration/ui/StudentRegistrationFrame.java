package university.registration.ui;

import university.registration.model.Course;
import university.registration.model.Offering;
import university.registration.model.RegItem;
import university.registration.model.Student;
import university.registration.store.Memory;
import university.registration.ui.components.NeutralButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class StudentRegistrationFrame extends JFrame {
    final Student student;

    JComboBox<String> cbTerm = new JComboBox<>();
    JComboBox<CourseItem> cbCourse = new JComboBox<>();
    JLabel lbTableTitle = new JLabel();
    JLabel lbTotal = new JLabel("0");
    JLabel lbEmpty = new JLabel("Sinh viên chưa đăng ký HP nào trong kỳ này");
    JLabel lbTermLock = new JLabel();
    JButton btnAdd, btnSubmit, btnDelete;
    JTable table; DefaultTableModel model;

    public StudentRegistrationFrame(JFrame owner, Student s){
        this.student=s;
        setTitle("Sinh viên – Đăng ký học phần | "+s.fullName+" ("+s.studentId+")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(250, 250, 250));
        headerPanel.setBorder(new EmptyBorder(16, 0, 6, 0));
        JLabel headerTitle = new JLabel("ĐĂNG KÝ HỌC PHẦN", SwingConstants.CENTER);
        headerTitle.setFont(new Font("Segoe UI", Font.BOLD, 34));
        headerTitle.setForeground(new Color(140, 0, 0));
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(12, 22, 8, 22));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 10, 8, 10);
        g.anchor = GridBagConstraints.WEST;

        Font fTopLabel = new Font("Segoe UI", Font.BOLD, 18);
        Font fTopText  = new Font("Segoe UI", Font.PLAIN, 18);

        int row = 0;

        JLabel lbTerm = new JLabel("Học kỳ:");
        lbTerm.setFont(fTopLabel);
        addCell(form, g, 0, row, lbTerm);
        cbTerm.removeAllItems();
        for (String t : Memory.loadTerms()) cbTerm.addItem(t);
        cbTerm.setFont(fTopText);
        cbTerm.setPreferredSize(new Dimension(150, 40));
        addCell(form, g, 1, row, cbTerm);

        JLabel lbProgram = new JLabel("Học chương trình: " + student.program);
        lbProgram.setFont(fTopText);
        lbProgram.setForeground(new Color(80,80,80));
        addCell(form, g, 2, row, lbProgram, 3);

        row++;
        JLabel lbCourse = new JLabel("Mã HP đăng ký:");
        lbCourse.setFont(fTopLabel);
        addCell(form, g, 0, row, lbCourse);

        cbCourse.setFont(fTopText);
        cbCourse.setPreferredSize(new Dimension(420, 40));
        cbCourse.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFont(fTopText);
                if (value instanceof CourseItem) {
                    CourseItem ci = (CourseItem) value;
                    setText(ci.code + " - " + ci.name + " (" + ci.credits + " TC)");
                }
                return this;
            }
        });
        addCell(form, g, 1, row, cbCourse, 2);

        btnAdd = new NeutralButton("Đăng ký");
        addCell(form, g, 3, row, btnAdd);

        lbTermLock.setForeground(new Color(180,0,0));
        add(form, BorderLayout.PAGE_START);

        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(new EmptyBorder(6, 20, 10, 20));
        lbTableTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lbTableTitle.setBorder(new EmptyBorder(8, 6, 8, 6));
        center.add(lbTableTitle, BorderLayout.NORTH);

        model=new DefaultTableModel(new Object[]{"Mã HP","Tên học phần","Ngày đăng ký","TT đăng ký","Số TC","Chọn"},0){
            @Override public boolean isCellEditable(int r,int c){ return c==5; }
            @Override public Class<?> getColumnClass(int c){ return (c==5)?Boolean.class:String.class; }
        };
        table=new JTable(model);
        table.setFont(new Font("Segoe UI",Font.PLAIN,16));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,16));
        JScrollPane sp=new JScrollPane(table);
        center.add(sp, BorderLayout.CENTER);

        lbEmpty.setHorizontalAlignment(SwingConstants.CENTER);
        lbEmpty.setForeground(new Color(120,120,120));
        lbEmpty.setBorder(new EmptyBorder(8,0,8,0));
        center.add(lbEmpty, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(new EmptyBorder(6, 20, 16, 20));

        JPanel rightRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JLabel lbSumText = new JLabel("Tổng số TC đăng ký = ");
        lbSumText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JLabel lbTotal = this.lbTotal;
        lbTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnDelete = new NeutralButton("Xóa các HP chọn");
        rightRow.add(lbSumText); rightRow.add(lbTotal); rightRow.add(Box.createHorizontalStrut(10)); rightRow.add(btnDelete);

        JPanel submitRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnSubmit = new NeutralButton("Gửi đăng ký");
        submitRow.add(btnSubmit);

        bottom.add(rightRow, BorderLayout.NORTH);
        bottom.add(submitRow, BorderLayout.SOUTH);
        add(bottom, BorderLayout.SOUTH);

        cbTerm.addActionListener(e -> refreshAll());
        btnAdd.addActionListener(e -> addSelected());
        btnDelete.addActionListener(e -> deleteSelected());
        btnSubmit.addActionListener(e -> submit());
        cbTerm.setSelectedIndex(0);
        refreshAll();

        setVisible(true);
    }

    void addCell(JPanel p, GridBagConstraints g, int x, int y, Component comp) { addCell(p,g,x,y,comp,1); }
    void addCell(JPanel p, GridBagConstraints g, int x, int y, Component comp, int w) { g.gridx=x; g.gridy=y; g.gridwidth=w; p.add(comp,g); }

    static class CourseItem { final String code,name; final int credits; CourseItem(String c,String n,int cr){code=c;name=n;credits=cr;} }

    void rebuildCourseList() {
        cbCourse.removeAllItems();
        String term=(String)cbTerm.getSelectedItem();
        for (Course c : Memory.courses.values()) {
            var off = Memory.getOffering(term, c.code);
            boolean allow = (off!=null && off.open && ("Tất cả".equals(off.allowedProgram) || student.program.equals(off.allowedProgram)));
            if (allow) cbCourse.addItem(new CourseItem(c.code, c.name, c.credits));
        }
    }

    void refreshAll(){
        String term=(String)cbTerm.getSelectedItem();
        boolean open = Memory.isTermOpen(term);
        lbTableTitle.setText("Bảng đăng ký học phần kỳ " + term + " của sinh viên " + student.studentId);
        lbTermLock.setText(open ? "" : "Học kỳ đang khóa đăng ký.");
        btnAdd.setEnabled(open);
        btnSubmit.setEnabled(open);
        rebuildCourseList();
        refreshTable();
    }

    void refreshTable(){
        String term=(String)cbTerm.getSelectedItem();
        model.setRowCount(0);
        var list=Memory.loadReg(student.studentId,term);
        int total=0;
        for(RegItem it:list){
            model.addRow(new Object[]{it.course.code,it.course.name,it.date,it.status,String.valueOf(it.course.credits),false});
            total+=it.course.credits;
        }
        lbTotal.setText(String.valueOf(total));
        lbEmpty.setVisible(list.isEmpty());
    }

    void addSelected() {
        CourseItem ci = (CourseItem) cbCourse.getSelectedItem();
        if (ci == null) { JOptionPane.showMessageDialog(this,"Không có học phần phù hợp để đăng ký."); return; }
        Course c = Memory.courses.get(ci.code);
        String term=(String)cbTerm.getSelectedItem();
        String today=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        boolean ok=Memory.addReg(student.studentId,term,new RegItem(c,today,"Tạm"));
        if(!ok){ JOptionPane.showMessageDialog(this,"Bạn đã đăng ký học phần này trong học kỳ "+term+"."); return; }
        refreshTable();
    }
    void deleteSelected(){
        int rows=model.getRowCount(); Set<String> del=new HashSet<>();
        for(int i=0;i<rows;i++){
            Object v=model.getValueAt(i,5);
            if(v instanceof Boolean && (Boolean)v) del.add((String)model.getValueAt(i,0));
        }
        if(del.isEmpty()){ JOptionPane.showMessageDialog(this,"Hãy tích chọn những dòng muốn xóa."); return; }
        String term=(String)cbTerm.getSelectedItem();
        Memory.deleteByCourseCodes(student.studentId,term,del); refreshTable();
    }
    void submit(){
        String term=(String)cbTerm.getSelectedItem();
        var list=Memory.loadReg(student.studentId,term);
        if(list.isEmpty()){ JOptionPane.showMessageDialog(this,"Chưa có học phần nào để gửi."); return; }
        for(RegItem it:list) it.status="Đã gửi";
        refreshTable();
        JOptionPane.showMessageDialog(this,"Đã gửi kết quả đăng ký cho kỳ "+term+".");
    }
}
