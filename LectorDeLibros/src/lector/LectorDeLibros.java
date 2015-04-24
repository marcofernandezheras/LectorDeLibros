package lector;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;


import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

public class LectorDeLibros extends JFrame {

	private JPanel contentPane;
	private Libro libro;
	private int paginaMarcada = 1;
	private JTextArea textArea;
	private JButton btnAtras;
	private JButton btnMarcar;
	private JButton btnIrAMarca;
	private JButton btnAlante;
	private JLabel lblNumeroPagina;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		setTitle("Lector");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 321, 490);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		contentPane.add(textArea, BorderLayout.CENTER);
		
		//Panel de botones
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		lblNumeroPagina = new JLabel("0");
		btnAtras = new JButton("<<");		
		btnAlante = new JButton(">>");		
		btnMarcar = new JButton("Marcar");		
		btnIrAMarca = new JButton("Ir a Marca");
		
		panel.add(btnAtras);
		panel.add(btnMarcar);
		panel.add(btnIrAMarca);
		panel.add(btnAlante);		
		panel.add(lblNumeroPagina);		
		
		//Eventos de los botones
		eventos();
		
		//Apertura del libro
		abrirLibro();
	}

	/**
	 * Abre el Libro
	 */
	private void abrirLibro() {
		try
		{
			libro = new Libro(new File("juegoTronos.txt"), textArea);
		}
		catch (IOException e1)
		{
			JOptionPane.showMessageDialog(this, e1.getMessage(), "Error al abrir archivo", JOptionPane.WARNING_MESSAGE);
		}		
	}

	/**
	 * Añade los eventos a los botones
	 */
	private void eventos() {		
		btnAtras.addActionListener(e -> atras());
		btnAlante.addActionListener(e -> adelante());
		btnIrAMarca.addActionListener(e -> irAMarca());	
		btnMarcar.addActionListener(e -> paginaMarcada = libro.getCurrentPage());
	}

	/**
	 * Mueve el libro a la pagina marcada.
	 * Por defecto está marcada la pagina 1.
	 */
	private void irAMarca() {
		try
		{
			textArea.setText(libro.getPage(paginaMarcada));
			lblNumeroPagina.setText(String.valueOf(libro.getCurrentPage()));
		}
		catch (IOException e1)
		{
			JOptionPane.showMessageDialog(this, e1.getMessage(), "Error al leer", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Mueve el libro una página hacia detrás.
	 */
	private void atras() {
		try
		{
			textArea.setText(libro.getPage(libro.getCurrentPage() - 1));
			lblNumeroPagina.setText(String.valueOf(libro.getCurrentPage()));
		}
		catch (IOException e1)
		{
			JOptionPane.showMessageDialog(this, e1.getMessage(), "Error al leer", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Mueve el libro una página hacia delente.
	 */
	private void adelante() {
		try
		{
			textArea.setText(libro.getPage(libro.getCurrentPage() + 1));
			lblNumeroPagina.setText(String.valueOf(libro.getCurrentPage()));
		}
		catch (IOException e1)
		{
			JOptionPane.showMessageDialog(this, e1.getMessage(), "Error al leer", JOptionPane.WARNING_MESSAGE);
		}
	}

}
