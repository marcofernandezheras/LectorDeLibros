package lector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

public class Libro {

	class Pagina {

		/**
		 * Crea una nueva pagina con <code>inicio</code> como byte inicial
		 * y <code>fin</code> como byte final.
		 * @param inicio Byte inicial dentro del buffer.
		 * @param fin Byte final dentro del buffer.
		 */
		public Pagina(int inicio, int fin) {
			this.inicio = inicio;
			this.fin = fin;
		}

		final int inicio;
		final int fin;
	}

	private File file;
	private JTextArea area;
	private BufferedReader reader = null;
	private List<Pagina> paginas;
	private String ultimaPagina = "", restoPagina = "";
	private int currentPage = 0, lastByte = 0;
	private int paginaMaxima = -1;

	/**
	 * Crea un nuevo {@link Libro} que representa el archivo
	 * <code>file</code> creando paginas del tamaño del <code>area</code>.
	 * @param file Archivo de texto plano a leer. Debe estar codificado en UTF-8
	 * @param area {@link JTextArea} donde se mostrará el libro
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
	 * @param index Número de la pagina
	 * @return Texto ajustado al tamaño de {@link #area}
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
	 * @param index Número de la pagina
	 * @throws IOException Si hay algun problema al leer la pagina.
	 */
	private void recuperarPagina(int index) throws IOException 
	{			
		resetBuffer();
		reader.skip(paginas.get(index).inicio);
		char[] buffer = new char[paginas.get(index).fin	- paginas.get(index).inicio];
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
}
