package compiler;

import ast.*;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import parser.Parser;
//import constrain.Constrainer;
//import codegen.*;
import visitor.*;

/**
 *  The Compiler class contains the main program for compiling
 *  a source program to bytecodes
*/
public class Compiler {

/**
 * The Compiler class reads and compiles a source program
*/
	
	String sourceFile;
	
    public Compiler(String sourceFile) {
    	this.sourceFile = sourceFile;
    }
    
    /**
     * Main method to compile the program
     */
    void compileProgram() {
        try {
//            System.out.println("---------------TOKENS-------------");
            Parser parser = new Parser(sourceFile);
            AST t = parser.execute();
            System.out.println("---------------AST-------------");
            PrintVisitor pv = new PrintVisitor();
            t.accept(pv);

            // TREE DRAWING PART BEGINS HERE
            CountVisitor cv = new CountVisitor();
            t.accept(cv);
            DrawVisitor dv = new DrawVisitor(cv.getCount());
            t.accept(dv);            
            try {
                File imagefile = new File(sourceFile + ".png");
                ImageIO.write(dv.getImage(), "png", imagefile);
            } catch (Exception e) {System.out.println("Error in saving image: " + e.getMessage());}
            
            final JFrame f = new JFrame();
            f.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    f.dispose();
                    System.exit(0);
                }
            });
            JLabel imagelabel = new JLabel(new ImageIcon(dv.getImage()));
            f.add("Center",imagelabel);
            f.pack();
            f.setSize(new Dimension(dv.getImage().getWidth()+ 30,dv.getImage().getHeight() + 40));
            f.setVisible(true);
            f.setResizable(false);
            f.repaint();
            
        }catch (Exception e) {
            System.out.println("********exception*******"+e.toString());
         };
    }
    
    public static void main(String args[]) {
        if (args.length == 0) {
            System.out.println("***Incorrect usage, try: java compiler.Compiler <file>");
            System.exit(1);
        }
        (new Compiler(args[0])).compileProgram();
    }
}
