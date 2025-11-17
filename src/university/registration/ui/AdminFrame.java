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
    JComboBox<String> cbTerm = new JComboBox<>();
    JTable table; DefaultTableModel model;

    JTextField tfCode = new JTextField();
    JTextField tfName = new JTextField();
    JSpinner spCredits = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
    JCheckBox chkOpen = new JCheckBox("Mở lớp kỳ này?");
    JComboBox<String> cbAllowedProgram = new JComboBox<>();

    JCheckBox chkTermOpen = new JCheckBox("Mở đăng ký học kỳ này");

    public AdminFrame(JFrame owner){
        setTitle("PĐT – Quản lý đăng ký học phần");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
		setContentPane(new HeroBackgroundPanel());
		setLayout(new BorderLayout());

        JPanel overlay = new JPanel(new BorderLayout());
        overlay.setOpaque(false);
        overlay.setBorder(new EmptyBorder(24, 32, 32, 32));
        add(overlay, BorderLayout.CENTER);

        CardPanel headerPanel = new CardPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(20, 32, 20, 32));
        JLabel headerTitle = new JLabel("PHÒNG ĐÀO TẠO – QUẢN TRỊ ĐĂNG KÝ HỌC PHẦN", SwingConstants.LEFT);
        headerTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerTitle.setForeground(new Color(16, 82, 138));
        JLabel headerSub = new JLabel("Giám sát học phần, tình trạng mở lớp và lưu lượng đăng ký theo học kỳ");
        headerSub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        headerSub.setForeground(new Color(90, 90, 90));

        JPanel headerLeft = new JPanel();
        headerLeft.setOpaque(false);
        headerLeft.setLayout(new BoxLayout(headerLeft, BoxLayout.Y_AXIS));
        headerLeft.add(headerTitle);
        headerLeft.add(Box.createVerticalStrut(4));
        headerLeft.add(headerSub);

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        headerRight.setOpaque(false);
        JLabel lbUser = new JLabel("Xin chào, PĐT");
        lbUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JButton btnLogout = new PrimaryButton("Đăng xuất");
        btnLogout.setPreferredSize(new Dimension(200, 52));
        btnLogout.addActionListener(e -> { dispose(); new LoginFrame(); });
        headerRight.add(lbUser);
        headerRight.add(btnLogout);

        headerPanel.add(headerLeft, BorderLayout.WEST);
        headerPanel.add(headerRight, BorderLayout.EAST);
        overlay.add(headerPanel, BorderLayout.NORTH);

        JPanel mainArea = new JPanel(new BorderLayout(0, 20));
        mainArea.setOpaque(false);
        mainArea.setBorder(new EmptyBorder(24, 0, 0, 0));
        overlay.add(mainArea, BorderLayout.CENTER);

		CardPanel cardTop = new CardPanel();
		cardTop.setLayout(new GridBagLayout());
		JPanel form = cardTop;
		form.setBorder(new EmptyBorder(12,22,8,22));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8,10,8,10);
        g.anchor = GridBagConstraints.WEST;

        Font fTopLabel = new Font("Segoe UI", Font.BOLD, 18);
        Font fTopText  = new Font("Segoe UI", Font.PLAIN, 18);

        int row=0;

        JLabel lbTerm = new JLabel("Học kỳ:");
        lbTerm.setFont(fTopLabel);
        addCell(form,g,0,row,lbTerm);
        cbTerm.setFont(fTopText); cbTerm.setPreferredSize(new Dimension(160,40));
        for(String t: Memory.loadTerms()) cbTerm.addItem(t);
        addCell(form,g,1,row,cbTerm);

        // Công tắc mở/khóa đăng ký học kỳ
        chkTermOpen.setOpaque(false);
        chkTermOpen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        addCell(form,g,2,row,chkTermOpen,2);

        row++;
        // Nhập thông tin HP
        JLabel lbCode = new JLabel("Mã HP:"); lbCode.setFont(fTopLabel);
        addCell(form,g,0,row,lbCode);
        tfCode.setFont(fTopText); tfCode.setPreferredSize(new Dimension(140,40));
        addCell(form,g,1,row,tfCode);

        JLabel lbName = new JLabel("Tên học phần:"); lbName.setFont(fTopLabel);
        addCell(form,g,2,row,lbName);
        tfName.setFont(fTopText); tfName.setPreferredSize(new Dimension(380,40));
        addCell(form,g,3,row,tfName,2);

        JLabel lbCr = new JLabel("Số TC:"); lbCr.setFont(fTopLabel);
        addCell(form,g,5,row,lbCr);
        ((JSpinner.DefaultEditor) spCredits.getEditor()).getTextField().setFont(fTopText);
        spCredits.setPreferredSize(new Dimension(80, 40));
        addCell(form,g,6,row,spCredits);

        row++;
        // Offering per term
        chkOpen.setOpaque(false); chkOpen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        addCell(form,g,0,row,chkOpen,2);

        JLabel lbAllow = new JLabel("Chỉ CTĐT:"); lbAllow.setFont(fTopLabel);
        addCell(form,g,2,row,lbAllow);
        cbAllowedProgram.setFont(fTopText); cbAllowedProgram.setPreferredSize(new Dimension(380,40));
        cbAllowedProgram.addItem("Tất cả");
        for(String p: Memory.programs) cbAllowedProgram.addItem(p);
        addCell(form,g,3,row,cbAllowedProgram,2);

        JButton btnAdd = new PrimaryButton("Lưu học phần");
        JButton btnDelete = new NeutralButton("Xóa học phần");
        addCell(form,g,5,row,btnAdd);
        addCell(form,g,6,row,btnDelete);

		JPanel northWrap = new JPanel(new BorderLayout());
		northWrap.setOpaque(false);
		northWrap.add(form, BorderLayout.CENTER);
		mainArea.add(northWrap, BorderLayout.NORTH);

        // BẢNG
        model = new DefaultTableModel(new Object[]{
                "Mã HP","Tên học phần","Số TC","Mở lớp?","Chỉ CTĐT","SV đã DK (kỳ chọn)","Chọn"
        }, 0) {
            @Override public boolean isCellEditable(int r,int c){ return c==6; }
            @Override public Class<?> getColumnClass(int c){ return (c==6)?Boolean.class:String.class; }
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

        // FOOTER
        overlay.add(UI.footer(), BorderLayout.SOUTH);

        // Actions
        cbTerm.addActionListener(e -> refresh());
        chkTermOpen.addActionListener(e -> {
            String term=(String)cbTerm.getSelectedItem();
            Memory.setTermOpen(term, chkTermOpen.isSelected());
        });

        btnAdd.addActionListener(e -> addOrUpdate());
        btnDelete.addActionListener(e -> deleteCourses());
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromSelection());

        refresh();
        setVisible(true);
    }

    void addCell(JPanel p, GridBagConstraints g, int x, int y, Component comp) {
        addCell(p,g,x,y,comp,1);
    }
    void addCell(JPanel p, GridBagConstraints g, int x, int y, Component comp, int w) {
        g.gridx=x; g.gridy=y; g.gridwidth=w; p.add(comp,g);
    }

    void refresh(){
        String term = (String) cbTerm.getSelectedItem();
        chkTermOpen.setSelected(Memory.isTermOpen(term));
        model.setRowCount(0);
        for (Course c : Memory.courses.values()) {
            Offering off = Memory.getOffering(term, c.code);
            String openText = off==null? "false" : String.valueOf(off.open);
            String ap = off==null? "Tất cả" : off.allowedProgram;
            int count = Memory.countRegByCourse(term, c.code);
            model.addRow(new Object[]{c.code, c.name, String.valueOf(c.credits), openText, ap, String.valueOf(count), false});
        }
    }

    void fillFormFromSelection(){
        int r=table.getSelectedRow(); if(r<0) return;
        tfCode.setText((String)model.getValueAt(r,0));
        tfName.setText((String)model.getValueAt(r,1));
        try{
            spCredits.setValue(Integer.parseInt((String)model.getValueAt(r,2)));
        }catch(Exception ignore){}
        chkOpen.setSelected(Boolean.parseBoolean((String)model.getValueAt(r,3)));
        cbAllowedProgram.setSelectedItem((String)model.getValueAt(r,4));
    }

    void addOrUpdate(){
        String code=tfCode.getText().trim().toUpperCase();
        String name=tfName.getText().trim();
        int cr=(int) spCredits.getValue();
        if(code.isEmpty()||name.isEmpty()){ JOptionPane.showMessageDialog(this,"Nhập đầy đủ Mã HP và Tên học phần."); return; }

        // cập nhật/ thêm course master
        Memory.addCourse(new Course(code,name,cr)); // put sẽ overwrite

        // cập nhật offering theo kỳ
        String term=(String)cbTerm.getSelectedItem();
        boolean open = chkOpen.isSelected();
        String ap = (String)cbAllowedProgram.getSelectedItem();
        Memory.setOffering(term, code, open, ap);

        clearInputs(); refresh();
    }

    void deleteCourses(){
        List<String> del = new ArrayList<>();
        for (int i=0;i < table.getRowCount();i++){
            Object v = model.getValueAt(i,6);
            if(v instanceof Boolean && (Boolean)v) del.add((String)model.getValueAt(i,0));
        }
        if(del.isEmpty()){ JOptionPane.showMessageDialog(this,"Chọn các dòng cần xóa (cột Chọn)."); return; }

        for(String code: del){
            if(!Memory.canDeleteCourse(code)){
                JOptionPane.showMessageDialog(this,"Không thể xóa " + code + " vì đã có sinh viên đăng ký ở một số học kỳ.");
                return;
            }
        }
        int r=JOptionPane.showConfirmDialog(this,"Xóa "+del.size()+" học phần đã chọn?","Xác nhận",JOptionPane.YES_NO_OPTION);
        if(r!=JOptionPane.YES_OPTION) return;

        for(String code: del) Memory.deleteCourse(code);
        refresh();
    }

    void clearInputs(){ tfCode.setText(""); tfName.setText(""); spCredits.setValue(2); chkOpen.setSelected(false); cbAllowedProgram.setSelectedItem("Tất cả"); }
}
