package Java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private Container c;
    private JLabel JL1,JL2,JL3;
    private JTextField JTF1;
    private JTextArea JTA1;
    private JButton JB,JB2;
    private Font f1;
    double F,C;



    Main(){
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Converter");
        initComponents();
    }
    public void initComponents(){

        c=this.getContentPane();
        c.setLayout(null);
        c.setBackground(Color.orange);

        f1=new Font("Bahnschrift SemiBold SemiConden",Font.BOLD,16);

        JL1=new JLabel("Temperature Converter");
        JL1.setBounds(150,50,180,30);
        JL1.setFont(f1);
        c.add(JL1);

        JL2=new JLabel("Farenheight Temperature");
        JL2.setBounds(100,100,180,30);
        c.add(JL2);

        JTF1=new JTextField();
        JTF1.setBounds(250,105,70,20);
        c.add(JTF1);

        JL2=new JLabel("Celcius Temperature");
        JL2.setBounds(100,150,180,30);
        c.add(JL2);

        JTA1=new JTextArea();
        JTA1.setBounds(220,155,70,20);
        c.add(JTA1);

        JB=new JButton("Convert");
        JB.setBounds(240,200,100,20);
        c.add(JB);

        JB2=new JButton("Clear");
        JB2.setBounds(120,200,80,20);
        c.add(JB2);


        JB2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTA1.setText("");
            }
        });


        JB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             String s1=JTF1.getText();
          if(s1.isEmpty()){
              JOptionPane.showMessageDialog(null,"Please input a number");
          }

          else{
              JTA1.setText(" ");
              F=Double.parseDouble(JTF1.getText());
              C=((5*(F-32)/9));
              JTA1.append(String.format("%.2f", C));
          }
            }
        });








    }







    public static void main(String[] args) {
        Main frame=new Main();
        frame.setVisible(true);
	// write your code here
    }
}
