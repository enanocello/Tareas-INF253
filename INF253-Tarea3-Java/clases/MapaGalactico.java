package clases;
import planetas.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapaGalactico{
	private static List<Planeta> planetas;//Lista de planetas
	private int posicion;
	private boolean centroDisponible = true;//Se encarga de generar un solo centro galactico

	//Inicializa la lista
	public MapaGalactico(){
		planetas = new ArrayList<>();
	}
	//Agrega planetas segun donde el jugador desea viajar
	//Si desea viajar hasta el planeta 100, se generaran los 100 planetas
	public void agregarPlaneta(int objetivo){
		if ((planetas.size()) < objetivo){//Agranda la lista rellenando con planetas
			for (int i=planetas.size(); i<=objetivo; i++){
				generadorPlaneta();
			}
		}
	}
	//Muestra los planetas de la lista Planetas
	public void mostrarPlanetas(){
		System.out.println("\u001B[37m\u001B[1mPLANETAS DESCUBIERTOS:\n");
		for (int i=0; i<planetas.size(); i++){
			Planeta planeta = planetas.get(i);
			System.out.println("\u001B[36mPlaneta "+i+": "+planeta.getNombre());
		}
		System.out.println("\u001B[0m\u001B[1m");
	}
	//Genera el planeta y los agrega a la lista de Planetas
	//Se generan segun la probabilidad dada en la tarea
	public Planeta generadorPlaneta(){
		Random rand = new Random();
		int probabilidad = rand.nextInt(100)+1;

		if (probabilidad >= 1 && probabilidad <= 30) {//30%
			Helado planeta = new Helado();
			planetas.add(planeta);
			return planeta;
		}
		else if (probabilidad >= 31 && probabilidad <= 60) {//30%
			Oceanico planeta = new Oceanico();
			planetas.add(planeta);
			return planeta;
		}
		else if (probabilidad >= 61 && probabilidad <= 80) {//20%
			Radioactivo planeta = new Radioactivo();
			planetas.add(planeta);
			return planeta;
		}
		else if (probabilidad >= 81 && probabilidad <= 99) {//19%
			Volcanico planeta = new Volcanico();
			planetas.add(planeta);
			return planeta;
		}
		else if (probabilidad == 100 && centroDisponible) {//1%
			CentroGalactico planeta = new CentroGalactico();
			centroDisponible = false;
			planetas.add(planeta);
			return planeta;
		}
		else {//Caso por defecto
			Helado planeta = new Helado();
			planetas.add(planeta);
			return planeta;
		}
	}
	//SETTERS
	public void setPosicion(int posicion){
		this.posicion = posicion;
	}
	//GETTERS
	public int getPosicion(){
		return posicion;
	}
	public Planeta getPlaneta(int pos){
		return planetas.get(pos);
	}
	
	
}