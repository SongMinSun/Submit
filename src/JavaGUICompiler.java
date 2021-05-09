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
		fileMenu.add(ExitMenu);

		// 수정 메뉴바 메뉴아이템 추가
		editMenu.add(CopyMenu);
		editMenu.add(PasteMenu);
		editMenu.add(CutMenu);

		// 컴파일 메뉴바 메뉴 아이템 추가
		compileMenu.add(CompileMenu);
		compileMenu.add(RunMenu);

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

//		Exit메뉴 이벤트 처리(오류 남)
//		Exit인데 Edit인줄 알았네용. Exit는 이렇게 하시면 됩니다~
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

		CutMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

//		새로 컴파일 진행시 기존 파일 삭제
//		클래스 추적 -> 파일명으로 지정
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
		// 수평 1:1 텍스트-스크롤패널 분할
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTf1, scrollTf2);
		splitPane.setResizeWeight(0.5);
		add(splitPane);
	}
}