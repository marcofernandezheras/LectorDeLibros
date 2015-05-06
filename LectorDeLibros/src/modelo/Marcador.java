package modelo;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class Marcador implements Serializable{
		private final int pagina;
		private final int marca;
		private final List<Pagina> paginas;
		
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

		public int getMarca() {
			return marca;
		}

		public List<Pagina> getPaginas() {
			return paginas;
		}		
}
