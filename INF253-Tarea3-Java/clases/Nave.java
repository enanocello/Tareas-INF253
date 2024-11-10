package clases;

import java.util.Scanner;

public class Nave{
	private float unidadesCombustible;
	private float capacidadCombustible;
	private float eficienciaPropulsor;

	//Inicializa la nave
	public Nave(){
		this.unidadesCombustible = 100.0f;
		this.capacidadCombustible = 100.0f;
		this.eficienciaPropulsor = 0.0f;
	}
	//Metodo que se encarga de llevar a cabo los viajes galacticos
	public boolean viajarPlaneta(MapaGalactico MG, int direccion, int tamanoSalto){
		ArteASCII dibujar = new ArteASCII();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		dibujar.naveEspacial();
		float unidadesConsumidas = (float)(0.75 * (tamanoSalto*tamanoSalto) * (1-eficienciaPropulsor));
	
		System.out.println("\u001B[37mPara viajar al planeta " + direccion + " se consumiran " + unidadesConsumidas + " Unidades de Combustible");
		System.out.println("\n\u001B[1m\u001B[32m(1) Viajar al Planeta\n\u001B[31m(2) Volver a la orbita\u001B[0m");
		
		int opcion = scanner.nextInt();
		if (opcion == 1) {
			MG.agregarPlaneta(direccion);//Si el planeta esta dentro de la lista, se ignora el agregarPlaneta
			MG.setPosicion(direccion);
			unidadesCombustible -= unidadesConsumidas;
		}
		return true;
	}
	//Muestra el estado de la nave
	public void mostrarNave(){
		System.out.println("\n\t\u001B[1m\u001B[37mESTADO DE LA NAVE\u001B[0m");
		System.out.println("\u001B[1m\u001B[35mCombustible:\u001B[0m " + unidadesCombustible + " Unidades");
		System.out.println("\u001B[1m\u001B[32mEficiencia de Propulsor:\u001B[0m " + eficienciaPropulsor*100 + " %");
	}
	//Se encarga de recargar propulsores y que no sobrepase la capacidad maxima
	public void recargarPropulsores(int hidrogeno){
		float unidadesRecargadas = (float)(0.6 * hidrogeno * (1+eficienciaPropulsor));
		this.unidadesCombustible += unidadesRecargadas;
		if (unidadesCombustible > capacidadCombustible){
			this.unidadesCombustible = capacidadCombustible;
		}
	}
	//Para la tienda del planeta Oceanico
	public void aumentarCapacidadCombustible(float masCombustible){
		this.capacidadCombustible += masCombustible;
	}
	//Para la tienda del planeta Oceanico
	public void aumentarEficienciaPropulsor(float masEficiencia){
		this.eficienciaPropulsor += masEficiencia;
	}
	//SETTERS
	public void setUnidadesCombustible(float unidadesCombustible){
		this.unidadesCombustible = unidadesCombustible;
	}
	public void setEficienciaPropulsor(float eficienciaPropulsor){
		this.eficienciaPropulsor = eficienciaPropulsor;
	}
	//GETTERS
	public float getUnidadesCombustible(){
		return unidadesCombustible;
	}
	public float getEficienciaPropulsor(){
		return eficienciaPropulsor;
	}

}