package university.registration.ui;

import university.registration.model.Course;
import university.registration.model.Offering;
import university.registration.store.Memory;
import university.registration.ui.components.CardPanel;
import university.registration.ui.components.HeroBackgroundPanel;
import university.registration.ui.components.NeutralButton;
import university.registration.ui.components.PrimaryButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdminFrame extends JFrame {

    // Combobox chọn học kỳ
    JComboBox<String> cbTerm = new JComboBox<>();
    // Bảng & model hiển thị danh sách học phần
    JTable table;
    DefaultTableModel model;

    // Các field nhập thông tin học phần
    JTextField tfCode = new JTextField(); // Mã HP
    JTextField tfName = new JTextField(); // Tên HP
    // Spinner chọn số tín chỉ (min=1, max=10, step=1, default=2)
    JSpinner spCredits = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));

    // Cấu hình offering cho kỳ hiện tại
    JCheckBox chkOpen = new JCheckBox("Mở lớp kỳ này?");
    JComboBox<String> cbAllowedProgram = new JComboBox<>();

    // Checkbox mở/khóa đăng ký cho học kỳ
    JCheckBox chkTermOpen = new JCheckBox("Mở đăng ký học kỳ này");

    public AdminFrame(JFrame owner){
        setTitle("PĐT – Quản lý đăng ký học phần");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);    // full màn hình

        // Nền hero gradient + texture
        setContentPane(new HeroBackgroundPanel());
        setLayout(new BorderLayout());

        // Overlay trong suốt, đặt nội dung chính lên trên background
        JPanel overlay = new JPanel(new BorderLayout());
        overlay.setOpaque(false);
        overlay.setBorder(new EmptyBorder(24, 32, 32, 32));
        add(overlay, BorderLayout.CENTER);

        // ========== HEADER ==========

        CardPanel headerPanel = new CardPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(20, 32, 20, 32));

        JLabel headerTitle = new JLabel(
                "PHÒNG ĐÀO TẠO – QUẢN TRỊ ĐĂNG KÝ HỌC PHẦN",
                SwingConstants.LEFT
        );
        headerTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerTitle.setForeground(new Color(16, 82, 138));

        JLabel headerSub = new JLabel(
                "Giám sát học phần, tình trạng mở lớp và lưu lượng đăng ký theo học kỳ"
        );
        headerSub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        headerSub.setForeground(new Color(90, 90, 90));

        // Bên trái header: Title + subtitle (xếp dọc)
        JPanel headerLeft = new JPanel();
        headerLeft.setOpaque(false);
        headerLeft.setLayout(new BoxLayout(headerLeft, BoxLayout.Y_AXIS));
        headerLeft.add(headerTitle);
        headerLeft.add(Box.createVerticalStrut(4)); // khoảng cách nhỏ
        headerLeft.add(headerSub);

        // Bên phải header: "Xin chào, PĐT" + nút Đăng xuất
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        headerRight.setOpaque(false);

        JLabel lbUser = new JLabel("Xin chào, PĐT");
        lbUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JButton btnLogout = new PrimaryButton("Đăng xuất");
        btnLogout.setPreferredSize(new Dimension(200, 52));
        // Logout: đóng frame hiện tại, mở lại màn hình Login
        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        headerRight.add(lbUser);
        headerRight.add(btnLogout);

        headerPanel.add(headerLeft, BorderLayout.WEST);
        headerPanel.add(headerRight, BorderLayout.EAST);
        overlay.add(headerPanel, BorderLayout.NORTH);

        // ========== MAIN AREA ==========

        JPanel mainArea = new JPanel(new BorderLayout(0, 20));
        mainArea.setOpaque(false);
        mainArea.setBorder(new EmptyBorder(24, 0, 0, 0));
        overlay.add(mainArea, BorderLayout.CENTER);

        // ----- CARD TOP: FORM NHẬP / CẤU HÌNH HỌC PHẦN -----

        CardPanel cardTop = new CardPanel();
        cardTop.setLayout(new GridBagLayout()); // layout dạng lưới linh hoạt
        JPanel form = cardTop;
        form.setBorder(new EmptyBorder(12,22,8,22));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8,10,8,10); // padding giữa các ô
        g.anchor = GridBagConstraints.WEST;

        Font fTopLabel = new Font("Segoe UI", Font.BOLD, 18);  // font label
        Font fTopText  = new Font("Segoe UI", Font.PLAIN, 18); // font input

        int row = 0;

        // --- Hàng 0: Chọn Học kỳ + Mở đăng ký học kỳ ---

        JLabel lbTerm = new JLabel("Học kỳ:");
        lbTerm.setFont(fTopLabel);
        addCell(form, g, 0, row, lbTerm);

        cbTerm.setFont(fTopText);
        cbTerm.setPreferredSize(new Dimension(160,40));
        // Nạp các học kỳ từ Memory
        for(String t: Memory.loadTerms()) cbTerm.addItem(t);
        addCell(form, g, 1, row, cbTerm);

        // Checkbox mở/khóa đăng ký học kỳ
        chkTermOpen.setOpaque(false);
        chkTermOpen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        addCell(form, g, 2, row, chkTermOpen, 2); // chiếm 2 cột

        row++;

        // --- Hàng 1: Nhập thông tin học phần (Mã HP, Tên HP, Số TC) ---

        JLabel lbCode = new JLabel("Mã HP:");
        lbCode.setFont(fTopLabel);
        addCell(form, g, 0, row, lbCode);

        tfCode.setFont(fTopText);
        tfCode.setPreferredSize(new Dimension(140,40));
        addCell(form, g, 1, row, tfCode);

        JLabel lbName = new JLabel("Tên học phần:");
        lbName.setFont(fTopLabel);
        addCell(form, g, 2, row, lbName);

        tfName.setFont(fTopText);
        tfName.setPreferredSize(new Dimension(380,40));
        addCell(form, g, 3, row, tfName, 2); // chiếm 2 cột

        JLabel lbCr = new JLabel("Số TC:");
        lbCr.setFont(fTopLabel);
        addCell(form, g, 5, row, lbCr);

        ((JSpinner.DefaultEditor) spCredits.getEditor())
                .getTextField().setFont(fTopText);
        spCredits.setPreferredSize(new Dimension(80, 40));
        addCell(form, g, 6, row, spCredits);

        row++;

        // --- Hàng 2: Cấu hình Offering (mở lớp + CTĐT) + 2 nút Lưu / Xóa ---

        chkOpen.setOpaque(false);
        chkOpen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        addCell(form, g, 0, row, chkOpen, 2);

        JLabel lbAllow = new JLabel("Chỉ CTĐT:");
        lbAllow.setFont(fTopLabel);
        addCell(form, g, 2, row, lbAllow);

        cbAllowedProgram.setFont(fTopText);
        cbAllowedProgram.setPreferredSize(new Dimension(380,40));
        // "Tất cả" trước, sau đó là danh sách CTĐT trong Memory
        cbAllowedProgram.addItem("Tất cả");
        for(String p: Memory.programs) cbAllowedProgram.addItem(p);
        addCell(form, g, 3, row, cbAllowedProgram, 2);

        JButton btnAdd = new PrimaryButton("Lưu học phần");
        JButton btnDelete = new NeutralButton("Xóa học phần");
        addCell(form, g, 5, row, btnAdd);
        addCell(form, g, 6, row, btnDelete);

        // Bọc form trong một panel để dễ căn trên dưới
        JPanel northWrap = new JPanel(new BorderLayout());
        northWrap.setOpaque(false);
        northWrap.add(form, BorderLayout.CENTER);
        mainArea.add(northWrap, BorderLayout.NORTH);

        // ========== BẢNG DANH MỤC HỌC PHẦN ==========

        model = new DefaultTableModel(new Object[]{
                "Mã HP","Tên học phần","Số TC","Mở lớp?","Chỉ CTĐT",
                "SV đã DK (kỳ chọn)","Chọn"
        }, 0) {
            // Chỉ cho phép sửa ô cột cuối (checkbox chọn)
            @Override public boolean isCellEditable(int r,int c){ return c==6; }
            // Cột 6 trả về kiểu Boolean để JTable render checkbox
            @Override public Class<?> getColumnClass(int c){
                return (c==6) ? Boolean.class : String.class;
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI",Font.PLAIN,16));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,16));

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new EmptyBorder(0,0,0,0));

        CardPanel tableCard = new CardPanel();
        tableCard.setLayout(new BorderLayout());
        tableCard.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel tableTitle = new JLabel("Danh mục học phần theo học kỳ");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tableTitle.setForeground(new Color(40, 40, 40));
        tableCard.add(tableTitle, BorderLayout.NORTH);
        tableCard.add(sp, BorderLayout.CENTER);

        JPanel centerWrap = new JPanel(new BorderLayout());
        centerWrap.setOpaque(false);
        centerWrap.add(tableCard, BorderLayout.CENTER);
        mainArea.add(centerWrap, BorderLayout.CENTER);

        // ========== FOOTER ==========

        overlay.add(UI.footer(), BorderLayout.SOUTH);

        // ========== GẮN ACTION ==========

        // Khi đổi học kỳ → load lại dữ liệu
        cbTerm.addActionListener(e -> refresh());

        // Mở/đóng đăng ký cho học kỳ hiện tại
        chkTermOpen.addActionListener(e -> {
            String term = (String) cbTerm.getSelectedItem();
            Memory.setTermOpen(term, chkTermOpen.isSelected());
        });

        // Lưu / cập nhật học phần
        btnAdd.addActionListener(e -> addOrUpdate());

        // Xóa học phần được tick ở cột "Chọn"
        btnDelete.addActionListener(e -> deleteCourses());

        // Khi chọn dòng trên bảng, đổ lại dữ liệu vào form
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromSelection());

        // Lần đầu: load dữ liệu
        refresh();

        setVisible(true);
    }

    // Helper add component vào GridBagLayout, gridwidth = 1
    void addCell(JPanel p, GridBagConstraints g, int x, int y, Component comp) {
        addCell(p, g, x, y, comp, 1);
    }

    // Helper add component vào GridBagLayout với gridwidth tùy ý
    void addCell(JPanel p, GridBagConstraints g, int x, int y,
                 Component comp, int w) {
        g.gridx = x;
        g.gridy = y;
        g.gridwidth = w;
        p.add(comp, g);
    }

    /**
     * Nạp lại dữ liệu bảng theo học kỳ đang chọn.
     * - Cập nhật trạng thái checkbox "Mở đăng ký học kỳ này"
     * - Duyệt tất cả course master trong Memory.courses
     *   rồi lấy Offering + số lượng SV đăng ký ở kỳ đó.
     */
    void refresh(){
        String term = (String) cbTerm.getSelectedItem();

        // Set trạng thái mở/đóng của kỳ
        chkTermOpen.setSelected(Memory.isTermOpen(term));

        // Xóa toàn bộ dòng cũ
        model.setRowCount(0);

        // Thêm từng course vào bảng
        for (Course c : Memory.courses.values()) {
            Offering off = Memory.getOffering(term, c.code);

            String openText = (off == null)
                    ? "false"
                    : String.valueOf(off.open);

            String ap = (off == null)
                    ? "Tất cả"
                    : off.allowedProgram;

            int count = Memory.countRegByCourse(term, c.code);

            model.addRow(new Object[]{
                    c.code,
                    c.name,
                    String.valueOf(c.credits),
                    openText,
                    ap,
                    String.valueOf(count),
                    false   // cột "Chọn" ban đầu = false
            });
        }
    }

    /**
     * Khi chọn một dòng trên bảng → đưa dữ liệu lại vào form để sửa.
     */
    void fillFormFromSelection(){
        int r = table.getSelectedRow();
        if(r < 0) return; // chưa chọn dòng

        tfCode.setText((String)model.getValueAt(r,0));
        tfName.setText((String)model.getValueAt(r,1));

        try{
            spCredits.setValue(
                    Integer.parseInt((String)model.getValueAt(r,2))
            );
        }catch(Exception ignore){}

        chkOpen.setSelected(
                Boolean.parseBoolean((String)model.getValueAt(r,3))
        );

        cbAllowedProgram.setSelectedItem(
                (String)model.getValueAt(r,4)
        );
    }

    /**
     * Thêm mới hoặc cập nhật học phần:
     * - Validate input
     * - Cập nhật course master trong Memory
     * - Cập nhật Offering cho học kỳ đang chọn
     * - Clear form + refresh bảng
     */
    void addOrUpdate(){
        String code = tfCode.getText().trim().toUpperCase();
        String name = tfName.getText().trim();
        int cr = (int) spCredits.getValue();

        if(code.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nhập đầy đủ Mã HP và Tên học phần."
            );
            return;
        }

        // Cập nhật/ thêm course master (put của Map sẽ overwrite nếu đã có)
        Memory.addCourse(new Course(code, name, cr));

        // Cập nhật offering theo kỳ
        String term = (String)cbTerm.getSelectedItem();
        boolean open = chkOpen.isSelected();
        String ap = (String)cbAllowedProgram.getSelectedItem();
        Memory.setOffering(term, code, open, ap);

        clearInputs();
        refresh();
    }

    /**
     * Xóa các học phần được tick ở cột "Chọn":
     * - Thu thập danh sách courseCode cần xóa
     * - Kiểm tra từng course: nếu đã có SV đăng ký bất kỳ kỳ nào → báo lỗi
     * - Hỏi confirm
     * - Xóa khỏi Memory.courses và offerings
     */
    void deleteCourses(){
        List<String> del = new ArrayList<>();

        // Duyệt bảng, lấy mã HP của các dòng có checkbox = true
        for (int i=0; i < table.getRowCount(); i++){
            Object v = model.getValueAt(i,6);
            if(v instanceof Boolean && (Boolean)v)
                del.add((String)model.getValueAt(i,0));
        }

        if(del.isEmpty()){
            JOptionPane.showMessageDialog(
                    this,
                    "Chọn các dòng cần xóa (cột Chọn)."
            );
            return;
        }

        // Kiểm tra từng course có thể xóa không
        for(String code: del){
            if(!Memory.canDeleteCourse(code)){
                JOptionPane.showMessageDialog(
                        this,
                        "Không thể xóa " + code +
                                " vì đã có sinh viên đăng ký ở một số học kỳ."
                );
                return;
            }
        }

        // Hỏi xác nhận
        int r = JOptionPane.showConfirmDialog(
                this,
                "Xóa " + del.size() + " học phần đã chọn?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );
        if(r != JOptionPane.YES_OPTION) return;

        // Xóa khỏi Memory
        for(String code: del) Memory.deleteCourse(code);

        refresh();
    }

    /** Reset form về trạng thái ban đầu */
    void clearInputs(){
        tfCode.setText("");
        tfName.setText("");
        spCredits.setValue(2);
        chkOpen.setSelected(false);
        cbAllowedProgram.setSelectedItem("Tất cả");
    }
}
