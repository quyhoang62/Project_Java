package university.registration.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UI {
    public static JLabel title(String txt){
        JLabel l=new JLabel(txt);
        l.setFont(new Font("Segoe UI",Font.BOLD,32));
        return l;
    }
    public static JTextField field(){
        JTextField f=new JTextField();
        f.setFont(new Font("Segoe UI",Font.PLAIN,18));
        return f;
    }
    public static JPanel footer(){
        JPanel p=new JPanel(new BorderLayout());
        JLabel left=new JLabel("© 2025 Khoa/Phòng Đào tạo    Hotline: 0123 456 789    Email: pdt@university.edu");
        left.setFont(new Font("Segoe UI",Font.PLAIN,14));
        JLabel right=new JLabel("Hệ thống ĐKHP • v1.0");
        right.setFont(new Font("Segoe UI",Font.PLAIN,14));
        p.add(left,BorderLayout.WEST); p.add(right,BorderLayout.EAST);
        p.setBorder(new EmptyBorder(8,24,12,24)); return p;
    }
}
