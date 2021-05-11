
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.*;

public class JavaGUICompiler extends JFrame {
	private static final long serialVersionUID = 1L;
	public String class_name;
	StringBuffer buffer;
	Process process;
	BufferedReader bufferedReader;
	StringBuffer readBuffer;

	JavaCompiler compiler;
	Clipboard c;

	JFrame f = new JFrame();
	JTextArea SourceText = new JTextArea("\r\n"
			+ "import javax.swing.*;\r\n"
			+ "import java.awt.*;\r\n"
			+ "import java.awt.datatransfer.StringSelection;\r\n"
			+ "import java.awt.datatransfer.Clipboard;\r\n"
			+ "import java.awt.datatransfer.DataFlavor;\r\n"
			+ "import java.awt.datatransfer.Transferable;\r\n"
			+ "import java.awt.event.ActionEvent;\r\n"
			+ "import java.awt.event.ActionListener;\r\n"
			+ "import java.io.*;\r\n"
			+ "import java.util.regex.Matcher;\r\n"
			+ "import java.util.regex.Pattern;\r\n"
			+ "\r\n"
			+ "import javax.tools.*;\r\n"
			+ "\r\n"
			+ "public class JavaGUICompiler extends JFrame {\r\n"
			+ "	private static final long serialVersionUID = 1L;\r\n"
			+ "	public String class_name;\r\n"
			+ "	StringBuffer buffer;\r\n"
			+ "	Process process;\r\n"
			+ "	BufferedReader bufferedReader;\r\n"
			+ "	StringBuffer readBuffer;\r\n"
			+ "\r\n"
			+ "	JavaCompiler compiler;\r\n"
			+ "	Clipboard c;\r\n"
			+ "\r\n"
			+ "	JFrame f = new JFrame();\r\n"
			+ "	JTextArea SourceText = new JTextArea(\"\");\r\n"
			+ "\r\n"
			+ "	JTextArea tf1 = new JTextArea();\r\n"
			+ "	JTextArea tf2 = new JTextArea();\r\n"
			+ "	\r\n"
			+ "	JScrollPane scrollSource = new JScrollPane(SourceText);\r\n"
			+ "	JScrollPane scrollTf1 = new JScrollPane(tf1);\r\n"
			+ "	JScrollPane scrollTf2 = new JScrollPane(tf2);\r\n"
			+ "\r\n"
			+ "	//	메뉴바\r\n"
			+ "	JMenuBar mb = new JMenuBar();\r\n"
			+ "	JMenu fileMenu = new JMenu(\"File\");\r\n"
			+ "	JMenu editMenu = new JMenu(\"Edit\");\r\n"
			+ "	JMenu compileMenu = new JMenu(\"Compile\");\r\n"
			+ "\r\n"
			+ "	//	메뉴바 - 메뉴 아이템\r\n"
			+ "	JFileChooser chooser;\r\n"
			+ "	JMenuItem NewMenu = new JMenuItem(\"New\");\r\n"
			+ "	JMenuItem OpenMenu = new JMenuItem(\"Open\");\r\n"
			+ "	JMenuItem SaveMenu = new JMenuItem(\"Save\");\r\n"
			+ "	JMenuItem CreditMenu = new JMenuItem(\"Credit\");\r\n"
			+ "	JMenuItem ExitMenu = new JMenuItem(\"Exit\");\r\n"
			+ "\r\n"
			+ "	JMenuItem CopyMenu = new JMenuItem(\"Copy\");\r\n"
			+ "	JMenuItem PasteMenu = new JMenuItem(\"Paste\");\r\n"
			+ "	JMenuItem CutMenu = new JMenuItem(\"Cut\");\r\n"
			+ "\r\n"
			+ "	JMenuItem CompileMenu = new JMenuItem(\"Compile\");\r\n"
			+ "	JMenuItem RunMenu = new JMenuItem(\"Run\");\r\n"
			+ "	JMenuItem SourceMenu = new JMenuItem(\"Source Code\");\r\n"
			+ "\r\n"
			+ "	/**\r\n"
			+ "	 * Launch the application.\r\n"
			+ "	 */\r\n"
			+ "	public static void main(String[] args) {\r\n"
			+ "		new JavaGUICompiler();\r\n"
			+ "	}\r\n"
			+ "\r\n"
			+ "	/**\r\n"
			+ "	 * Create the application.\r\n"
			+ "	 */\r\n"
			+ "	public JavaGUICompiler() {\r\n"
			+ "		this.setTitle(\"자바 컴파일러\");\r\n"
			+ "		this.setSize(500, 500);\r\n"
			+ "		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);\r\n"
			+ "\r\n"
			+ "		//		메뉴바 생성 메소드\r\n"
			+ "		CreateMenuBar();\r\n"
			+ "		//		텍스크 구역 생성 메소드\r\n"
			+ "		CreateTextField();\r\n"
			+ "		this.setVisible(true);\r\n"
			+ "	}\r\n"
			+ "\r\n"
			+ "	/**\r\n"
			+ "	 * Initialize the contents of the frame.\r\n"
			+ "	 */\r\n"
			+ "	public void CreateMenuBar() {\r\n"
			+ "\r\n"
			+ "		// 메뉴바 추가\r\n"
			+ "		mb.add(fileMenu);\r\n"
			+ "		mb.add(editMenu);\r\n"
			+ "		mb.add(compileMenu);\r\n"
			+ "\r\n"
			+ "		// 파일 메뉴바 메뉴아이템 추가\r\n"
			+ "		fileMenu.add(NewMenu);\r\n"
			+ "		fileMenu.add(OpenMenu);\r\n"
			+ "		fileMenu.add(SaveMenu);\r\n"
			+ "		fileMenu.add(CreditMenu);\r\n"
			+ "		fileMenu.add(ExitMenu);\r\n"
			+ "\r\n"
			+ "		// 수정 메뉴바 메뉴아이템 추가\r\n"
			+ "		editMenu.add(CopyMenu);\r\n"
			+ "		editMenu.add(PasteMenu);\r\n"
			+ "		editMenu.add(CutMenu);\r\n"
			+ "\r\n"
			+ "		// 컴파일 메뉴바 메뉴 아이템 추가\r\n"
			+ "		compileMenu.add(CompileMenu);\r\n"
			+ "		compileMenu.add(RunMenu);\r\n"
			+ "		compileMenu.add(SourceMenu);\r\n"
			+ "\r\n"
			+ "		//		new메뉴 이벤트 처리\r\n"
			+ "		NewMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				tf1.setText(\"\");\r\n"
			+ "				tf2.setText(\"\");\r\n"
			+ "			}\r\n"
			+ "		});\r\n"
			+ "\r\n"
			+ "		//		Open메뉴 이벤트 처리\r\n"
			+ "		OpenMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				chooser = new JFileChooser();\r\n"
			+ "				int retval = chooser.showOpenDialog(null);\r\n"
			+ "				if (retval != JFileChooser.APPROVE_OPTION) {\r\n"
			+ "					JOptionPane.showMessageDialog(null, \"파일을 선택하지 않았음.\", \"경고\", JOptionPane.WARNING_MESSAGE);\r\n"
			+ "					return;\r\n"
			+ "				}\r\n"
			+ "				File file = null;\r\n"
			+ "				if (retval == JFileChooser.APPROVE_OPTION) {\r\n"
			+ "					file = chooser.getSelectedFile();\r\n"
			+ "				}\r\n"
			+ "				BufferedReader br = null;\r\n"
			+ "				try {\r\n"
			+ "					br = new BufferedReader(new FileReader(file));\r\n"
			+ "					String line;\r\n"
			+ "					while ((line = br.readLine()) != null) {\r\n"
			+ "						tf1.append(line + \"\\n\");\r\n"
			+ "					}\r\n"
			+ "				} catch (Exception e1) {\r\n"
			+ "					e1.printStackTrace();\r\n"
			+ "				}\r\n"
			+ "\r\n"
			+ "			}\r\n"
			+ "		});\r\n"
			+ "\r\n"
			+ "		//		Save 메뉴 이벤트 처리\r\n"
			+ "		SaveMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				//				상동\r\n"
			+ "				chooser = new JFileChooser();\r\n"
			+ "				chooser.setDialogTitle(\"Save\");\r\n"
			+ "				int ret = chooser.showOpenDialog(null);\r\n"
			+ "				if (ret != JFileChooser.APPROVE_OPTION) {\r\n"
			+ "					JOptionPane.showMessageDialog(null, \"파일을 선택하지 않았습니다.\", \"경고!\", JOptionPane.WARNING_MESSAGE);\r\n"
			+ "					return;\r\n"
			+ "				}\r\n"
			+ "				//				Text 파일로 윗부분 텍스트입력 부분 입력된 문자 저장\r\n"
			+ "				try (FileWriter fw = new FileWriter(chooser.getSelectedFile() + \".txt\")) {\r\n"
			+ "					tf1.write(fw);\r\n"
			+ "					fw.close();\r\n"
			+ "				} catch (Exception e2) {\r\n"
			+ "					e2.printStackTrace();\r\n"
			+ "				}\r\n"
			+ "			}\r\n"
			+ "		});\r\n"
			+ "\r\n"
			+ "		CreditMenu.addActionListener(new ActionListener () {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				JOptionPane.showMessageDialog(null, \"컴퓨터공학과\\n205113 박정후\\n송민선\", \"Credit\", JOptionPane.QUESTION_MESSAGE);\r\n"
			+ "			}\r\n"
			+ "		});\r\n"
			+ "		\r\n"
			+ "		//		Exit메뉴 이벤트 처리\r\n"
			+ "		ExitMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				System.exit(0);\r\n"
			+ "			}\r\n"
			+ "\r\n"
			+ "		});\r\n"
			+ "\r\n"
			+ "		//		Copy메뉴 이벤트 추가\r\n"
			+ "		CopyMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				StringSelection stringSelection = new StringSelection(tf1.getText());\r\n"
			+ "				c = Toolkit.getDefaultToolkit().getSystemClipboard();\r\n"
			+ "				c.setContents(stringSelection, null);\r\n"
			+ "			}\r\n"
			+ "		});\r\n"
			+ "\r\n"
			+ "		//		Paste메뉴 이벤트 추가\r\n"
			+ "		PasteMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				c = Toolkit.getDefaultToolkit().getSystemClipboard();\r\n"
			+ "				Transferable text = c.getContents(this);\r\n"
			+ "				try {\r\n"
			+ "					String s = (String) text.getTransferData(DataFlavor.stringFlavor);\r\n"
			+ "					tf1.setText(s);\r\n"
			+ "				} catch (Throwable e3) {\r\n"
			+ "					e3.printStackTrace();\r\n"
			+ "				}\r\n"
			+ "			}\r\n"
			+ "		});\r\n"
			+ "		//		Cut메뉴 이벤트 추가\r\n"
			+ "		CutMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				StringSelection StringSelection = new StringSelection(tf1.getText());\r\n"
			+ "				c = Toolkit.getDefaultToolkit().getSystemClipboard();\r\n"
			+ "				c.setContents(StringSelection, null);\r\n"
			+ "				tf1.setText(\"\");\r\n"
			+ "			}\r\n"
			+ "		});\r\n"
			+ "\r\n"
			+ "//		컴파일 진행시 클래스명 가져와서 파일이름으로 설정 추가\r\n"
			+ "		CompileMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				tf2.setText(\"\");\r\n"
			+ "				class_name = tf1.getText();\r\n"
			+ "//				public class 와 { 사이 클래스 이름 가져오기\r\n"
			+ "				Pattern pattern = Pattern.compile(\"(\\\\bpublic class \\\\b)(.*?)[ {]\");\r\n"
			+ "				Matcher matcher = pattern.matcher(class_name);\r\n"
			+ "				if (matcher.find()) {\r\n"
			+ "					class_name = matcher.group(2);\r\n"
			+ "				} else {\r\n"
			+ "					tf2.setText(\"Class 이름 추출 실패\");\r\n"
			+ "					return;\r\n"
			+ "				}\r\n"
			+ "				try (FileWriter fw = new FileWriter(class_name + \".java\")) {\r\n"
			+ "					tf1.write(fw);\r\n"
			+ "					fw.close();\r\n"
			+ "				} catch (Exception e2) {\r\n"
			+ "					e2.printStackTrace();\r\n"
			+ "				}\r\n"
			+ "				try {\r\n"
			+ "					String line;\r\n"
			+ "					InputStream is;\r\n"
			+ "					is = Runtime.getRuntime().exec(\"javac \" + class_name + \".java\").getInputStream();\r\n"
			+ "					BufferedReader br = new BufferedReader(new InputStreamReader(is, \"MS949\"));\r\n"
			+ "					while ((line = br.readLine()) != null) {\r\n"
			+ "						tf2.setText(line);\r\n"
			+ "					}\r\n"
			+ "					br.close();\r\n"
			+ "					is.close();\r\n"
			+ "				} catch (IOException e4) {\r\n"
			+ "					e4.printStackTrace();\r\n"
			+ "				}\r\n"
			+ "			}\r\n"
			+ "		});\r\n"
			+ "\r\n"
			+ "		RunMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				try {\r\n"
			+ "					String line;\r\n"
			+ "					InputStream is;\r\n"
			+ "//					public class, { 사이 클래스 명으로 실행\r\n"
			+ "					is = Runtime.getRuntime().exec(\"java \" + class_name).getInputStream();\r\n"
			+ "					BufferedReader br = new BufferedReader(new InputStreamReader(is, \"MS949\"));\r\n"
			+ "					while ((line = br.readLine()) != null) {\r\n"
			+ "						tf2.setText(line);\r\n"
			+ "					}\r\n"
			+ "					br.close();\r\n"
			+ "					is.close();\r\n"
			+ "				} catch (IOException e4) {\r\n"
			+ "					e4.printStackTrace();\r\n"
			+ "				}\r\n"
			+ "			}\r\n"
			+ "		});\r\n"
			+ "		\r\n"
			+ "		SourceMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				SourceText.setLineWrap(true);  \r\n"
			+ "				SourceText.setWrapStyleWord(true); \r\n"
			+ "				scrollSource.setPreferredSize( new Dimension( 500, 500 ) );\r\n"
			+ "				JOptionPane.showMessageDialog(null, scrollSource, \"Credit\", getDefaultCloseOperation());\r\n"
			+ "			}\r\n"
			+ "		});\r\n"
			+ "\r\n"
			+ "		this.setJMenuBar(mb);\r\n"
			+ "	}\r\n"
			+ "\r\n"
			+ "	public void CreateTextField() {\r\n"
			+ "		// 수평 1:1 텍스트-스크롤패널 분할\r\n"
			+ "		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTf1, scrollTf2);\r\n"
			+ "		splitPane.setResizeWeight(0.5);\r\n"
			+ "		add(splitPane);\r\n"
			+ "	}\r\n"
			+ "}\r\n"
			+ "");

	JTextArea tf1 = new JTextArea();
	JTextArea tf2 = new JTextArea();
	
	JScrollPane scrollSource = new JScrollPane(SourceText);
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
	JMenuItem CreditMenu = new JMenuItem("Credit");
	JMenuItem ExitMenu = new JMenuItem("Exit");

	JMenuItem CopyMenu = new JMenuItem("Copy");
	JMenuItem PasteMenu = new JMenuItem("Paste");
	JMenuItem CutMenu = new JMenuItem("Cut");

	JMenuItem CompileMenu = new JMenuItem("Compile");
	JMenuItem RunMenu = new JMenuItem("Run");
	JMenuItem SourceMenu = new JMenuItem("Source Code");

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
		this.setSize(500, 500);
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

		// 메뉴바 추가
		mb.add(fileMenu);
		mb.add(editMenu);
		mb.add(compileMenu);

		// 파일 메뉴바 메뉴아이템 추가
		fileMenu.add(NewMenu);
		fileMenu.add(OpenMenu);
		fileMenu.add(SaveMenu);
		fileMenu.add(CreditMenu);
		fileMenu.add(ExitMenu);

		// 수정 메뉴바 메뉴아이템 추가
		editMenu.add(CopyMenu);
		editMenu.add(PasteMenu);
		editMenu.add(CutMenu);

		// 컴파일 메뉴바 메뉴 아이템 추가
		compileMenu.add(CompileMenu);
		compileMenu.add(RunMenu);
		compileMenu.add(SourceMenu);

		//		new메뉴 이벤트 처리
		NewMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf1.setText("");
				tf2.setText("");
			}
		});

		//		Open메뉴 이벤트 처리
		OpenMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser = new JFileChooser();
				int retval = chooser.showOpenDialog(null);
				if (retval != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "파일을 선택하지 않았음.", "경고", JOptionPane.WARNING_MESSAGE);
					return;
				}
				File file = null;
				if (retval == JFileChooser.APPROVE_OPTION) {
					file = chooser.getSelectedFile();
				}
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(file));
					String line;
					while ((line = br.readLine()) != null) {
						tf1.append(line + "\n");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		//		Save 메뉴 이벤트 처리
		SaveMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//				상동
				chooser = new JFileChooser();
				chooser.setDialogTitle("Save");
				int ret = chooser.showOpenDialog(null);
				if (ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고!", JOptionPane.WARNING_MESSAGE);
					return;
				}
				//				Text 파일로 윗부분 텍스트입력 부분 입력된 문자 저장
				try (FileWriter fw = new FileWriter(chooser.getSelectedFile() + ".txt")) {
					tf1.write(fw);
					fw.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});

		CreditMenu.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "컴퓨터공학과\n205113 박정후\n203913 송민선", "Credit", JOptionPane.QUESTION_MESSAGE);
			}
		});
		
		//		Exit메뉴 이벤트 처리
		ExitMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

		//		Copy메뉴 이벤트 추가
		CopyMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection stringSelection = new StringSelection(tf1.getText());
				c = Toolkit.getDefaultToolkit().getSystemClipboard();
				c.setContents(stringSelection, null);
			}
		});

		//		Paste메뉴 이벤트 추가
		PasteMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable text = c.getContents(this);
				try {
					String s = (String) text.getTransferData(DataFlavor.stringFlavor);
					tf1.setText(s);
				} catch (Throwable e3) {
					e3.printStackTrace();
				}
			}
		});
		//		Cut메뉴 이벤트 추가
		CutMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection StringSelection = new StringSelection(tf1.getText());
				c = Toolkit.getDefaultToolkit().getSystemClipboard();
				c.setContents(StringSelection, null);
				tf1.setText("");
			}
		});

//		컴파일 진행시 클래스명 가져와서 파일이름으로 설정 추가
		CompileMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf2.setText("");
				class_name = tf1.getText();
//				public class 와 { 사이 클래스 이름 가져오기
				Pattern pattern = Pattern.compile("(\\bpublic class \\b)(.*?)[ {]");
				Matcher matcher = pattern.matcher(class_name);
				if (matcher.find()) {
					class_name = matcher.group(2);
				} else {
					tf2.setText("Class 이름 추출 실패");
					return;
				}
				try (FileWriter fw = new FileWriter(class_name + ".java")) {
					tf1.write(fw);
					fw.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				try {
					String line;
					InputStream is;
					is = Runtime.getRuntime().exec("javac " + class_name + ".java").getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is, "MS949"));
					while ((line = br.readLine()) != null) {
						tf2.setText(line);
					}
					br.close();
					is.close();
				} catch (IOException e4) {
					e4.printStackTrace();
				}
			}
		});

		RunMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String line;
					InputStream is;
//					public class, { 사이 클래스 명으로 실행
					is = Runtime.getRuntime().exec("java " + class_name).getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is, "MS949"));
					while ((line = br.readLine()) != null) {
						tf2.setText(line);
					}
					br.close();
					is.close();
				} catch (IOException e4) {
					e4.printStackTrace();
				}
			}
		});
		
		SourceMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SourceText.setLineWrap(true);  
				SourceText.setWrapStyleWord(true); 
				scrollSource.setPreferredSize( new Dimension( 500, 500 ) );
				JOptionPane.showMessageDialog(null, scrollSource, "Source Code", getDefaultCloseOperation());
			}
		});

		this.setJMenuBar(mb);
	}

	public void CreateTextField() {
		// 수평 1:1 텍스트-스크롤패널 분할
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTf1, scrollTf2);
		splitPane.setResizeWeight(0.5);
		add(splitPane);
	}
}
