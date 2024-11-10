package planetas;
import clases.*;

import java.util.Random;

public class Radioactivo extends Planeta{
	Random rand = new Random();
	//Inicializa el planeta Radioactivo con sus atributos
	public Radioactivo(){
		int radiacion = rand.nextInt(50-10+1)+10;//Entre 10 y 50
		int radio = rand.nextInt(100000-10000+1)+10000;//Entre 10⁴ y 10⁵
		long uranio = (long)(0.25*4*Math.PI*(radio*radio));
		long cristalesHidrogeno = (long)(0.2*4*Math.PI*(radio*radio));
		long floresDeSodio = (long)(0.2*4*Math.PI*(radio*radio));
		float consumoDeEnergia = (float)(0.3*radiacion);
	
		setRadiacion(radiacion);
		setRadio(radio);
		setUranio(Math.abs(uranio));
		setCristalesHidrogeno(Math.abs(cristalesHidrogeno));
		setFloresDeSodio(Math.abs(floresDeSodio));
		setConsumoDeEnergia(consumoDeEnergia);	
		setNombre("Radioactivo");
	}
}