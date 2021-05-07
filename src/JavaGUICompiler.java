import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.tools.*;

public class JavaGUICompiler extends JFrame {
	private static final long serialVersionUID = 1L;
	StringBuffer buffer;
	Process process;
	BufferedReader bufferedReader;
	StringBuffer readBuffer;
	
	JavaCompiler compiler;
	Clipboard c;
	
	JFrame f = new JFrame();
	JTextArea tf1 = new JTextArea();
	JTextArea tf2 = new JTextArea();
	
	JScrollPane scrollTf1 = new JScrollPane(tf1);
	JScrollPane scrollTf2 = new JScrollPane(tf2);
	
//	∏ﬁ¥∫πŸ
	JMenuBar mb = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenu editMenu = new JMenu("Edit");
	JMenu compileMenu = new JMenu("Compile");

//	∏ﬁ¥∫πŸ - ∏ﬁ¥∫ æ∆¿Ã≈€
	JFileChooser chooser;
	JMenuItem NewMenu = new JMenuItem("New");
	JMenuItem OpenMenu = new JMenuItem("Open");
	JMenuItem SaveMenu = new JMenuItem("Save");
	JMenuItem EditMenu = new JMenuItem("Edit");
	
	JMenuItem CopyMenu = new JMenuItem("Copy");
	JMenuItem PasteMenu = new JMenuItem("Paste");
	JMenuItem CutMenu = new JMenuItem("Cut");
	
	JMenuItem CompileMenu = new JMenuItem("Compile");
	JMenuItem RunMenu = new JMenuItem("Run");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new JavaGUICompiler();
	}

	/**
	 * Create the application.
	 */
	public JavaGUICompiler() {
		this.setTitle("ÏûêÎ∞î Ïª¥ÌååÏùºÎü¨");
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		∏ﬁ¥∫πŸ ª˝º∫ ∏ﬁº“µÂ
		CreateMenuBar();
//		≈ÿΩ∫≈© ±∏ø™ ª˝º∫ ∏ﬁº“µÂ
		CreateTextField();
		this.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
public void CreateMenuBar() {

//		∏ﬁ¥∫πŸ √ﬂ∞°
		mb.add(fileMenu);
		mb.add(compileMenu);
		mb.add(editMenu);
		
//		∆ƒ¿œ ∏ﬁ¥∫πŸ ∏ﬁ¥∫æ∆¿Ã≈€ √ﬂ∞°
		fileMenu.add(NewMenu);
		fileMenu.add(OpenMenu);
		fileMenu.add(SaveMenu);
		fileMenu.add(EditMenu);

//		ºˆ¡§ ∏ﬁ¥∫πŸ ∏ﬁ¥∫æ∆¿Ã≈€ √ﬂ∞°
		editMenu.add(CopyMenu);
		editMenu.add(PasteMenu);
		editMenu.add(CutMenu);
		
//		ƒƒ∆ƒ¿œ ∏ﬁ¥∫πŸ ∏ﬁ¥∫ æ∆¿Ã≈€ √ﬂ∞°
		compileMenu.add(CompileMenu);
		compileMenu.add(RunMenu);
		
		this.setJMenuBar(mb);
	}

public void CreateTextField() {
//	ºˆ∆Ú 1:1 ≈ÿΩ∫∆Æ-Ω∫≈©∑—∆–≥Œ ∫–«“ 
	JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTf1, scrollTf2);
	splitPane.setResizeWeight(0.5);
	add(splitPane);
}

}
