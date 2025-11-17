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
        JLabel left=new JLabel("© 2025 Trường Đại học An Giang • Trung tâm CNTT – Hotline: 0296 3831 265");
        left.setFont(new Font("Segoe UI",Font.PLAIN,14));
        left.setForeground(new Color(255,255,255,230));
        JLabel right=new JLabel("Cổng dịch vụ sinh viên AGU • v1.0");
        right.setFont(new Font("Segoe UI",Font.PLAIN,14));
        right.setForeground(new Color(255,255,255,200));
        p.add(left,BorderLayout.WEST); p.add(right,BorderLayout.EAST);
        p.setBorder(new EmptyBorder(8,24,12,24));
        p.setOpaque(false);
        return p;
    }
}
