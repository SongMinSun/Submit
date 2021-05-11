
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
			+ "	//	�޴���\r\n"
			+ "	JMenuBar mb = new JMenuBar();\r\n"
			+ "	JMenu fileMenu = new JMenu(\"File\");\r\n"
			+ "	JMenu editMenu = new JMenu(\"Edit\");\r\n"
			+ "	JMenu compileMenu = new JMenu(\"Compile\");\r\n"
			+ "\r\n"
			+ "	//	�޴��� - �޴� ������\r\n"
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
			+ "		this.setTitle(\"�ڹ� �����Ϸ�\");\r\n"
			+ "		this.setSize(500, 500);\r\n"
			+ "		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);\r\n"
			+ "\r\n"
			+ "		//		�޴��� ���� �޼ҵ�\r\n"
			+ "		CreateMenuBar();\r\n"
			+ "		//		�ؽ�ũ ���� ���� �޼ҵ�\r\n"
			+ "		CreateTextField();\r\n"
			+ "		this.setVisible(true);\r\n"
			+ "	}\r\n"
			+ "\r\n"
			+ "	/**\r\n"
			+ "	 * Initialize the contents of the frame.\r\n"
			+ "	 */\r\n"
			+ "	public void CreateMenuBar() {\r\n"
			+ "\r\n"
			+ "		// �޴��� �߰�\r\n"
			+ "		mb.add(fileMenu);\r\n"
			+ "		mb.add(editMenu);\r\n"
			+ "		mb.add(compileMenu);\r\n"
			+ "\r\n"
			+ "		// ���� �޴��� �޴������� �߰�\r\n"
			+ "		fileMenu.add(NewMenu);\r\n"
			+ "		fileMenu.add(OpenMenu);\r\n"
			+ "		fileMenu.add(SaveMenu);\r\n"
			+ "		fileMenu.add(CreditMenu);\r\n"
			+ "		fileMenu.add(ExitMenu);\r\n"
			+ "\r\n"
			+ "		// ���� �޴��� �޴������� �߰�\r\n"
			+ "		editMenu.add(CopyMenu);\r\n"
			+ "		editMenu.add(PasteMenu);\r\n"
			+ "		editMenu.add(CutMenu);\r\n"
			+ "\r\n"
			+ "		// ������ �޴��� �޴� ������ �߰�\r\n"
			+ "		compileMenu.add(CompileMenu);\r\n"
			+ "		compileMenu.add(RunMenu);\r\n"
			+ "		compileMenu.add(SourceMenu);\r\n"
			+ "\r\n"
			+ "		//		new�޴� �̺�Ʈ ó��\r\n"
			+ "		NewMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				tf1.setText(\"\");\r\n"
			+ "				tf2.setText(\"\");\r\n"
			+ "			}\r\n"
			+ "		});\r\n"
			+ "\r\n"
			+ "		//		Open�޴� �̺�Ʈ ó��\r\n"
			+ "		OpenMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				chooser = new JFileChooser();\r\n"
			+ "				int retval = chooser.showOpenDialog(null);\r\n"
			+ "				if (retval != JFileChooser.APPROVE_OPTION) {\r\n"
			+ "					JOptionPane.showMessageDialog(null, \"������ �������� �ʾ���.\", \"���\", JOptionPane.WARNING_MESSAGE);\r\n"
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
			+ "		//		Save �޴� �̺�Ʈ ó��\r\n"
			+ "		SaveMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				//				��\r\n"
			+ "				chooser = new JFileChooser();\r\n"
			+ "				chooser.setDialogTitle(\"Save\");\r\n"
			+ "				int ret = chooser.showOpenDialog(null);\r\n"
			+ "				if (ret != JFileChooser.APPROVE_OPTION) {\r\n"
			+ "					JOptionPane.showMessageDialog(null, \"������ �������� �ʾҽ��ϴ�.\", \"���!\", JOptionPane.WARNING_MESSAGE);\r\n"
			+ "					return;\r\n"
			+ "				}\r\n"
			+ "				//				Text ���Ϸ� ���κ� �ؽ�Ʈ�Է� �κ� �Էµ� ���� ����\r\n"
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
			+ "				JOptionPane.showMessageDialog(null, \"��ǻ�Ͱ��а�\\n205113 ������\\n�۹μ�\", \"Credit\", JOptionPane.QUESTION_MESSAGE);\r\n"
			+ "			}\r\n"
			+ "		});\r\n"
			+ "		\r\n"
			+ "		//		Exit�޴� �̺�Ʈ ó��\r\n"
			+ "		ExitMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				System.exit(0);\r\n"
			+ "			}\r\n"
			+ "\r\n"
			+ "		});\r\n"
			+ "\r\n"
			+ "		//		Copy�޴� �̺�Ʈ �߰�\r\n"
			+ "		CopyMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				StringSelection stringSelection = new StringSelection(tf1.getText());\r\n"
			+ "				c = Toolkit.getDefaultToolkit().getSystemClipboard();\r\n"
			+ "				c.setContents(stringSelection, null);\r\n"
			+ "			}\r\n"
			+ "		});\r\n"
			+ "\r\n"
			+ "		//		Paste�޴� �̺�Ʈ �߰�\r\n"
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
			+ "		//		Cut�޴� �̺�Ʈ �߰�\r\n"
			+ "		CutMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				StringSelection StringSelection = new StringSelection(tf1.getText());\r\n"
			+ "				c = Toolkit.getDefaultToolkit().getSystemClipboard();\r\n"
			+ "				c.setContents(StringSelection, null);\r\n"
			+ "				tf1.setText(\"\");\r\n"
			+ "			}\r\n"
			+ "		});\r\n"
			+ "\r\n"
			+ "//		������ ����� Ŭ������ �����ͼ� �����̸����� ���� �߰�\r\n"
			+ "		CompileMenu.addActionListener(new ActionListener() {\r\n"
			+ "			public void actionPerformed(ActionEvent e) {\r\n"
			+ "				tf2.setText(\"\");\r\n"
			+ "				class_name = tf1.getText();\r\n"
			+ "//				public class �� { ���� Ŭ���� �̸� ��������\r\n"
			+ "				Pattern pattern = Pattern.compile(\"(\\\\bpublic class \\\\b)(.*?)[ {]\");\r\n"
			+ "				Matcher matcher = pattern.matcher(class_name);\r\n"
			+ "				if (matcher.find()) {\r\n"
			+ "					class_name = matcher.group(2);\r\n"
			+ "				} else {\r\n"
			+ "					tf2.setText(\"Class �̸� ���� ����\");\r\n"
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
			+ "//					public class, { ���� Ŭ���� ������ ����\r\n"
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
			+ "		// ���� 1:1 �ؽ�Ʈ-��ũ���г� ����\r\n"
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

	//	�޴���
	JMenuBar mb = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenu editMenu = new JMenu("Edit");
	JMenu compileMenu = new JMenu("Compile");

	//	�޴��� - �޴� ������
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
		this.setTitle("�ڹ� �����Ϸ�");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//		�޴��� ���� �޼ҵ�
		CreateMenuBar();
		//		�ؽ�ũ ���� ���� �޼ҵ�
		CreateTextField();
		this.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void CreateMenuBar() {

		// �޴��� �߰�
		mb.add(fileMenu);
		mb.add(editMenu);
		mb.add(compileMenu);

		// ���� �޴��� �޴������� �߰�
		fileMenu.add(NewMenu);
		fileMenu.add(OpenMenu);
		fileMenu.add(SaveMenu);
		fileMenu.add(CreditMenu);
		fileMenu.add(ExitMenu);

		// ���� �޴��� �޴������� �߰�
		editMenu.add(CopyMenu);
		editMenu.add(PasteMenu);
		editMenu.add(CutMenu);

		// ������ �޴��� �޴� ������ �߰�
		compileMenu.add(CompileMenu);
		compileMenu.add(RunMenu);
		compileMenu.add(SourceMenu);

		//		new�޴� �̺�Ʈ ó��
		NewMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf1.setText("");
				tf2.setText("");
			}
		});

		//		Open�޴� �̺�Ʈ ó��
		OpenMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser = new JFileChooser();
				int retval = chooser.showOpenDialog(null);
				if (retval != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "������ �������� �ʾ���.", "���", JOptionPane.WARNING_MESSAGE);
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

		//		Save �޴� �̺�Ʈ ó��
		SaveMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//				��
				chooser = new JFileChooser();
				chooser.setDialogTitle("Save");
				int ret = chooser.showOpenDialog(null);
				if (ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "������ �������� �ʾҽ��ϴ�.", "���!", JOptionPane.WARNING_MESSAGE);
					return;
				}
				//				Text ���Ϸ� ���κ� �ؽ�Ʈ�Է� �κ� �Էµ� ���� ����
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
				JOptionPane.showMessageDialog(null, "��ǻ�Ͱ��а�\n205113 ������\n203913 �۹μ�", "Credit", JOptionPane.QUESTION_MESSAGE);
			}
		});
		
		//		Exit�޴� �̺�Ʈ ó��
		ExitMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

		//		Copy�޴� �̺�Ʈ �߰�
		CopyMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection stringSelection = new StringSelection(tf1.getText());
				c = Toolkit.getDefaultToolkit().getSystemClipboard();
				c.setContents(stringSelection, null);
			}
		});

		//		Paste�޴� �̺�Ʈ �߰�
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
		//		Cut�޴� �̺�Ʈ �߰�
		CutMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection StringSelection = new StringSelection(tf1.getText());
				c = Toolkit.getDefaultToolkit().getSystemClipboard();
				c.setContents(StringSelection, null);
				tf1.setText("");
			}
		});

//		������ ����� Ŭ������ �����ͼ� �����̸����� ���� �߰�
		CompileMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf2.setText("");
				class_name = tf1.getText();
//				public class �� { ���� Ŭ���� �̸� ��������
				Pattern pattern = Pattern.compile("(\\bpublic class \\b)(.*?)[ {]");
				Matcher matcher = pattern.matcher(class_name);
				if (matcher.find()) {
					class_name = matcher.group(2);
				} else {
					tf2.setText("Class �̸� ���� ����");
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
//					public class, { ���� Ŭ���� ������ ����
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
		// ���� 1:1 �ؽ�Ʈ-��ũ���г� ����
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTf1, scrollTf2);
		splitPane.setResizeWeight(0.5);
		add(splitPane);
	}
}
