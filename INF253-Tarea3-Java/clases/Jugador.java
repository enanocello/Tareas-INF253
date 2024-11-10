package clases;

public class Jugador{
	private float unidadesEnergiaProteccion;
	private float capacidadEnergiaProteccion;
	private float eficienciaEnergiaProteccion;
	private long uranio;
	private long platino;

	//Se inicializa el jugador
	public Jugador(){
		this.unidadesEnergiaProteccion = 100.0f;
		this.capacidadEnergiaProteccion = 100.0f;
		this.eficienciaEnergiaProteccion = 0.0f;
		this.uranio = 0;
		this.platino = 0;
	}
	//Muestra el estado de jugador y su inventario
	public void mostrarEstado(){
		System.out.println("\n\t\u001B[1m\u001B[37mESTADO DEL JUGADOR\u001B[0m");
		System.out.println("\u001B[1m\u001B[35mEnergia de Proteccion:\u001B[0m " + unidadesEnergiaProteccion + " Unidades");
		System.out.println("\u001B[1m\u001B[32mEficiencia de Proteccion:\u001B[0m " + eficienciaEnergiaProteccion*100 + " %");
		System.out.println("\u001B[1m\u001B[33mUranio:\u001B[0m " + uranio + " Unidades");
		System.out.println("\u001B[1m\u001B[33mPlatino:\u001B[0m " + platino + " Unidades");
	}
	//Metodo que se encarga de recargar, y que no sobrepase la capacidad maxima
	public void recargarEnergiaProteccion(int sodio){
		float unidadesRecargadas = (float)(0.65 * sodio * (1+eficienciaEnergiaProteccion));
		this.unidadesEnergiaProteccion += unidadesRecargadas;
		if (unidadesEnergiaProteccion > capacidadEnergiaProteccion){
			this.unidadesEnergiaProteccion = capacidadEnergiaProteccion;
		}
	}
	//Se encarga de restar las unidades consumidas
	public void restarEnergiaProteccion(float unidadesConsumidas){
		this.unidadesEnergiaProteccion -= unidadesConsumidas;
	}
	//Para la tienda del planeta Helado
	public void aumentarCapacidadEnergiaProteccion(float masCapacidad){
		this.capacidadEnergiaProteccion += masCapacidad;
	}
	//Para la tienda del planeta Helado
	public void aumentarEficienciaEnergiaProteccion(float masEficiencia){
		this.eficienciaEnergiaProteccion += masEficiencia;
	}
	//SETTERS
	public void setUnidadesEnergiaProteccion(float unidadesEnergiaProteccion){
		this.unidadesEnergiaProteccion = unidadesEnergiaProteccion;
	}
	public void setEficienciaEnergiaProteccion(float eficienciaEnergiaProteccion){
		this.eficienciaEnergiaProteccion = eficienciaEnergiaProteccion;
	}
	public void setUranio(long uranio){
		this.uranio = uranio;
	}
	public void setPlatino(long platino){
		this.platino = platino;
	}
	//GETTERS
	public float getUnidadesEnergiaProteccion(){
		return this.unidadesEnergiaProteccion;
	}
	public float getEficienciaEnergiaProteccion(){
		return this.eficienciaEnergiaProteccion;
	}
	public long getUranio(){
		return uranio;
	}
	public long getPlatino(){
		return platino;
	}
}