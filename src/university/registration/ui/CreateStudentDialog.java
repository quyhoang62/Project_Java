package university.registration.ui;

import university.registration.model.Student;
import university.registration.store.Memory;
import university.registration.ui.components.CardPanel;
import university.registration.ui.components.NeutralButton;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class CreateStudentDialog extends JDialog {
    JTextField tfId=new JTextField(), tfName=new JTextField(), tfDob=new JTextField("2004-01-01"),
            tfAddr=new JTextField(), tfEmail=new JTextField();
    JPasswordField pf1=new JPasswordField(), pf2=new JPasswordField();
    JComboBox<String> cbProgram = new JComboBox<>();

    public CreateStudentDialog(JFrame owner){
        super(owner,"Tạo tài khoản sinh viên",true);
        setSize(760,560); setLocationRelativeTo(owner); setLayout(new BorderLayout());

        for (String p : Memory.programs) cbProgram.addItem(p);

        CardPanel card=new CardPanel(); card.setLayout(new GridBagLayout());
        GridBagConstraints g=new GridBagConstraints(); g.insets=new Insets(8,12,8,12); g.fill=GridBagConstraints.HORIZONTAL;

        JLabel title=new JLabel("Tạo tài khoản sinh viên"); title.setFont(new Font("Segoe UI",Font.BOLD,24));
        g.gridx=0; g.gridy=0; g.gridwidth=2; card.add(title,g);

        addRow(card,g,1,"Mã số sinh viên:", tfId);
        addRow(card,g,2,"Họ tên:", tfName);
        addRow(card,g,3,"Ngày sinh (YYYY-MM-DD):", tfDob);
        addRow(card,g,4,"Địa chỉ:", tfAddr);
        addRow(card,g,5,"Email SV:", tfEmail);

        JLabel lbProg = new JLabel("Chương trình học:");
        lbProg.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cbProgram.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cbProgram.setPreferredSize(new Dimension(420, 36));
        g.gridwidth=1; g.gridx=0; g.gridy=6; g.anchor=GridBagConstraints.EAST; card.add(lbProg,g);
        g.gridx=1; g.gridy=6; g.anchor=GridBagConstraints.WEST; card.add(cbProgram,g);

        addRow(card,g,7,"Mật khẩu:", pf1);
        addRow(card,g,8,"Xác nhận mật khẩu:", pf2);

        JPanel actions=new JPanel(new FlowLayout(FlowLayout.RIGHT,14,0));
        JButton btnCancel=new NeutralButton("Hủy");
        JButton btnCreate=new NeutralButton("Tạo tài khoản");
        actions.add(btnCancel); actions.add(btnCreate);
        g.gridx=0; g.gridy=9; g.gridwidth=2; card.add(actions,g);

        add(card,BorderLayout.CENTER);

        btnCancel.addActionListener(e -> dispose());
        btnCreate.addActionListener(e -> create());

        setVisible(true);
    }

    static void addRow(JPanel p, GridBagConstraints g, int row, String label, JComponent field){
        JLabel lb=new JLabel(label); lb.setFont(new Font("Segoe UI",Font.PLAIN,18));
        field.setFont(new Font("Segoe UI",Font.PLAIN,18));
        g.gridwidth=1; g.gridx=0; g.gridy=row; g.anchor=GridBagConstraints.EAST; p.add(lb,g);
        g.gridx=1; g.anchor=GridBagConstraints.WEST; g.weightx=1; g.fill=GridBagConstraints.HORIZONTAL; p.add(field,g);
    }

    void create(){
        try{
            String id=tfId.getText().trim(), name=tfName.getText().trim(), dob=tfDob.getText().trim(),
                    addr=tfAddr.getText().trim(), email=tfEmail.getText().trim();
            String program = (String) cbProgram.getSelectedItem();
            String p1=new String(pf1.getPassword()), p2=new String(pf2.getPassword());
            if(id.isEmpty()||name.isEmpty()||email.isEmpty()||p1.isEmpty()||p2.isEmpty()){
                JOptionPane.showMessageDialog(this,"Vui lòng điền đầy đủ các trường bắt buộc."); return; }
            if(!p1.equals(p2)){ JOptionPane.showMessageDialog(this,"Mật khẩu xác nhận không khớp."); return; }
            if(!isValidEmail(email)){ JOptionPane.showMessageDialog(this,"Email không hợp lệ."); return; }

            Memory.addStudent(new Student(id,name,dob,addr,email,program),p1);
            JOptionPane.showMessageDialog(this,"Tạo tài khoản thành công!"); dispose();
        }catch(Exception ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); }
    }
    boolean isValidEmail(String email){
        String regex="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"; return Pattern.compile(regex).matcher(email).matches();
    }
}
