package pattern;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.text.DecimalFormat;

public class Neural
{
        DecimalFormat df= new DecimalFormat("0.00");
        static int InputUnitNo=35;
        static int HiddenUnitNo=16;
        static int OutputUnitNo=4;
        static int MaxPatternNo=20;
        double Eta=0.75;
        double Alpha=0.8;
        double ErrorFunc=0.08;
        double Wmin=-0.30;
        double Wmax=0.30;
        static double [][]o1=new double[MaxPatternNo][InputUnitNo];
        double []o2=new double[HiddenUnitNo];
        static double []o3=new double[OutputUnitNo];
        double [][]t=new double[MaxPatternNo][OutputUnitNo];
        double [][]w21=new double[HiddenUnitNo][InputUnitNo];
        double [][]dw21=new double[HiddenUnitNo][InputUnitNo];
        double [][]w32=new double[OutputUnitNo][HiddenUnitNo];
        double [][]dw32=new double[OutputUnitNo][HiddenUnitNo];
        double []bias2=new double[HiddenUnitNo];
        double []dbias2=new double[HiddenUnitNo];
        double []bias3=new double[OutputUnitNo];
        double []dbias3=new double[OutputUnitNo];
        static int []node_max=new int[MaxPatternNo];
        static double error[]=new double [100];

        static int learning_pattern_no=4;
        static int test_pattern_no;//=8;
        static int count=1;

        public Neural(double[][] inp) throws FileNotFoundException,IOException
        {


          int i,j,k;
          double errorfunc;
         // read_file("Pattern.txt");
          o1=inp;
          t=new double[][]{{1,0,0,0},
                           {0,1,0,0},
                           {0,0,1,0},
                           {0,0,0,1}};
          initialize();

          System.out.println("***** Before Learning \n");
          System.out.println("pattern  output1  output2  output3  output4\n");
          for(errorfunc=0.0,i=0;i<learning_pattern_no;i++)
          {
              state(i);
             //delta ok
             for(j=0;j<OutputUnitNo; j++)
              errorfunc+=Math.pow(t[i][j]-o3[j],2);
          }

          errorfunc/=2;
          error[0]=errorfunc;
          System.out.println("ErrorFunction : "+df.format(errorfunc));
          System.out.println("\n***** Start to Learn *****\n");
          System.out.println(" counter  pattern  output1  output2  output3  output4\n");

          for(i=0;errorfunc>ErrorFunc;)
          {
            for(j=0;j<learning_pattern_no;j++)
            {
               propagation(j);
               back_propagation(j);

            }

            for(errorfunc=0,j=0;j<learning_pattern_no;j++)
            {
               i++;
               System.out.print(" "+i+" ");
               state(j);
               for(k=0;k<OutputUnitNo;k++)
                 errorfunc+=Math.pow(t[j][k]-o3[k],2);

            }
            errorfunc/=2;
            error[count]=errorfunc;
            
            System.out.println("ErrorFunction:  "+df.format(errorfunc));
            count++;
           }

            System.out.println("\n***** After Learning *****\n");
            System.out.println("pattern ouput1 output2 output3 output4 ");
            //execute and display test pattern
            for(i=0;i<learning_pattern_no+test_pattern_no;i++)
            {
              state(i);
              sort(i);
            }


        }

        public static void main(String arg[])
        {
                try
                {
                 double[][] noo=new double[12][35];
                 Neural neural=new Neural(noo);
                }

                catch(Exception ex)
                {}

        }
      /***  moving data from the input layer to the output layer***/
     public void propagation(int p)
     {
             int i,j;
             double net;

             /**** output value of hidden unit***/
             for(i=0;i<HiddenUnitNo;i++)
             {
              for(net=0,j=0;j<InputUnitNo;j++)
                  net+=w21[i][j]*o1[p][j];
               o2[i]=f(net+bias2[i]);
             }

       /**** output value of output unit***/
             for(i=0;i<OutputUnitNo;i++)
             {
              for(net=0,j=0;j<HiddenUnitNo;j++)
                  net+=w32[i][j]*o2[j];
               o3[i]=f(net+bias3[i]);
             }


     }

     /* correcting the weights*/

     public void back_propagation(int p)
     {
             int i,j;
             double d2[]=new double[HiddenUnitNo];
             double d3[]=new double[OutputUnitNo];
             double sum;


             for(i=0;i<OutputUnitNo;i++)
              d3[i]=(t[p][i]-o3[i])*o3[i]*(1-o3[i]);

        for(i=0;i<HiddenUnitNo;i++)
        {
                for(sum=0,j=0;j<OutputUnitNo;j++)
                {
                  dw32[j][i]=Eta*d3[j]*o2[i]+Alpha*dw32[j][i];
                  w32[j][i]+=dw32[j][i];
                  /*xx*/sum+=d3[j]*w32[j][i];
                }
                d2[i]=o2[i]*(1-o2[i])*sum;
        //	System.out.println("  "+i+" : "+d2[i]);
        }


        //correct thershold
        for(i=0;i<OutputUnitNo;i++)
        {
                dbias3[i]=Eta*d3[i]+Alpha*dbias3[i];
                bias3[i]+=dbias3[i];
        }

        for(i=0;i<InputUnitNo;i++)
         for(j=0;j<HiddenUnitNo;j++)
          {
                  dw21[j][i]=Eta*d2[j]*o1[p][i]+Alpha*dw21[j][i];
                  w21[j][i]+=dw21[j][i];
          }

          for(i=0;i<HiddenUnitNo;i++)
          {
                   dbias2[i]=Eta*d2[i]+Alpha*dbias2[i];
                   bias2[i]+=dbias2[i];
          }

     }

    //state
    public void state(int p)
    {
     int i,j;

     System.out.print((p+1)+"  ->");
     propagation(p);
     for(i=0;i<OutputUnitNo;i++)
      System.out.print("  "+df.format(o3[i]));

     System.out.println();

    }

    //*initializing the weights*//
    public void initialize()
    {

     int i,j;
     for(i=0;i<HiddenUnitNo;i++)
      for(j=0;j<InputUnitNo;j++)
       w21[i][j]=rnd();

     for(i=0;i<OutputUnitNo;i++)
      for(j=0;j<HiddenUnitNo;j++)
       w32[i][j]=rnd();

     for(i=0;i<HiddenUnitNo;i++)
       bias2[i]=rnd();

     for(i=0;i<OutputUnitNo;i++)
       bias3[i]=rnd();

     /*for(i=0;i<HiddenUnitNo;i++)
       bias2[i]=0;

     for(i=0;i<OutputUnitNo;i++)
       bias3[i]=0;*/

    }

    //*rnd method*//
    public double rnd()
    {
            return ((double)Math.random()*(Wmax-Wmin)+Wmin);
    }

    //*Sigmoid function*//
    public double f(double x)
    {
            return (1/(1+Math.exp(-(x))));
    }


    //sort method
     public static void sort(int p)
     {
       double target=0;
       for(int i=0;i<OutputUnitNo;i++)
       {
          if(target<o3[i])
          {
            target = o3[i];
            node_max[p]=i;
          }
       }
     }


 }




