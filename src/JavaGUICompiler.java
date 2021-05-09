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
		fileMenu.add(ExitMenu);

		// ���� �޴��� �޴������� �߰�
		editMenu.add(CopyMenu);
		editMenu.add(PasteMenu);
		editMenu.add(CutMenu);

		// ������ �޴��� �޴� ������ �߰�
		compileMenu.add(CompileMenu);
		compileMenu.add(RunMenu);

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

//		Exit�޴� �̺�Ʈ ó��(���� ��)
//		Exit�ε� Edit���� �˾ҳ׿�. Exit�� �̷��� �Ͻø� �˴ϴ�~
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

		CutMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

//		���� ������ ����� ���� ���� ����
//		Ŭ���� ���� -> ���ϸ����� ����
		CompileMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (FileWriter fw = new FileWriter("Hello.java")) {
					tf1.write(fw);
					fw.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				try {
					String line;
					InputStream is;
					is = Runtime.getRuntime().exec("javac Hello.java").getInputStream();
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
					is = Runtime.getRuntime().exec("java Hello").getInputStream();
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

		this.setJMenuBar(mb);
	}

	public void CreateTextField() {
		// ���� 1:1 �ؽ�Ʈ-��ũ���г� ����
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTf1, scrollTf2);
		splitPane.setResizeWeight(0.5);
		add(splitPane);
	}
}