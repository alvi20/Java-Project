package Java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private Container c;
    private JLabel JL,imgJL;
    private JButton JB,JB2;
    private JTextField JF1;
    private JTextArea JT;
    private Font JF;
    private ImageIcon img;



    Main(){
      c=this.getContentPane();
      c.setBackground(Color.orange);
      c.setLayout(null);

        initComponents();
    }

    public void initComponents(){

        img=new ImageIcon(getClass().getResource("image3.jpg"));

        imgJL=new JLabel(img);
        imgJL.setBounds(20,10,img.getIconWidth(),img.getIconHeight());
        c.add(imgJL);

        JF=new Font("Arial",Font.BOLD,13);
        JL=new JLabel("Enter Number: ");
        JL.setBounds(50,200,100,100);
        JL.setFont(JF);
        c.add(JL);

        JF1=new JTextField();
        JF1.setBackground(Color.yellow);
        JF1.setBounds(150,230,100,50);
        JF1.setFont(JF);
        c.add(JF1);

        JB=new JButton("Submit");
        JB.setFont(JF);
        JB.setBounds(200,300,80,20);
        c.add(JB);

        JB2=new JButton("Clear");
        JB2.setFont(JF);
        JB2.setBounds(110,300,80,20);
        c.add(JB2);

        JT=new JTextArea();
        JT.setBounds(50,400,250,250);
        JT.setFont(JF);
        JT.setBackground(Color.GREEN);
        c.add(JT);

        JF1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String value=JF1.getText();
                if(value.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Please put any number!!");
                }
                else{
                    JT.setText("");
                    int num=Integer.parseInt(JF1.getText());
                    for(int i=1;i<=10;i++){
                        int result=num*i;
                        String r=String.valueOf(result);
                        String n=String.valueOf(num);
                        String inc=String.valueOf(i);

                        JT.append(n+" X "+inc + " = "+r+"\n");

                    }
                }




            }
        });

       JB2.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               JT.setText("");
           }
       });


    }


    public static void main(String[] args) {
        Main frame=new Main();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setBounds(300,20,360,700);
    }
}
