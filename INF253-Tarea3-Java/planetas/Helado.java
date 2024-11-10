package planetas;
import clases.*;

import java.util.Random;
import java.util.Scanner;

public class Helado extends Planeta implements tieneAsentamientos{
	Random rand = new Random();

	//Inicializa el planeta con sus atributos
	public Helado(){
		int temperatura = (rand.nextInt(120-30+1)+30)*-1; //Entre -120 y -30
		int radio = rand.nextInt(1000000-1000+1)+1000;//Entre 10³ y 10⁶
		long cristalesHidrogeno = (long)(0.65*4*Math.PI*(radio*radio));
		long floresDeSodio = (long)(0.35*4*Math.PI*(radio*radio));
		float consumoDeEnergia = (float)(0.15*(temperatura)*-1);

		setTemperatura(temperatura);
		setRadio(radio);
		setConsumoDeEnergia(consumoDeEnergia);
		setCristalesHidrogeno(Math.abs(cristalesHidrogeno));
		setFloresDeSodio(Math.abs(floresDeSodio));
		setNombre("Helado");
	}

	//Implementacion de tienda
	//En esta tienda se mejora todo lo que tenga que ver con el exotraje
	public void visitarAsentamientos(Jugador jugador, Nave nave){
		ArteASCII dibujar = new ArteASCII();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		dibujar.marciano();
		System.out.println("\u001B[33m\u001B[1mBienvenido a mi tienda humano\n¿Que se le ofrece?\u001B[0m");
		jugador.mostrarEstado();
		System.out.println("\n\u001B[1m\u001B[32m(1) Aumentar capacidad del Exotraje \n\t\u001B[31mCOSTE: 200 Uranio 200 Platino");
		System.out.println("\u001B[32m(2) Aumentar eficiencia del Exotraje \n\t\u001B[31mCOSTE: 500 Uranio 500 Platino");
		System.out.println("\u001B[34m(3) Salir de la tienda\u001B[0m");
		int opcion = scanner.nextInt();
		if (opcion == 1){//Aumentar capacidad
			if (jugador.getUranio() > 200 && jugador.getPlatino() > 200){
				jugador.aumentarCapacidadEnergiaProteccion(20);
				System.out.println("\n\u001B[33m\u001B[1mCAPACIDAD DEL EXOTRAJE AUMENTADA\nGRACIAS HUMANO");
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
				jugador.aumentarEficienciaEnergiaProteccion(0.1f);
				System.out.println("\n\u001B[33m\u001B[1mEFICIENCIA DEL EXOTRAJE AUMENTADA\nGRACIAS HUMANO");
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