package modelo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Pagina implements Serializable {

	/**
	 * Crea una nueva pagina con <code>inicio</code> como byte inicial
	 * y <code>fin</code> como byte final.
	 * @param inicio Byte inicial dentro del buffer.
	 * @param fin Byte final dentro del buffer.
	 */
	public Pagina(int inicio, int fin) {
		super();
		this.inicio = inicio;
		this.fin = fin;
	}

	private final int inicio;
	private final int fin;
	
	public int getInicio() {
		return inicio;
	}
	public int getFin() {
		return fin;
	}
}
