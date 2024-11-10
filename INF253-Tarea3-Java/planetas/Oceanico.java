package planetas;
import clases.*;

import java.util.Random;
import java.util.Scanner;

public class Oceanico extends Planeta implements tieneAsentamientos{
	Random rand = new Random();

	//Se inicializa el planeta con sus atributos
	public Oceanico(){
		int profundidad = rand.nextInt(100-30+1)+30;//Entre 30 y 100
		int radio = rand.nextInt(1000000-10000+1)+10000;//Entre 10⁴ y 10⁶
		long cristalesHidrogeno = (long)(0.2*4*Math.PI*(radio*radio));
		long floresDeSodio = (long)(0.65*4*Math.PI*(radio*radio));
		float consumoDeEnergia = (float)(0.002*(profundidad*profundidad));

		setProfundidad(profundidad);
		setRadio(radio);
		setCristalesHidrogeno(Math.abs(cristalesHidrogeno));//Valor absoluto para evitar el negativo del desbordamiento
		setFloresDeSodio(Math.abs(floresDeSodio));
		setConsumoDeEnergia(consumoDeEnergia);
		setNombre("Oceanico");
	}
	//Implementacion de tienda
	//En esta tienda se mejora todo lo que tiene que ver con la nave
	public void visitarAsentamientos(Jugador jugador, Nave nave){
		ArteASCII dibujar = new ArteASCII();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		dibujar.marciano();
		System.out.println("\u001B[33m\u001B[1mBienvenido a mi tienda humano\n¿Que se le ofrece?\u001B[0m");
		jugador.mostrarEstado();
		System.out.println("\n\u001B[1m\u001B[32m(1) Aumentar capacidad de Combustible \n\t\u001B[32mCOSTE: 500 Uranio 500 Platino");
		System.out.println("\u001B[32m(2) Aumentar eficiencia del Propulsor \n\t\u001B[32mCOSTE: 1000 Uranio 1000 Platino");
		System.out.println("\u001B[34m(3) Salir de la tienda\u001B[0m");
		int opcion = scanner.nextInt();
		if (opcion == 1){//Aumentar capacidad 
			if (jugador.getUranio() > 500 && jugador.getPlatino() > 500){
				nave.aumentarCapacidadCombustible(20);
				System.out.println("\n\u001B[33m\u001B[1mCAPACIDAD DE COMBUSTIBLE AUMENTADA\nGRACIAS HUMANO");
				scanner.nextLine();
				scanner.nextLine();
				return;
			}
			else {
				System.out.println("\n\u001B[33m\u001B[1mNO TIENES SUFICIENTES RECURSOS\nLARGATE HUMANO");
				scanner.nextLine();
				scanner.nextLine();
				return;
			}
		}
		else if (opcion == 2){//Aumentar eficiencia
			if (jugador.getUranio() > 200 && jugador.getPlatino() > 200){
				nave.aumentarEficienciaPropulsor(0.1f);
				System.out.println("\n\u001B[33m\u001B[1mEFICIENCIA DEL PROPULSOR AUMENTADA\nGRACIAS HUMANO");
				scanner.nextLine();
				scanner.nextLine();
				return;
			}
			else {
				System.out.println("\n\u001B[33m\u001B[1mNO TIENES SUFICIENTES RECURSOS\nLARGATE HUMANO");
				scanner.nextLine();
				scanner.nextLine();
				return;
			}
		}
		else {
			System.out.println("\n\u001B[33m\u001B[1mNOS VEMOS HUMANO");
			scanner.nextLine();
			scanner.nextLine();
			return;
		}
	}
}