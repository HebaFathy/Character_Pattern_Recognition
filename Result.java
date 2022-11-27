package pattern;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Result extends JFrame implements ActionListener
{
        private Font font=new Font("Serif",Font.BOLD,14);
        private Font font2=new Font("Serif",Font.ITALIC,13);

        private JFrame frame=new JFrame();
        private JPanel patternt1,patternt2,patternt3,patternt4,patternt5,patternt6,patternt7,patternt8,pattern_panel,west_panelt,north_panelt;
        private JLabel ttitle,tempty;
        private JButton outk,exit1,error1;

        private JButton matrixt1[][]=new JButton[8][35];
        private int num;
        private int index[]=new int[Neural.learning_pattern_no+Neural.test_pattern_no];
        private static double o1[][];
        
        //Colors
        Color color1=new Color(43,118,154);
        Color color2=new Color(164,185,207);
        
        //Constructor
        public Result(double x[][],int test_pattern_no)
        {
          o1=x;
          num=test_pattern_no;
          frame.setTitle("Output");
          patternt1=new JPanel();
          patternt2=new JPanel();
          patternt3=new JPanel();
          patternt4=new JPanel();
          patternt5=new JPanel();
          patternt6=new JPanel();
          patternt7=new JPanel();
          patternt8=new JPanel();
          pattern_panel=new JPanel();
          west_panelt=new JPanel();
          north_panelt=new JPanel();
          
          
          patternt1.setBackground(color2);
          patternt2.setBackground(color2);
          patternt3.setBackground(color2);
	      patternt4.setBackground(color2);
	      patternt5.setBackground(color2);
	      patternt6.setBackground(color2);
	      patternt7.setBackground(color2);
	      patternt8.setBackground(color2);


          patternt1.setLayout(new GridLayout(7,5));
          patternt2.setLayout(new GridLayout(7,5));
          patternt3.setLayout(new GridLayout(7,5));
          patternt4.setLayout(new GridLayout(7,5));
          patternt5.setLayout(new GridLayout(7,5));
          patternt6.setLayout(new GridLayout(7,5));
          patternt7.setLayout(new GridLayout(7,5));
          patternt8.setLayout(new GridLayout(7,5));

          for(int i=0;i<8;i++)
           for(int j=0;j<35;j++)
           {
               matrixt1[i][j] = new JButton("");
           }


         outk=new JButton("Show");
         exit1=new JButton("Exit");
         error1=new JButton("Error");

         ttitle=new JLabel("Output Patterns      ");
         tempty=new JLabel("");


         ttitle.setFont(font);
         outk.setFont(font2);
         exit1.setFont(font2);
         error1.setFont(font2);

         for(int i=0;i<35;i++)
              patternt1.add(matrixt1[0][i]);
         for(int i=0;i<35;i++)
              patternt2.add(matrixt1[1][i]);
         for(int i=0;i<35;i++)
              patternt3.add(matrixt1[2][i]);
         for(int i=0;i<35;i++)
              patternt4.add(matrixt1[3][i]);
         for(int i=0;i<35;i++)
            patternt5.add(matrixt1[4][i]);
         for(int i=0;i<35;i++)
            patternt6.add(matrixt1[5][i]);
         for(int i=0;i<35;i++)
            patternt7.add(matrixt1[6][i]);
         for(int i=0;i<35;i++)
            patternt8.add(matrixt1[7][i]);


         outk.addActionListener(this);
         exit1.addActionListener(this);
         error1.addActionListener(this);


         north_panelt.add(ttitle);
         north_panelt.add(tempty);
         north_panelt.setBackground(color2);

         pattern_panel.setLayout(new GridLayout(2,4,12,20));
         pattern_panel.setBackground(color2);
         pattern_panel.add(patternt1);
		 pattern_panel.add(patternt2);
		 pattern_panel.add(patternt3);
		 pattern_panel.add(patternt4);
		 pattern_panel.add(patternt5);
		 pattern_panel.add(patternt6);
		 pattern_panel.add(patternt7);
		 pattern_panel.add(patternt8);
         
         west_panelt.setLayout(new GridLayout(9,1,9,9));
         west_panelt.add(outk);
         west_panelt.add(error1);
         west_panelt.add(exit1);
         west_panelt.setBackground(color2);

	     Container c=frame.getContentPane();
	     c.add(north_panelt,BorderLayout.NORTH);
	     c.add(west_panelt,BorderLayout.EAST);
	     c.add(pattern_panel,BorderLayout.CENTER);

         this.setVisible(false);
         frame.setSize(400,310);
         frame.setLocation(150,70);
         frame.setVisible(true);

       }


        public void actionPerformed(ActionEvent e)
        {
         
            if(e.getSource()==outk)
           {
            // Output

            index=Neural.node_max;

             for(int i=4,j=0;j<num;i++,j++)
             {
                 if(index[i]==0)
                 {
                   for(int k=0;k<o1[0].length;k++)
                     if(o1[0][k]==1)
                         matrixt1[j][k].setBackground(Color.black);
                 }

                 if(index[i]==1)
                 {
                   for(int k=0;k<o1[0].length;k++)
                    if(o1[1][k]==1)
                         matrixt1[j][k].setBackground(Color.black);
                 }

                 if(index[i]==2)
                 {
                  for(int k=0;k<o1[0].length;k++)
                      if(o1[2][k]==1)
                         matrixt1[j][k].setBackground(Color.black);
                 }

                 if(index[i]==3)
                 {
                   for(int k=0;k<o1[0].length;k++)
                      if(o1[3][k]==1)
                         matrixt1[j][k].setBackground(Color.black);
                 }

               }

        }



       if(e.getSource()==exit1)
       {
         frame.setVisible(false);
       }

       if(e.getSource()==error1)
      {
        Error_Drawing error_drawing=new Error_Drawing(Neural.error,Neural.count);
      }


    }


        public static void main(String[] args) {
    Result out= new Result(o1,Neural.test_pattern_no);

   }


}
