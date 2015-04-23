package lector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

public class Libro {

	class Pagina {

		public Pagina(int inicio, int fin) {
			this.inicio = inicio;
			this.fin = fin;
		}

		final int inicio;
		final int fin;
	}

	private File file;
	private JTextArea area;
	private BufferedReader reader;
	private List<Pagina> paginas;
	private String ultimaPagina = "", restoPagina = "";
	int currentPage = 0, lastByte = 0;
	private int paginaMaxima = -1;

	public Libro(File file, JTextArea area) throws FileNotFoundException,
			UnsupportedEncodingException {
		this.file = file;
		paginas = new ArrayList<Pagina>();
		// reader = new BufferedReader(new FileReader(file));
		reader = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(file), "UTF8"));
		this.area = area;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public String getPage(int index) {
		if (index <= 0)
			// throw new IllegalArgumentException();
			return ultimaPagina;

		if (paginas.size() <= index - 1)
		{
			if(index - 2 == paginaMaxima && paginaMaxima != -1) 
				return ultimaPagina;
			leerPagina();
		}
		else if (index <= paginas.size())
		{
			try
			{
				recuperarPagina(index - 1);
			}
			catch (Exception e)
			{
				return "Internal Error";
			}
		}

		currentPage = index;
		return ultimaPagina;
	}

	private void recuperarPagina(int index) throws IOException {		
		reader.close();		
		
		reader = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(file), "UTF8"));
		reader.skip(paginas.get(index).inicio);
		char[] buffer = new char[paginas.get(index).fin
				- paginas.get(index).inicio];
		reader.read(buffer);
		ultimaPagina = String.valueOf(buffer);
		restoPagina = "";
	}

	private void leerPagina() {				
		try
		{
			StringBuilder builder = new StringBuilder();
			builder.append(restoPagina);
			int aux;
			do
			{
				aux = reader.read();
				if(aux != -1)
					builder.append((char)aux);
				else{
					System.out.println("fin");
					paginaMaxima = currentPage;
				}
				area.setText(builder.toString());
			}
			while (area.getPreferredSize().height < area
					.getHeight() && aux != -1);

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
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
