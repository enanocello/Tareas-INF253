package clases;
import planetas.*;

import java.util.Scanner;

public abstract class Planeta{
	private String nombre;
	private int radio;
	private long cristalesHidrogeno;
	private long floresDeSodio;
	private float consumoDeEnergia;
	private int profundidad; //Oceanico
	private int temperatura; //Helado Volcanico
	private int radiacion; //Radioactivo
	private long uranio; //Radioactivo
	private long platino; //Volcanico

	//Metodo que se encarga de limpiar la consola
	public static void limpiarConsola() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("No se puede limpiar la consola");
            System.exit(0);
        }
    }
	//Inicializa el planeta y setea todo en 0
	//Esto es ya que los planetas no ocupan necesariamente todos los atributos
	public Planeta(){
		this.radio = 0;
		this.cristalesHidrogeno = 0;
		this.floresDeSodio = 0;
		this.consumoDeEnergia = 0.0f;
		this.profundidad = 0;
		this.temperatura = 0;
		this.radiacion = 0;
		this.uranio = 0;
		this.platino = 0;
	}

	//Se encarga de visitar el planeta y mostrar todos los datos por pantalla
	public boolean visitar(Jugador jugador, Nave nave, MapaGalactico mapa){
		ArteASCII dibujar = new ArteASCII();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while(true) {
			limpiarConsola();
			Helado helado = null;
			Oceanico oceanico = null;
			if (nombre == "Helado"){
				System.out.println("\u001B[36m\u001B[1m");
				helado = (Helado)mapa.getPlaneta(mapa.getPosicion());//Se castea a Helado. Esto es para implementar la tienda
			}
			else if (nombre == "Oceanico"){
				System.out.println("\u001B[34m\u001B[1m");
				oceanico = (Oceanico)mapa.getPlaneta(mapa.getPosicion());//Se castea a Oceanico. Esto es para implementar la tienda
			}
			else if (nombre == "Radioactivo"){System.out.println("\u001B[33m\u001B[1m");}
			else {System.out.println("\u001B[31m\u001B[1m");}
	
			dibujar.dentroDelPlaneta();
			System.out.println("¡Estas dentro del planeta " + nombre + "!\u001B[0m\n");
			jugador.mostrarEstado();
			mostrarRecursos();
			System.out.println("\n\u001B[1m\u001B[32m(1) Extraer Recursos\n\u001B[31m(2) Salir\u001B[0m");
			if (getNombre() == "Helado" || getNombre() == "Oceanico"){System.out.println("\u001B[33m\u001B[1m(3) Visitar Asentamiento\u001B[0m");}
	
			int opcion = scanner.nextInt();
			if (opcion == 1) {//Extraer recursos
				extraerRecursos(0,jugador,nave);
				return true;
			}
			else if (opcion == 2) {//Volver al menu anterior
				return false;
			}
            else if (opcion == 3 && (getNombre() == "Helado" || getNombre() == "Oceanico")){
                limpiarConsola();
                if (getNombre() == "Helado"){helado.visitarAsentamientos(jugador,nave);}//Visitar tienda si es Helado
                else if (getNombre() == "Oceanico"){oceanico.visitarAsentamientos(jugador, nave);}//Visitar tienda si es Oceanico
            }
			else {
				return true;//Caso por defecto
			}
		}
	}

	//Se encarga de mostrar los datos y recursos de los planetas
	//De esta manera el jugador puede tomar decisiones en base a los datos
	public void mostrarRecursos(){
		System.out.println("\n\t\u001B[1m\u001B[37mDATOS DEL PLANETA\u001B[0m");
		System.out.println("\u001B[1m\u001B[35mRadio:\u001B[0m " + radio + " Metros");
		if (nombre == "Helado" || nombre == "Volcanico"){System.out.println("\u001B[1m\u001B[36mTemperatura:\u001B[0m " + temperatura + " °C");}
		else if (nombre == "Oceanico"){System.out.println("\u001B[1m\u001B[36mProfundidad:\u001B[0m " + profundidad + " Metros");}
		else {System.out.println("\u001B[1m\u001B[36mRadiacion:\u001B[0m " + radiacion + " Rad");}
		System.out.println("\u001B[1m\u001B[31mConsumo de Energia:\u001B[0m " + consumoDeEnergia + " Unidades de Energia");

		System.out.println("\n\t\u001B[1m\u001B[37mRECURSOS DEL PLANETA\u001B[0m");
		System.out.println("\u001B[1m\u001B[34mCristales de Hidrogeno:\u001B[0m " + cristalesHidrogeno + " Unidades");
		System.out.println("\u001B[1m\u001B[32mFlores de Sodio:\u001B[0m " + floresDeSodio + " Unidades");
		if (nombre == "Radioactivo"){System.out.println("\u001B[1m\u001B[33mUranio:\u001B[0m " + uranio + " Unidades");}
		else if (nombre == "Volcanico"){System.out.println("\u001B[1m\u001B[33mPlatino:\u001B[0m " + platino + " Unidades");}
	}
	//Permite extraer recursos de los planetas
	//Una vez que el jugador elije cuanto extraer se calcula el total de unidades consumidas
	//para que el jugador decida si continuar o decide extraer otra cantidad
	public int extraerRecursos(int tipo, Jugador jugador, Nave nave){
		ArteASCII dibujar = new ArteASCII();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		limpiarConsola();
		System.out.println("\u001B[36\u001B[1m");
		dibujar.diamante();
		jugador.mostrarEstado();
		mostrarRecursos();
		long unidadesRecurso = 0;
		long extraccionCristalesHidrogeno = 0;
		long extraccionFloresDeSodio = 0;
		long extraccionPlatino = 0;
		long extraccionUranio = 0;

		System.out.println("\n\u001B[34m\u001B[1mCantidad de Cristales de Hidrogeno que desea extraer:");
		extraccionCristalesHidrogeno = scanner.nextLong();
		unidadesRecurso += extraccionCristalesHidrogeno;

		if (nombre != "Volcanico") {//Volcanico no tiene Flores de Sodio
			System.out.println("\u001B[32mCantidad de Flores de Sodio que desea extraer:");
			extraccionFloresDeSodio = scanner.nextLong();
			unidadesRecurso += extraccionFloresDeSodio;
		}

		if (nombre == "Volcanico") {//Si es Volcanico tiene platino
			System.out.println("\u001B[33mCantidad de Platino que desea extraer:");
			extraccionPlatino = scanner.nextLong();
			unidadesRecurso += extraccionPlatino;
		}
		else if (nombre == "Radioactivo") {//Si es Radioactivo tiene Uranio
			System.out.println("\u001B[33mCantidad de Uranio que desea extraer:");
			extraccionUranio = scanner.nextLong();
			unidadesRecurso += extraccionUranio;
		}
		
		float unidadesConsumidas = (float)(0.5 * unidadesRecurso * (consumoDeEnergia/100) * (1-jugador.getEficienciaEnergiaProteccion()));
		float unidadesRecargadasJugador = (float)(0.65 * extraccionFloresDeSodio * (1+jugador.getEficienciaEnergiaProteccion()));
		float unidadesRecargadasNave = (float)(0.6 * extraccionCristalesHidrogeno * (1+nave.getEficienciaPropulsor()));

		System.out.println("\n\u001B[37mTe consumira un total de " + unidadesConsumidas + " Unidades de Energia\nLas Flores de Sodio te recargaran " + unidadesRecargadasJugador + "\nLos Cristales de Hidrogeno te recargaran " + unidadesRecargadasNave + " Unidades de Combustible a tu Nave" + "\nPulse (1) para continuar, (2) para volver");
		int opcion = scanner.nextInt();

		if (opcion == 1) {//Extraer recursos y restar o aumentar la Energia de Proteccion
			this.cristalesHidrogeno -= extraccionCristalesHidrogeno;
			this.floresDeSodio -= extraccionFloresDeSodio;
			jugador.setPlatino(jugador.getPlatino()+extraccionPlatino);
			this.platino -= extraccionPlatino;
			jugador.setUranio(jugador.getUranio()+extraccionUranio);
			this.uranio -= extraccionUranio;
			
			jugador.restarEnergiaProteccion(unidadesConsumidas);
			jugador.recargarEnergiaProteccion((int)extraccionFloresDeSodio);
			nave.recargarPropulsores((int)extraccionCristalesHidrogeno);
		}
		return 0;
	}
	//No encontre la utilidad de esta funcion, por lo tanto la deje como lo pedia la tarea
	public boolean salir(){
		return true;
	}

	//SETTERS
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	public void setRadio(int radio){
		this.radio = radio;
	}
	public void setCristalesHidrogeno(long cristalesHidrogeno){
		this.cristalesHidrogeno = cristalesHidrogeno;
	}
	public void setFloresDeSodio(long floresDeSodio){
		this.floresDeSodio = floresDeSodio;
	}
	public void setConsumoDeEnergia(float consumoDeEnergia){
		this.consumoDeEnergia = consumoDeEnergia;
	}
	public void setProfundidad(int profundidad){
		this.profundidad = profundidad;
	}
	public void setTemperatura(int temperatura){
		this.temperatura = temperatura;
	}
	public void setRadiacion(int radiacion){
		this.radiacion = radiacion;
	}
	public void setUranio(long uranio){
		this.uranio = uranio;
	}
	public void setPlatino(long platino){
		this.platino = platino;
	}
	//GETTERS
	public String getNombre(){
		return this.nombre;
	}
	public int getRadio(){
		return this.radio;
	}
	public long getCristalesHidrogeno(){
		return this.cristalesHidrogeno;
	}
	public long getFloresDeSodio(){
		return this.floresDeSodio;
	}
	public float getConsumoDeEnergia(){
		return this.consumoDeEnergia;
	}
	public int getProfundidad(){
		return profundidad;
	}
	public int getTemperatura(){
		return temperatura;
	}
	public int getRadiacion(){
		return radiacion;
	}
	public float getUranio(){
		return uranio;
	}
	public float getPlatino(){
		return platino;
	}
}