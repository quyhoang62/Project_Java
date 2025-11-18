package university.registration.ui;

import university.registration.store.Memory;
import university.registration.model.Student;
import university.registration.ui.components.AguBadgePanel;
import university.registration.ui.components.CardPanel;
import university.registration.ui.components.HeroBackgroundPanel;
import university.registration.ui.components.NeutralButton;
import university.registration.ui.components.PrimaryButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {

    // Ô nhập tài khoản / email
    JTextField tfUser = UI.field();   // UI.field() chắc là helper set font, border...
    // Ô nhập mật khẩu
    JPasswordField pfPass = new JPasswordField();

    // Radio chọn vai trò đăng nhập
    JRadioButton rbAdmin = new JRadioButton("PĐT", true);
    JRadioButton rbStudent = new JRadioButton("Sinh viên");

    public LoginFrame(){
        setTitle("Đăng nhập - Đăng ký học phần");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // full màn hình

        // Nền hero gradient + texture
        setContentPane(new HeroBackgroundPanel());
        setLayout(new BorderLayout());

        // Lớp overlay trong suốt để đặt nội dung lên trên background
        JPanel overlay = new JPanel(new BorderLayout());
        overlay.setOpaque(false);
        overlay.setBorder(BorderFactory.createEmptyBorder(32, 48, 32, 48));

        // ====== VÙNG HERO TITLE (LOGO + TEXT) ======

        JPanel heroTitle = new JPanel(new BorderLayout());
        heroTitle.setOpaque(false);

        // Logo tròn AGU bên trái
        AguBadgePanel badge = new AguBadgePanel();
        heroTitle.add(badge, BorderLayout.WEST);

        // Text hero bên phải logo
        JPanel heroText = new JPanel();
        heroText.setOpaque(false);
        heroText.setLayout(new BoxLayout(heroText, BoxLayout.Y_AXIS));

        JLabel uni = new JLabel("TRƯỜNG ĐẠI HỌC AN GIANG");
        uni.setForeground(Color.WHITE);
        uni.setFont(new Font("Segoe UI", Font.BOLD, 34));
        uni.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel portal = new JLabel("AN GIANG UNIVERSITY STUDENT PORTAL");
        portal.setForeground(new Color(255, 255, 255, 230));
        portal.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        portal.setAlignmentX(Component.LEFT_ALIGNMENT);

        heroText.add(uni);
        heroText.add(Box.createVerticalStrut(4));
        heroText.add(portal);

        heroTitle.add(heroText, BorderLayout.CENTER);

        // Đặt heroTitle lên phía trên
        overlay.add(heroTitle, BorderLayout.NORTH);

        // ====== VÙNG TRUNG TÂM: CARD ĐĂNG NHẬP ======

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        overlay.add(center, BorderLayout.CENTER);

        CardPanel card = new CardPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(560, 520));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 16, 8, 16);
        g.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề card
        JLabel logo = new JLabel("ĐĂNG NHẬP HỆ THỐNG");
        logo.setOpaque(false);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logo.setForeground(new Color(16, 82, 138));

        // Label + field tài khoản
        JLabel lbUser = new JLabel("Tài khoản / Email AGU:");
        lbUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tfUser.setPreferredSize(new Dimension(460, 42));

        // Label + field mật khẩu
        JLabel lbPass = new JLabel("Mật khẩu:");
        lbPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        pfPass.setPreferredSize(new Dimension(460, 42));
        pfPass.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        // Link "Quên mật khẩu?"
        JLabel forgot = new JLabel("<html><u>Quên mật khẩu?</u></html>");
        forgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgot.setForeground(new Color(140, 0, 0));
        forgot.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        // Khi click → hiện dialog hướng dẫn liên hệ PĐT
        forgot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(
                        LoginFrame.this,
                        "Vui lòng liên hệ Phòng Đào tạo để được hỗ trợ đặt lại mật khẩu."
                );
            }
        });

        // Radio cho PĐT / Sinh viên
        rbAdmin.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        rbStudent.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        rbAdmin.setOpaque(false);
        rbStudent.setOpaque(false);
        rbAdmin.setForeground(new Color(60, 60, 60));
        rbStudent.setForeground(new Color(60, 60, 60));

        // Gom 2 radio vào 1 ButtonGroup để chỉ chọn được 1
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbAdmin);
        bg.add(rbStudent);

        JPanel roles = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        roles.setOpaque(false);
        JLabel lbRole = new JLabel("Đăng nhập với vai trò:");
        lbRole.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        roles.add(rbAdmin);
        roles.add(rbStudent);

        JPanel roleRow = new JPanel(new BorderLayout());
        roleRow.setOpaque(false);
        roleRow.add(lbRole, BorderLayout.WEST);
        roleRow.add(roles, BorderLayout.CENTER);

        // Hai nút: Đăng nhập & Tạo tài khoản
        JButton btnLogin = new PrimaryButton("Đăng nhập");
        JButton btnCreate = new NeutralButton("Tạo tài khoản sinh viên");
        Dimension btnSize = new Dimension(240, 54);
        btnLogin.setPreferredSize(btnSize);
        btnCreate.setPreferredSize(btnSize);

        // ====== Đặt các component lên card bằng GridBagLayout ======
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2; g.anchor = GridBagConstraints.CENTER;
        card.add(logo, g);

        g.gridy++;
        card.add(lbUser, g);

        g.gridy++;
        card.add(tfUser, g);

        g.gridy++;
        card.add(lbPass, g);

        g.gridy++;
        card.add(pfPass, g);

        g.gridy++;
        g.anchor = GridBagConstraints.WEST;
        card.add(forgot, g);

        g.gridy++;
        card.add(roleRow, g);

        g.gridy++;
        g.anchor = GridBagConstraints.CENTER;
        card.add(btnLogin, g);

        g.gridy++;
        card.add(btnCreate, g);

        // Card đặt ở giữa màn hình
        center.add(card);

        // Footer dùng chung
        overlay.add(UI.footer(), BorderLayout.SOUTH);

        // Thêm overlay vào frame
        add(overlay, BorderLayout.CENTER);

        // ====== GẮN ACTION ======

        // Mở dialog tạo tài khoản sinh viên
        btnCreate.addActionListener(e -> new CreateStudentDialog(this));

        // Thực hiện đăng nhập
        btnLogin.addActionListener(e -> doLogin());

        setVisible(true);
    }

    /**
     * Xử lý khi bấm nút "Đăng nhập"
     */
    void doLogin(){
        String user = tfUser.getText().trim();
        String pass = new String(pfPass.getPassword());

        // Kiểm tra nhập đủ
        if(user.isEmpty() || pass.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        // Nếu chọn vai trò PĐT (Admin)
        if(rbAdmin.isSelected()){
            if(Memory.verifyAdmin(user, pass)) {
                // Đăng nhập PĐT thành công: mở AdminFrame
                new AdminFrame(this);
                dispose(); // đóng màn hình login
            } else {
                JOptionPane.showMessageDialog(this,
                        "Tài khoản hoặc mật khẩu PĐT không đúng.");
            }
        } else {
            // Vai trò Sinh viên
            if(Memory.verifyStudent(user, pass)){
                // Tìm Student theo MSSV
                Student s = Memory.studentsById.get(user);
                // Mở màn hình đăng ký học phần cho sinh viên đó
                new StudentRegistrationFrame(this, s);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "MSSV hoặc mật khẩu sinh viên không đúng.");
            }
        }
    }
}
