package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class Marcador implements Serializable{
		private int pagina;
		private int marca = 1;
		private List<Pagina> paginas;
		
		/**
		 * Crea un nuevo Marcador vacio
		 */
		public Marcador(){
			this.paginas = new ArrayList<Pagina>();
		}
		
		/**
		 * Crea un nuevo {@link Marcador} con una lista de paginas, una
		 * pagina actual y la posicion actual de la marca.
		 * @param pagina Pagina actual
		 * @param marca Pagina marcada
		 * @param paginas Lista de paginas
		 */
		public Marcador(int pagina,int marca, List<Pagina> paginas) {
			super();
			this.pagina = pagina;
			this.paginas = paginas;
			this.marca = marca;
		}

		public int getPagina() {
			return pagina;
		}


		public List<Pagina> getPaginas() {
			return paginas;
		}	
		
		public void setPagina(int pagina) {
			this.pagina = pagina;
		}

		public int getMarca() {
			return marca;
		}
		
		public void setMarca(int marca) {
			this.marca = marca;
		}
}
