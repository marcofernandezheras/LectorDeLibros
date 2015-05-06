package controlador;


import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class Controlador extends vista.LectorDeLibros {

	private Librero libro;

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
					Controlador frame = new Controlador();
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
	public Controlador() {
		super();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				libro.serialize();
			}
		});
		
		//Eventos de los botones
		eventos();

		//Apertura del libro
		abrirLibro();				
		
		textArea.setText(libro.deserialize());
		lblNumeroPagina.setText(String.valueOf(libro.getCurrentPage()));
	}

	
	/**
	 * Abre el Libro
	 */
	private void abrirLibro() {
		try
		{
			libro = new Librero(new File("juegoTronos.txt"), textArea);
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
		btnMarcar.addActionListener(e -> libro.setPaginaMarcada(libro.getCurrentPage()));
	}

	/**
	 * Mueve el libro a la pagina marcada.
	 * Por defecto está marcada la pagina 1.
	 */
	private void irAMarca() {
		try
		{
			textArea.setText(libro.getPage(libro.getPaginaMarcada()));
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
