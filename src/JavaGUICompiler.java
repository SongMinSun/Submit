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
	
//	메뉴바
	JMenuBar mb = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenu editMenu = new JMenu("Edit");
	JMenu compileMenu = new JMenu("Compile");

//	메뉴바 - 메뉴 아이템
	JFileChooser chooser;
	JMenuItem NewMenu = new JMenuItem("New");
	JMenuItem OpenMenu = new JMenuItem("Open");
	JMenuItem SaveMenu = new JMenuItem("Save");
	JMenuItem ExitMenu = new JMenuItem("Exit");
	
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
		this.setTitle("자바 컴파일러");
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

//		메뉴바 생성 메소드
		CreateMenuBar();
//		텍스크 구역 생성 메소드
		CreateTextField();
		this.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void CreateMenuBar() {

			//		메뉴바 추가
			mb.add(fileMenu);
			mb.add(compileMenu);
			mb.add(editMenu);
		
			//		파일 메뉴바 메뉴아이템 추가
			fileMenu.add(NewMenu);
			fileMenu.add(OpenMenu);
			fileMenu.add(SaveMenu);
			fileMenu.add(ExitMenu);

			//		수정 메뉴바 메뉴아이템 추가
			editMenu.add(CopyMenu);
			editMenu.add(PasteMenu);
			editMenu.add(CutMenu);
		
			//		컴파일 메뉴바 메뉴 아이템 추가
			compileMenu.add(CompileMenu);
			compileMenu.add(RunMenu);
		
			this.setJMenuBar(mb);
		
		NewMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf1.setText("");
				tf2.setText("");
			}
		});
		
		OpenMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser = new JFileChooser();
				int retval = chooser.showOpenDialog(null);
				if(retval != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "파일을 선택하지 않았음.","경고",JOptionPane.WARNING_MESSAGE);
					return;
				}
				File file = null;
				if(retval == JFileChooser.APPROVE_OPTION) {
					file = chooser.getSelectedFile();
				}
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(file));
					String line;
					while((line = br.readLine()) != null) {
						tf1.append(line + "\n");
					}
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		SaveMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		ExitMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		CopyMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		PasteMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		CutMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		CompileMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		RunMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		this.setJMenuBar(mb);
		
	}
	
	public void CreateTextField() {
		//	수평 1:1 텍스트-스크롤패널 분할 
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTf1, scrollTf2);
		splitPane.setResizeWeight(0.5);
		add(splitPane);
	}

	}
