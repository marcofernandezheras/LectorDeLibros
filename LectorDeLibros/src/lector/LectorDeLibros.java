package lector;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JLabel;

public class LectorDeLibros extends JFrame {

	private JPanel contentPane;
	private Libro libro;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try
				{
					LectorDeLibros frame = new LectorDeLibros();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LectorDeLibros() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 302, 484);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		contentPane.add(textArea, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		JLabel lblNumeroPagina = new JLabel("0");
		
		JButton btnAtras = new JButton("<<");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(libro.getPage(libro.getCurrentPage() - 1));
				lblNumeroPagina.setText(String.valueOf(libro.getCurrentPage()));
			}
		});
		panel.add(btnAtras);
		
		JButton btnAlante = new JButton(">>");
		btnAlante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(libro.getPage(libro.getCurrentPage() + 1));
				lblNumeroPagina.setText(String.valueOf(libro.getCurrentPage()));
			}
		});
		
		panel.add(lblNumeroPagina);
		panel.add(btnAlante);
		
		try
		{
			libro = new Libro(new File("juegoTronos.txt"), textArea);
		}
		catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
