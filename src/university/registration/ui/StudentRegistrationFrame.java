package university.registration.ui;

import university.registration.model.Course;
import university.registration.model.RegItem;
import university.registration.model.Student;
import university.registration.store.Memory;
import university.registration.ui.components.CardPanel;
import university.registration.ui.components.HeroBackgroundPanel;
import university.registration.ui.components.NeutralButton;
import university.registration.ui.components.PrimaryButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentRegistrationFrame extends JFrame {

    // Sinh viên đang đăng nhập
    final Student student;

    // Chọn học kỳ & học phần
    JComboBox<String> cbTerm = new JComboBox<>();
    JComboBox<CourseItem> cbCourse = new JComboBox<>();

    // Label tiêu đề bảng, tổng TC, thông báo trống, thông báo khóa kỳ
    JLabel lbTableTitle = new JLabel();
    JLabel lbTotal = new JLabel("0");
    JLabel lbEmpty = new JLabel("Sinh viên chưa đăng ký HP nào trong kỳ này");
    JLabel lbTermLock = new JLabel();

    // Các nút thao tác
    JButton btnAdd, btnSubmit, btnDelete;

    // Bảng & model
    JTable table;
    DefaultTableModel model;

    public StudentRegistrationFrame(JFrame owner, Student s){
        this.student = s;

        setTitle("Sinh viên – Đăng ký học phần | " +
                s.fullName + " (" + s.studentId + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setContentPane(new HeroBackgroundPanel());
        setLayout(new BorderLayout());

        // Lớp overlay chứa nội dung
        JPanel overlay = new JPanel(new BorderLayout());
        overlay.setOpaque(false);
        overlay.setBorder(new EmptyBorder(24, 32, 32, 32));
        add(overlay, BorderLayout.CENTER);

        // Card chính
        CardPanel mainCard = new CardPanel();
        mainCard.setLayout(new BorderLayout(0, 24));
        mainCard.setBorder(new EmptyBorder(24, 32, 32, 32));
        overlay.add(mainCard, BorderLayout.CENTER);
        overlay.add(UI.footer(), BorderLayout.SOUTH);

        // ========== HEADER ==========
        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setOpaque(false);

        // Bên trái header: text
        JPanel headerLeft = new JPanel();
        headerLeft.setOpaque(false);
        headerLeft.setLayout(new BoxLayout(headerLeft, BoxLayout.Y_AXIS));

        JLabel headerTitle = new JLabel("TRUNG TÂM ĐÀO TẠO – AGU");
        headerTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerTitle.setForeground(new Color(16, 82, 138));

        JLabel headerSub = new JLabel("Đăng ký học phần trực tuyến cho sinh viên");
        headerSub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        headerSub.setForeground(new Color(90, 90, 90));

        headerLeft.add(headerTitle);
        headerLeft.add(Box.createVerticalStrut(4));
        headerLeft.add(headerSub);

        // Bên phải header: tên sinh viên + nút đăng xuất
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        headerRight.setOpaque(false);

        JLabel lbUser = new JLabel(student.fullName + " • " + student.studentId);
        lbUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JButton btnLogoutTop = new PrimaryButton("Đăng xuất");
        btnLogoutTop.setPreferredSize(new Dimension(200, 52));
        btnLogoutTop.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        headerRight.add(lbUser);
        headerRight.add(btnLogoutTop);

        headerRow.add(headerLeft, BorderLayout.WEST);
        headerRow.add(headerRight, BorderLayout.EAST);
        mainCard.add(headerRow, BorderLayout.NORTH);

        // ========== BODY ==========
        JPanel body = new JPanel(new BorderLayout(0, 18));
        body.setOpaque(false);
        mainCard.add(body, BorderLayout.CENTER);

        // ----- FORM TRÊN CÙNG -----
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 10, 8, 10);
        g.anchor = GridBagConstraints.WEST;

        Font fTopLabel = new Font("Segoe UI", Font.BOLD, 18);
        Font fTopText  = new Font("Segoe UI", Font.PLAIN, 18);

        int row = 0;

        // Hàng 0: Chọn học kỳ + hiển thị CTĐT
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
        addCell(form, g, 2, row, lbProgram, 2);

        row++;

        // Hàng 1: Chọn Mã học phần để đăng ký
        JLabel lbCourse = new JLabel("Mã HP đăng ký:");
        lbCourse.setFont(fTopLabel);
        addCell(form, g, 0, row, lbCourse);

        cbCourse.setFont(fTopText);
        cbCourse.setPreferredSize(new Dimension(420, 40));

        // Renderer custom: hiển thị "CODE - NAME (x TC)"
        cbCourse.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
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

        row++;

        // Hàng 2: Label báo "Học kỳ đang khóa đăng ký" (nếu có)
        lbTermLock.setForeground(new Color(200, 0, 0));
        lbTermLock.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        addCell(form, g, 0, row, lbTermLock, 4);

        body.add(form, BorderLayout.NORTH);

        // ----- BẢNG ĐĂNG KÝ -----
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);

        lbTableTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lbTableTitle.setBorder(new EmptyBorder(8, 6, 8, 6));
        tablePanel.add(lbTableTitle, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new Object[]{"Mã HP","Tên học phần","Ngày đăng ký",
                        "TT đăng ký","Số TC","Chọn"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return c==5; }
            @Override public Class<?> getColumnClass(int c){
                return (c==5) ? Boolean.class : String.class;
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI",Font.PLAIN,16));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,16));
        JScrollPane sp = new JScrollPane(table);
        tablePanel.add(sp, BorderLayout.CENTER);

        lbEmpty.setHorizontalAlignment(SwingConstants.CENTER);
        lbEmpty.setForeground(new Color(120,120,120));
        lbEmpty.setBorder(new EmptyBorder(8,0,8,0));
        tablePanel.add(lbEmpty, BorderLayout.SOUTH);

        body.add(tablePanel, BorderLayout.CENTER);

        // ----- PHẦN DƯỚI: TỔNG TC + NÚT XOÁ / GỬI -----
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(6, 0, 0, 0));

        // Dòng trên: tổng số TC + nút xóa
        JPanel rightRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightRow.setOpaque(false);
        JLabel lbSumText = new JLabel("Tổng số TC đăng ký = ");
        lbSumText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JLabel lbTotal = this.lbTotal;
        lbTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));

        btnDelete = new NeutralButton("Xóa các HP chọn");
        rightRow.add(lbSumText);
        rightRow.add(lbTotal);
        rightRow.add(Box.createHorizontalStrut(10));
        rightRow.add(btnDelete);

        // Dòng dưới: nút gửi đăng ký
        JPanel submitRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        submitRow.setOpaque(false);
        btnSubmit = new PrimaryButton("Gửi đăng ký");
        submitRow.add(btnSubmit);

        bottom.add(rightRow, BorderLayout.NORTH);
        bottom.add(submitRow, BorderLayout.SOUTH);
        body.add(bottom, BorderLayout.SOUTH);

        // ====== GẮN ACTION ======
        cbTerm.addActionListener(e -> refreshAll());
        btnAdd.addActionListener(e -> addSelected());
        btnDelete.addActionListener(e -> deleteSelected());
        btnSubmit.addActionListener(e -> submit());

        // Chọn kỳ đầu tiên, sau đó load dữ liệu
        cbTerm.setSelectedIndex(0);
        refreshAll();

        setVisible(true);
    }

    // Helper add component vào GridBagLayout
    void addCell(JPanel p, GridBagConstraints g, int x, int y, Component comp) {
        addCell(p, g, x, y, comp, 1);
    }
    void addCell(JPanel p, GridBagConstraints g, int x, int y, Component comp, int w) {
        g.gridx = x; g.gridy = y; g.gridwidth = w;
        p.add(comp, g);
    }

    // Item cho combobox course
    static class CourseItem {
        final String code, name;
        final int credits;
        CourseItem(String c, String n, int cr){ code=c; name=n; credits=cr; }
    }

    /**
     * Xây lại danh sách học phần trong combobox,
     * chỉ bao gồm những HP:
     *  - có Offering cho kỳ chọn
     *  - off.open == true
     *  - allowedProgram = "Tất cả" hoặc đúng CTĐT của sinh viên
     */
    void rebuildCourseList() {
        cbCourse.removeAllItems();
        String term = (String) cbTerm.getSelectedItem();

        for (Course c : Memory.courses.values()) {
            var off = Memory.getOffering(term, c.code);

            boolean allow = (off != null && off.open &&
                    ("Tất cả".equals(off.allowedProgram) ||
                            student.program.equals(off.allowedProgram)));

            if (allow)
                cbCourse.addItem(new CourseItem(c.code, c.name, c.credits));
        }
    }

    /**
     * Refresh toàn bộ UI theo kỳ được chọn:
     *  - cập nhật tiêu đề, thông báo khóa kỳ
     *  - enable/disable nút
     *  - rebuild danh sách HP có thể đăng ký
     *  - refresh bảng đăng ký
     */
    void refreshAll(){
        String term = (String) cbTerm.getSelectedItem();
        boolean open = Memory.isTermOpen(term);

        lbTableTitle.setText(
                "Bảng đăng ký học phần kỳ " + term +
                        " của sinh viên " + student.studentId
        );

        lbTermLock.setText(open ? "" : "Học kỳ đang khóa đăng ký.");

        btnAdd.setEnabled(open);
        btnSubmit.setEnabled(open);

        rebuildCourseList();
        refreshTable();
    }

    /**
     * Load bảng đăng ký hiện tại từ Memory cho kỳ đang chọn.
     * Tính lại tổng số tín chỉ, ẩn/hiện label "lbEmpty".
     */
    void refreshTable(){
        String term=(String)cbTerm.getSelectedItem();
        model.setRowCount(0);

        var list = Memory.loadReg(student.studentId, term);
        int total = 0;

        for(RegItem it : list){
            model.addRow(new Object[]{
                    it.course.code,
                    it.course.name,
                    it.date,
                    it.status,
                    String.valueOf(it.course.credits),
                    false   // cột "Chọn" = false ban đầu
            });
            total += it.course.credits;
        }

        lbTotal.setText(String.valueOf(total));
        lbEmpty.setVisible(list.isEmpty());
    }

    /**
     * Đăng ký học phần đang chọn trong combobox.
     */
    void addSelected() {
        CourseItem ci = (CourseItem) cbCourse.getSelectedItem();
        if (ci == null) {
            JOptionPane.showMessageDialog(this,
                    "Không có học phần phù hợp để đăng ký.");
            return;
        }

        Course c = Memory.courses.get(ci.code);
        String term = (String)cbTerm.getSelectedItem();

        String today = new SimpleDateFormat("yyyy-MM-dd")
                .format(new Date());

        boolean ok = Memory.addReg(
                student.studentId,
                term,
                new RegItem(c, today, "Tạm")
        );

        if(!ok){
            JOptionPane.showMessageDialog(
                    this,
                    "Bạn đã đăng ký học phần này trong học kỳ " + term + "."
            );
            return;
        }

        refreshTable();
    }

    /**
     * Xóa các dòng đã tick ở cột "Chọn".
     */
    void deleteSelected(){
        int rows = model.getRowCount();
        Set<String> del = new HashSet<>();

        for(int i=0; i<rows; i++){
            Object v = model.getValueAt(i,5);
            if(v instanceof Boolean && (Boolean)v)
                del.add((String)model.getValueAt(i,0)); // mã HP
        }

        if(del.isEmpty()){
            JOptionPane.showMessageDialog(
                    this,
                    "Hãy tích chọn những dòng muốn xóa."
            );
            return;
        }

        String term = (String)cbTerm.getSelectedItem();
        Memory.deleteByCourseCodes(student.studentId, term, del);
        refreshTable();
    }

    /**
     * Gửi kết quả đăng ký:
     *  - Nếu chưa có HP nào → báo
     *  - Ngược lại: set status = "Đã gửi" cho toàn bộ RegItem của kỳ
     */
    void submit(){
        String term = (String)cbTerm.getSelectedItem();
        var list = Memory.loadReg(student.studentId, term);

        if(list.isEmpty()){
            JOptionPane.showMessageDialog(
                    this,
                    "Chưa có học phần nào để gửi."
            );
            return;
        }

        for(RegItem it : list)
            it.status = "Đã gửi";

        refreshTable();
        JOptionPane.showMessageDialog(
                this,
                "Đã gửi kết quả đăng ký cho kỳ " + term + "."
        );
    }
}
