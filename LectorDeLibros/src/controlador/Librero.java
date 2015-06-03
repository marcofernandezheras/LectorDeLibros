package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JTextArea;

import modelo.Libro;
import modelo.Pagina;

public class Librero{
	
	private File file;
	private JTextArea area;
	private BufferedReader reader = null;

	private String ultimaPagina = "", restoPagina = "";
	private int lastByte = 0;
	private int paginaMaxima = -1;

	private Libro libro;
	
	/**
	 * Crea un nuevo {@link Librero} que representa el archivo
	 * <code>file</code> creando paginas del tamaño del <code>area</code>.
	 * @param file Archivo de texto plano a leer. Debe estar codificado en UTF-8
	 * @param area {@link JTextArea} donde se mostrará el libro
	 * @throws IOException Si hay algun problema con el archivo
	 */
	public Librero(File file, JTextArea area) throws IOException {
		this.file = file;
		this.area = area;
		this.libro = new Libro();
		resetBuffer();
	}

	/**
	 * Abre un buffer sobre la propiedad {@link #file}.
	 * Si el buffer estaba abierto, lo cierra primero.
	 * @throws IOException Si hay algun problema con el archivo
	 */
	private void resetBuffer() throws IOException 
	{
		if(reader != null)
			reader.close();	
		reader = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(file), "UTF8"));
	}
	
	/**
	 * Obtiene la pagina correspondiente al <code>index</code>
	 * @param index Número de la pagina
	 * @return Texto ajustado al tamaño de {@link #area}
	 * @throws IOException Si hay algun problema al leer la pagina.
	 */
	public String getPage(int index) throws IOException {
		if (index <= 0)
			return ultimaPagina;

		if (libro.getPaginas().size() <= index - 1)
		{
			if(index - 2 == paginaMaxima && paginaMaxima != -1) 
				return ultimaPagina;
			leerPagina();
		}
		else if (index <= libro.getPaginas().size())
		{
			recuperarPagina(index - 1);
		}

		libro.setPagina(index);
		return ultimaPagina;
	}

	/**
	 * Recupera un pagina basandose en las {@link Pagina} almacenadas.
	 * @param index Número de la pagina
	 * @throws IOException Si hay algun problema al leer la pagina.
	 */
	private void recuperarPagina(int index) throws IOException 
	{			
		resetBuffer();
		reader.skip(libro.getPaginas().get(index).getInicio());
		char[] buffer = new char[libro.getPaginas().get(index).getFin() - libro.getPaginas().get(index).getInicio()];
		reader.read(buffer);
		ultimaPagina = String.valueOf(buffer);
		restoPagina = "";
	}


	/**
	 * Extrae el texto necesario para rellenar el {@link #area} por completo
	 * @throws IOException Si hay algun problema al leer {@link #file}
	 */
	private void leerPagina() throws IOException {				
		
		StringBuilder builder = new StringBuilder();
	
		builder.append(restoPagina);
		int aux;

		do
		{
			aux = reader.read();
			if(aux != -1)
				builder.append((char)aux);
			else
			{
				paginaMaxima = libro.getPagina();
			}
			area.setText(builder.toString());
										
		}
		while (area.getPreferredSize().height < area.getHeight() && aux != -1);

		if(paginaMaxima == libro.getPagina()){
			ultimaPagina = builder.toString();
			restoPagina = "";
		}
		else 
		{
			ultimaPagina = builder.substring(0, builder.lastIndexOf(" "));
			restoPagina = builder.substring(builder.lastIndexOf(" "));
		}

		libro.getPaginas().add(new Pagina(lastByte, lastByte + ultimaPagina.length()));
		lastByte += ultimaPagina.length();
	}
	
	/**
	 * Guarda el estado actual del libro
	 */
	public void serialize() {		
		try(ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("marcador.dat")))
		{
			salida.writeObject(libro);
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	
	/**
	 * Recupera el estado guardado del libro, devolviendo
	 * el contenido de la pagina actual
	 * @return Pagina actual.
	 */
	public String deserialize(){
		File marcadorFile = new File("marcador.dat");
		if(marcadorFile.exists())
		{
			try (ObjectInputStream entrada =new ObjectInputStream(new FileInputStream(marcadorFile)))
			{
				libro = (Libro) entrada.readObject();
				this.recuperarPagina(libro.getPagina()-1);
				lastByte = libro.getPaginas().get(libro.getPagina()-1).getFin();
				return ultimaPagina;
			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return "";
	}
	
	/**
	 * Devuelve el numero de pagina actual.
	 * @return pagina actual.
	 */
	public int getCurrentPage() {
		return this.libro.getPagina();
	}

	/**
	 * Pone una marca a la que poder volver.
	 * @param paginaMarcada Numero de pagina a marcar.
	 */
	public void setPaginaMarcada(int paginaMarcada) {
		this.libro.setMarca(paginaMarcada);
	}
	
	/**
	 * Devuelve el numero de pagina marcada.
	 * @return pagina marcada.
	 */
	public int getPaginaMarcada() {
		return libro.getMarca();
	}
}
