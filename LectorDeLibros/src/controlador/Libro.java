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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import modelo.Marcador;
import modelo.Pagina;

public class Libro{
	
	private File file;
	private JTextArea area;
	private BufferedReader reader = null;
	private List<Pagina> paginas;
	private String ultimaPagina = "", restoPagina = "";
	private int currentPage = 0, lastByte = 0;
	private int paginaMaxima = -1;
	private int paginaMarcada = 1;

	public int getPaginaMarcada() {
		return paginaMarcada;
	}

	public void setPaginaMarcada(int paginaMarcada) {
		this.paginaMarcada = paginaMarcada;
	}

	/**
	 * Crea un nuevo {@link Libro} que representa el archivo
	 * <code>file</code> creando paginas del tama�o del <code>area</code>.
	 * @param file Archivo de texto plano a leer. Debe estar codificado en UTF-8
	 * @param area {@link JTextArea} donde se mostrar� el libro
	 * @throws IOException Si hay algun problema con el archivo
	 */
	public Libro(File file, JTextArea area) throws IOException {
		this.file = file;
		this.area = area;
		paginas = new ArrayList<Pagina>();
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
	
	public int getCurrentPage() {
		return this.currentPage;
	}

	/**
	 * Obtiene la pagina correspondiente al <code>index</code>
	 * @param index N�mero de la pagina
	 * @return Texto ajustado al tama�o de {@link #area}
	 * @throws IOException Si hay algun problema al leer la pagina.
	 */
	public String getPage(int index) throws IOException {
		if (index <= 0)
			return ultimaPagina;

		if (paginas.size() <= index - 1)
		{
			if(index - 2 == paginaMaxima && paginaMaxima != -1) 
				return ultimaPagina;
			leerPagina();
		}
		else if (index <= paginas.size())
		{
			recuperarPagina(index - 1);
		}

		currentPage = index;
		return ultimaPagina;
	}

	/**
	 * Recupera un pagina basandose en las {@link Pagina} almacenadas.
	 * @param index N�mero de la pagina
	 * @throws IOException Si hay algun problema al leer la pagina.
	 */
	private void recuperarPagina(int index) throws IOException 
	{			
		resetBuffer();
		reader.skip(paginas.get(index).getInicio());
		char[] buffer = new char[paginas.get(index).getFin() - paginas.get(index).getInicio()];
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
				paginaMaxima = currentPage;
			}
			area.setText(builder.toString());
										
		}
		while (area.getPreferredSize().height < area.getHeight() && aux != -1);

		if(paginaMaxima == currentPage){
			ultimaPagina = builder.toString();
			restoPagina = "";
		}
		else 
		{
			ultimaPagina = builder.substring(0, builder.lastIndexOf(" "));
			restoPagina = builder.substring(builder.lastIndexOf(" "));
		}

		paginas.add(new Pagina(lastByte, lastByte + ultimaPagina.length()));
		lastByte += ultimaPagina.length();
	}
	
	public void serialize() {		
		try(ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("marcador.dat")))
		{
			salida.writeObject(new Marcador(currentPage,paginaMarcada, paginas));
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
	
	public String deserialize(){
		try (ObjectInputStream entrada =new ObjectInputStream(new FileInputStream("marcador.dat")))
		{
			Marcador marcador = (Marcador) entrada.readObject();
			this.paginas = marcador.getPaginas();
			this.currentPage = marcador.getPagina();
			this.recuperarPagina(currentPage-1);
			this.paginaMarcada = marcador.getMarca();
			lastByte = paginas.get(currentPage-1).getFin();
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
		return "";
	}
}
