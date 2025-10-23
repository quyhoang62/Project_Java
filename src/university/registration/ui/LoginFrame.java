package university.registration.ui;

import university.registration.store.Memory;
import university.registration.model.Student;
import university.registration.ui.components.CardPanel;
import university.registration.ui.components.NeutralButton;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    JTextField tfUser = UI.field();
    JPasswordField pfPass = new JPasswordField();
    JRadioButton rbAdmin = new JRadioButton("PĐT", true);
    JRadioButton rbStudent = new JRadioButton("Sinh viên");

    public LoginFrame(){
        setTitle("Đăng nhập - Đăng ký học phần (Styled)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JPanel center = new JPanel(new GridBagLayout()); center.setOpaque(false);
        GridBagConstraints c=new GridBagConstraints(); c.gridx=0;c.gridy=0;c.anchor=GridBagConstraints.CENTER;

        CardPanel card = new CardPanel(); card.setLayout(new GridBagLayout());
        GridBagConstraints g=new GridBagConstraints(); g.insets=new Insets(10,12,10,12); g.fill=GridBagConstraints.HORIZONTAL;

        JLabel ttl=UI.title("Đăng ký học phần"); ttl.setHorizontalAlignment(SwingConstants.CENTER);
        g.gridx=0;g.gridy=0;g.gridwidth=2; card.add(ttl,g);

        JLabel lbRole=new JLabel("Chọn vai trò:"); lbRole.setFont(new Font("Segoe UI",Font.PLAIN,18));
        rbAdmin.setFont(new Font("Segoe UI",Font.PLAIN,18)); rbStudent.setFont(new Font("Segoe UI",Font.PLAIN,18));
        rbAdmin.setOpaque(false); rbStudent.setOpaque(false);
        rbAdmin.setFocusPainted(false); rbStudent.setFocusPainted(false);
        rbAdmin.putClientProperty("JComponent.focusWidth",0); rbStudent.putClientProperty("JComponent.focusWidth",0);
        ButtonGroup bg=new ButtonGroup(); bg.add(rbAdmin); bg.add(rbStudent);
        JPanel roleGrp=new JPanel(new FlowLayout(FlowLayout.LEFT,16,0)); roleGrp.setOpaque(false);
        roleGrp.add(rbAdmin); roleGrp.add(rbStudent);
        g.gridwidth=1; g.gridy=1; g.gridx=0; g.anchor=GridBagConstraints.EAST; card.add(lbRole,g);
        g.gridx=1; g.anchor=GridBagConstraints.WEST; card.add(roleGrp,g);

        JLabel lbUser=new JLabel("Tài khoản / Mã SV:"); lbUser.setFont(new Font("Segoe UI",Font.PLAIN,18));
        JLabel lbPass=new JLabel("Mật khẩu:");          lbPass.setFont(new Font("Segoe UI",Font.PLAIN,18));
        tfUser.setPreferredSize(new Dimension(520,36)); pfPass.setPreferredSize(new Dimension(520,36)); pfPass.setFont(new Font("Segoe UI",Font.PLAIN,18));
        g.gridy=2; g.gridx=0; g.anchor=GridBagConstraints.EAST; card.add(lbUser,g);
        g.gridx=1; g.anchor=GridBagConstraints.WEST;  card.add(tfUser,g);
        g.gridy=3; g.gridx=0; g.anchor=GridBagConstraints.EAST; card.add(lbPass,g);
        g.gridx=1; g.anchor=GridBagConstraints.WEST;  card.add(pfPass,g);

        JButton btnCreate = new NeutralButton("Tạo tài khoản SV");
        JButton btnLogin  = new NeutralButton("Đăng nhập");
        JPanel actions=new JPanel(new FlowLayout(FlowLayout.CENTER,20,0)); actions.setOpaque(false);
        actions.add(btnCreate); actions.add(btnLogin);
        g.gridy=4; g.gridx=0; g.gridwidth=2; card.add(actions,g);

        center.add(card,c);
        add(center,BorderLayout.CENTER);
        add(UI.footer(),BorderLayout.SOUTH);

        btnCreate.addActionListener(e -> new CreateStudentDialog(this));
        btnLogin.addActionListener(e -> doLogin());

        setVisible(true);
    }

    void doLogin(){
        String user=tfUser.getText().trim(); String pass=new String(pfPass.getPassword());
        if(user.isEmpty()||pass.isEmpty()){ JOptionPane.showMessageDialog(this,"Vui lòng nhập đầy đủ thông tin."); return; }
        if(rbAdmin.isSelected()){
            if(Memory.verifyAdmin(user,pass)) { new AdminFrame(this); dispose(); }
            else JOptionPane.showMessageDialog(this,"Sai tài khoản hoặc mật khẩu PĐT.");
        }else{
            if(Memory.verifyStudent(user,pass)){
                Student s=Memory.studentsById.get(user);
                new StudentRegistrationFrame(this,s);
                dispose();
            }else JOptionPane.showMessageDialog(this,"Sai MSSV hoặc mật khẩu sinh viên.");
        }
    }
}
