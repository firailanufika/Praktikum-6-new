package absen;

import javax.swing.*;
import javax.swing.JScrollPane;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class GUI {
    
    DefaultTableModel tabelModel;
    public GUI() {
        

        JFrame frame = new JFrame("Absen Pengunjung");
        frame.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JPanel panelAbsen = new JPanel();
        JScrollPane panelTabel = new JScrollPane();
        


        formAbsen(panelAbsen);        
        cetakTabel(panelTabel);
        
        frame.add(panelAbsen);
        frame.add(panelTabel);      

        frame.setLayout(new FlowLayout(FlowLayout.LEFT));
        frame.setResizable(false);
        frame.setVisible(true);

    }
    private void formAbsen(JPanel panel){
        //panel.setSize(200, 200);
        panel.setLayout(new GridLayout(5,0,0,10));
        Absen absen = new Absen();
        
        panel.setBorder(BorderFactory.createTitledBorder("Form Absen"));
        
        JTextField fieldNamaLengkap = new JTextField(20);
        JTextField fieldAsalKota = new JTextField(20);
        JTextField fieldNomorPonsel = new JTextField(20);
        JTextField fieldEmail = new JTextField(20);
        JButton tombolSubmit = new JButton("Absen");
        
        panel.add(new JLabel("Nama lengkap  :"));
        panel.add(fieldNamaLengkap);
        
        panel.add(new JLabel("Asal kota           :"));
        panel.add(fieldAsalKota);
       
        panel.add(new JLabel("Nomor ponsel  :"));
        panel.add(fieldNomorPonsel);
                
        panel.add(new JLabel("Email                  :"));
        panel.add(fieldEmail);
        
        panel.add(tombolSubmit);
        
       // panel.add(tombolSubmit,BorderLayout.SOUTH);
        
        tombolSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String namaLengkap = fieldNamaLengkap.getText();
                String asalKota = fieldAsalKota.getText();
                String nomorPonsel = fieldNomorPonsel.getText();
                String email = fieldEmail.getText();
                
                if (fieldNamaLengkap.getText().equals("")
                        || fieldAsalKota.getText().equals("")
                        || fieldNomorPonsel.getText().equals("")
                        || fieldEmail.getText().equals("")) {
                    JOptionPane.showMessageDialog(panel, "Ada isian yg belum diisi!");
                    return;
                }

                if (absen.cekDuplikat(namaLengkap)) {
                    JOptionPane.showMessageDialog(panel, "Ada duplikat!");
                    return;
                }
                
                if (!absen.cekAsalKota(asalKota)) {
                    JOptionPane.showMessageDialog(panel,"Asal kota harus dari Jawa Timur");
                    return;
                }
                
                if (!absen.cekNomorPonsel(nomorPonsel)) {
                    JOptionPane.showMessageDialog(panel,"Error: Format nomor ponsel salah!");
                    return;
                }
                
                if (!absen.cekEmail(email)) {
                    JOptionPane.showMessageDialog(panel,"Error: Format email salah!");
                    return;
                }

                try {
                    Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/absen_pengunjung","root","");
                    cn.createStatement().executeUpdate("insert into daftar_pengunjung values('"+fieldNamaLengkap.getText()+"','"+fieldAsalKota.getText()+"','"+fieldNomorPonsel.getText()+"','"+fieldEmail.getText()+"')");
                    JOptionPane.showMessageDialog(null,"Absen Berhasil");
                    //cetakTabel();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Isi Form Dengan Benar");
                }
                //...

                absen.listPengunjung.add(new Pengunjung(namaLengkap, asalKota, nomorPonsel, email));

                fieldNamaLengkap.setText("");
                fieldAsalKota.setText("");
                fieldNomorPonsel.setText("");
                fieldEmail.setText("");
                
                updateTabel();
            }
        });
        /*
        tombolRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                cetakTabel();
            }
        });*/
    }
    
    private void cetakTabel(JScrollPane panelTabel){
        panelTabel.setBorder(BorderFactory.createTitledBorder("Data"));
    
        String[] kolom = {"Nama Lengkap", "Asal Kota", "Nomer Ponsel", "Email"};
        //String[][] isi = {{"Mirza","Lumajang","0823312341","mirza@gmail.com"}};
        tabelModel = new DefaultTableModel(kolom, 0);
        JTable tabel = new JTable(tabelModel);
        tabel.setPreferredScrollableViewportSize(new Dimension(540, 175));
        panelTabel.setViewportView(tabel);
        
        try {
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/absen_pengunjung","root","");
            ResultSet rs = cn.createStatement().executeQuery("SELECT * FROM daftar_pengunjung");
            while(rs.next()){
                String data [] = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)};
                tabelModel.addRow(data);
            }
        } catch (SQLException ex) {}
   }

    private void updateTabel() {
        
        Absen absen = new Absen();
        int row = tabelModel.getRowCount();
        for(int a = 0; a<row;a++){
            tabelModel.removeRow(0);
        }
        try {
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/absen_pengunjung","root","");
            ResultSet rs = cn.createStatement().executeQuery("SELECT * FROM daftar_pengunjung");
            while(rs.next()){
                String data [] = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)};
                tabelModel.addRow(data);
            }
        } catch (SQLException ex) {
        }
    }
}
