package lab5.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentFrame extends JFrame {

    JTextField txtId, txtName, txtMark;
    JRadioButton rMale, rFemale;
    JTable table;

    StudentService service = new StudentService();

    public StudentFrame() {
        setTitle("Student Manager");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));

        txtId = new JTextField();
        txtName = new JTextField();
        txtMark = new JTextField();

        rMale = new JRadioButton("Male", true);
        rFemale = new JRadioButton("Female");

        ButtonGroup group = new ButtonGroup();
        group.add(rMale);
        group.add(rFemale);

        form.add(new JLabel("ID"));
        form.add(txtId);

        form.add(new JLabel("Name"));
        form.add(txtName);

        form.add(new JLabel("Mark"));
        form.add(txtMark);

        form.add(new JLabel("Gender"));
        JPanel genderPanel = new JPanel();
        genderPanel.add(rMale);
        genderPanel.add(rFemale);
        form.add(genderPanel);

        add(form, BorderLayout.NORTH);

        // ===== TABLE =====
        table = new JTable(new DefaultTableModel(
                new Object[]{"ID", "Name", "Gender", "Mark"}, 0
        ));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BUTTON =====
        JPanel btnPanel = new JPanel();

        JButton btnCreate = new JButton("Create");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnReset = new JButton("Reset");

        btnPanel.add(btnCreate);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnReset);

        add(btnPanel, BorderLayout.SOUTH);

        // ===== EVENTS =====
        btnCreate.addActionListener(e -> create());
        btnUpdate.addActionListener(e -> update());
        btnDelete.addActionListener(e -> delete());
        btnReset.addActionListener(e -> clear());

        table.getSelectionModel().addListSelectionListener(e -> fillForm());

        // Load data ban đầu
        loadTable();
    }

    // ===== LOAD TABLE =====
    void loadTable() {
        try {
            List<Student> list = service.findAll();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            for (Student s : list) {
                model.addRow(new Object[]{
                        s.getId(),
                        s.getName(),
                        s.isGender(),
                        s.getMark()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load lỗi: " + e.getMessage());
        }
    }

    // ===== LẤY DỮ LIỆU FORM =====
    Student getForm() {
        return new Student(
                txtId.getText(),
                txtName.getText(),
                Double.parseDouble(txtMark.getText()),
                rMale.isSelected()
        );
    }

    // ===== SET FORM =====
    void setForm(Student s) {
        txtId.setText(s.getId());
        txtName.setText(s.getName());
        txtMark.setText(String.valueOf(s.getMark()));
        if (s.isGender()) rMale.setSelected(true);
        else rFemale.setSelected(true);
    }

    // ===== CLEAR =====
    void clear() {
        txtId.setText("");
        txtName.setText("");
        txtMark.setText("");
        rMale.setSelected(true);
        table.clearSelection();
    }

    // ===== CREATE =====
    void create() {
        try {
            Student s = getForm();
            service.create(s);
            loadTable();
            clear();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm: " + e.getMessage());
        }
    }

    // ===== UPDATE =====
    void update() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn dòng để update!");
            return;
        }

        String id = table.getValueAt(row, 0).toString();

        try {
            service.update(id, getForm());
            loadTable();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi update: " + e.getMessage());
        }
    }

    // ===== DELETE =====
    void delete() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn dòng để xóa!");
            return;
        }

        String id = table.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                service.delete(id);
                loadTable();
                clear();
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi xóa: " + e.getMessage());
            }
        }
    }

    // ===== FILL FORM =====
    void fillForm() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        Student s = new Student(
                table.getValueAt(row, 0).toString(),
                table.getValueAt(row, 1).toString(),
                Double.parseDouble(table.getValueAt(row, 3).toString()),
                Boolean.parseBoolean(table.getValueAt(row, 2).toString())
        );

        setForm(s);
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentFrame().setVisible(true);
        });
    }
}