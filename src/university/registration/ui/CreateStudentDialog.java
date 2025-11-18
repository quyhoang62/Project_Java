package university.registration.ui;

import university.registration.model.Student;
import university.registration.store.Memory;
import university.registration.ui.components.CardPanel;
import university.registration.ui.components.HeroBackgroundPanel;
import university.registration.ui.components.NeutralButton;
import university.registration.ui.components.PrimaryButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.regex.Pattern;

public class CreateStudentDialog extends JDialog {

    // Các trường nhập liệu của form
    JTextField tfId = new JTextField();
    JTextField tfName = new JTextField();
    JTextField tfDob = new JTextField("2004-01-01");   // có giá trị mặc định
    JTextField tfAddr = new JTextField();
    JTextField tfEmail = new JTextField();

    // Mật khẩu và xác nhận mật khẩu
    JPasswordField pf1 = new JPasswordField();
    JPasswordField pf2 = new JPasswordField();

    // Chọn chương trình đào tạo
    JComboBox<String> cbProgram = new JComboBox<>();

    public CreateStudentDialog(JFrame owner){
        super(owner, "Tạo tài khoản sinh viên", true);  // Dialog modal
        setSize(1100, 720);
        setLocationRelativeTo(owner);

        // Background dạng hero với texture
        setContentPane(new HeroBackgroundPanel());
        setLayout(new BorderLayout());

        // Load danh sách CTĐT từ Memory
        for (String p : Memory.programs) cbProgram.addItem(p);

        // Panel dạng card chứa form
        CardPanel card = new CardPanel();
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(32, 48, 32, 48));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 16, 10, 16);
        g.fill = GridBagConstraints.HORIZONTAL;

        // ---------- TITLE ----------
        JLabel title = new JLabel("Đăng ký tài khoản sinh viên");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(new Color(16, 82, 138));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        card.add(title, g);

        JLabel subtitle = new JLabel(
                "Nhập thông tin cá nhân bên dưới. Tài khoản sẽ được kích hoạt ngay sau khi tạo."
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(new Color(90, 90, 90));
        g.gridy = 1;
        card.add(subtitle, g);

        // ---------- FORM INPUT ROWS ----------
        addRow(card, g, 2, "Mã số sinh viên", tfId);
        addRow(card, g, 3, "Họ tên đầy đủ", tfName);
        addRow(card, g, 4, "Ngày sinh (YYYY-MM-DD)", tfDob);
        addRow(card, g, 5, "Địa chỉ liên hệ", tfAddr);
        addRow(card, g, 6, "Email sinh viên", tfEmail);

        // ---------- CHƯƠNG TRÌNH HỌC ----------
        JLabel lbProg = new JLabel("Chương trình học:");
        lbProg.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        cbProgram.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cbProgram.setPreferredSize(new Dimension(420, 36));

        g.gridwidth = 1;
        g.gridx = 0; g.gridy = 7; g.anchor = GridBagConstraints.EAST;
        card.add(lbProg, g);

        g.gridx = 1; g.gridy = 7; g.anchor = GridBagConstraints.WEST;
        card.add(cbProgram, g);

        // ---------- MẬT KHẨU ----------
        addRow(card, g, 8, "Mật khẩu đăng nhập", pf1);
        addRow(card, g, 9, "Xác nhận mật khẩu", pf2);

        // ---------- ACTION BUTTONS ----------
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 0));
        actions.setOpaque(false);

        // Nút đóng
        JButton btnCancel = new NeutralButton("Đóng");
        btnCancel.setPreferredSize(new Dimension(220, 54));

        // Nút tạo tài khoản
        JButton btnCreate = new PrimaryButton("Tạo tài khoản");
        btnCreate.setPreferredSize(new Dimension(220, 54));

        actions.add(btnCancel);
        actions.add(btnCreate);

        g.gridx = 0;
        g.gridy = 10;
        g.gridwidth = 2;
        card.add(actions, g);

        // Đưa card vào dialog
        add(card, BorderLayout.CENTER);

        // ---------- ACTION LISTENERS ----------
        btnCancel.addActionListener(e -> dispose());
        btnCreate.addActionListener(e -> create());

        setVisible(true); // Mở dialog
    }

    /**
     * Helper: thêm 1 hàng vào form dạng: Label | Field
     */
    static void addRow(JPanel p, GridBagConstraints g,
                       int row, String label, JComponent field) {

        JLabel lb = new JLabel(label);
        lb.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        // Label
        g.gridwidth = 1;
        g.gridx = 0; g.gridy = row;
        g.anchor = GridBagConstraints.EAST;
        p.add(lb, g);

        // Field
        g.gridx = 1;
        g.anchor = GridBagConstraints.WEST;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        p.add(field, g);
    }

    /**
     * Nút "Tạo tài khoản" gọi vào đây
     */
    void create(){
        try{
            // Lấy dữ liệu từ form
            String id = tfId.getText().trim();
            String name = tfName.getText().trim();
            String dob = tfDob.getText().trim();
            String addr = tfAddr.getText().trim();
            String email = tfEmail.getText().trim();
            String program = (String) cbProgram.getSelectedItem();

            String p1 = new String(pf1.getPassword());
            String p2 = new String(pf2.getPassword());

            // ---------- VALIDATION ----------
            if(id.isEmpty() || name.isEmpty() || email.isEmpty() ||
                    p1.isEmpty() || p2.isEmpty()){
                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng điền đầy đủ các trường bắt buộc."
                );
                return;
            }

            if(!p1.equals(p2)){
                JOptionPane.showMessageDialog(
                        this,
                        "Mật khẩu xác nhận không khớp."
                );
                return;
            }

            if(!isValidEmail(email)){
                JOptionPane.showMessageDialog(
                        this,
                        "Email không hợp lệ."
                );
                return;
            }

            // ---------- LƯU DỮ LIỆU ----------
            Memory.addStudent(
                    new Student(id, name, dob, addr, email, program),
                    p1
            );

            JOptionPane.showMessageDialog(this, "Tạo tài khoản thành công!");
            dispose(); // Đóng dialog

        }catch(Exception ex){
            // Memory.addStudent() có thể ném lỗi trùng email, trùng MSSV, v.v.
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    /**
     * Check email bằng regex cơ bản
     */
    boolean isValidEmail(String email){
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(regex).matcher(email).matches();
    }
}
