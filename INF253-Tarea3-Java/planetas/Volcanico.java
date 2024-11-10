package planetas;
import clases.*;

import java.util.Random;

public class Volcanico extends Planeta{
	Random rand = new Random();
	//Se inicializa el planeta Volcanico con sus atributos
	public Volcanico(){
		int temperatura = rand.nextInt(256-120+1)+120;
		int radio = rand.nextInt(100000-1000+1)+1000;
		long platino = (long)(0.25*4*Math.PI*(radio*radio) - 20.5*(temperatura*temperatura));
		long cristalesHidrogeno = (long)(0.3*4*Math.PI*(radio*radio));
		float consumoDeEnergia = (float)(0.08*temperatura);

		setTemperatura(temperatura);
		setRadio(radio);
		setPlatino(Math.abs(platino));
		setCristalesHidrogeno(Math.abs(cristalesHidrogeno));
		setConsumoDeEnergia(consumoDeEnergia);
		setNombre("Volcanico");
	}
}