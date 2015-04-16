package lector;

import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

public class Libro {
	
	JTextArea area;
	BufferedReader reader;
	List<String> paginas;
	int currentPage = 0;
	
	public Libro(File file, JTextArea area) throws FileNotFoundException {
		paginas = new ArrayList<String>();
		reader = new BufferedReader(new FileReader(file));
		this.area = area;
	}
	
	public int getCurrentPage(){
		return this.currentPage;
	}
	
	public String getPage(int index){
		if(index <= 0)
			throw new IllegalArgumentException();
		
		if(paginas.size() <= index)
			leerPagina();	
		
		currentPage = index;
		return paginas.get(index - 1);
	}

	private void leerPagina() {
		
		try
		{
			StringBuilder builder = new StringBuilder();
			do
			{
				builder.append(String.valueOf((char)reader.read()));
				area.setText(builder.toString());
			}
			while (area.getPreferredSize().height - area.getFont().getSize() < area.getHeight() );
			
			if(paginas.isEmpty()){
				paginas.add(builder.substring(0, builder.lastIndexOf(" ")));
			}
			else{
				paginas.set(paginas.size()-1, (paginas.get(paginas.size()-1)) + builder.substring(0, builder.lastIndexOf(" ")));
			}
			paginas.add(builder.substring(builder.lastIndexOf(" ")));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
				
	}
}
