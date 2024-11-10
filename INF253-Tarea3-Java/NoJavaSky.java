import clases.*;
import java.util.Scanner;

public class NoJavaSky{
    //Metodo que se encarga de limpiar la consola
    public static void limpiarConsola() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("No se puede limpiar la consola");
            System.exit(0);
        }
    }
    //Metodo MAIN del juego. Se encarga de llevar el flujo principal de juego
    public static void main(String[] args) {
        ArteASCII dibujar = new ArteASCII();
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        limpiarConsola();
        
        //Comienza el juego
        dibujar.titulo();
        System.out.println("\t\tPresione ENTER para comenzar el juego");
        scanner.nextLine();
        
        MapaGalactico mapa = new MapaGalactico();
        Jugador jugador = new Jugador();
        Nave nave = new Nave();
        mapa.agregarPlaneta(9); //Inicializa con 10 planetas
        
        while (true) {
            limpiarConsola();
            Planeta planeta = mapa.getPlaneta(mapa.getPosicion());
            verificarEstadoJuego(mapa, planeta, jugador, nave);
            
            //Indica en que orbita se encuentra y muestra todos los datos y opciones para jugar
            dibujar.planeta();
            System.out.println("Estas en la orbita del Planeta " + mapa.getPosicion() + ": " + planeta.getNombre() + "\n");
            jugador.mostrarEstado();
            nave.mostrarNave();
            planeta.mostrarRecursos();
            System.out.println("\n\u001B[1m\u001B[32m(1) Entrar\n\u001B[31m(2) Viajar\n\u001B[33m(3) Salir\u001B[0m");
            
            int opcion = scanner.nextInt();
            if (opcion == 1) { //Extraer recursos del planeta
                jugador.restarEnergiaProteccion(planeta.getConsumoDeEnergia());
                Boolean dentroPlaneta = true;
                while (dentroPlaneta) {
                    verificarEstadoJuego(mapa, planeta, jugador, nave);
                    dentroPlaneta = planeta.visitar(jugador,nave,mapa);
                }
            }
            else if (opcion == 2) { //Viajar a otro planeta
                limpiarConsola();
                dibujar.sistemaSolar();
                mapa.mostrarPlanetas();

                System.out.println("Numero de planeta que desea viajar:");
                int objetivo = scanner.nextInt();
                int salto;
                if (objetivo > mapa.getPosicion()) {
                    salto = objetivo - mapa.getPosicion();
                }
                else {
                    salto = mapa.getPosicion() - objetivo;
                }
                limpiarConsola();
                nave.viajarPlaneta(mapa, objetivo, salto);
            }
            else if (opcion == 3) {//Salir del juego
                limpiarConsola();
                dibujar.astronauta();
                System.out.println("\t\t¡NOS VEMOS EN LA PROXIMA MISION!");
                scanner.nextLine();
                scanner.nextLine();
                System.exit(0);
            }
        }
    } 
    //Este metodo se encarga de verificar por cada ronda si el jugador ha perdido o ganado
    //Si el jugador pierde, entonces se activa el sistema de emergencia
    //Si gana, se llama al metodo de pantalla de victoria
    public static void verificarEstadoJuego(MapaGalactico mapa, Planeta planeta, Jugador jugador, Nave nave){
        
        if ((planeta.getNombre() == "Centro Galactico" && nave.getEficienciaPropulsor() < 0.5f)||(jugador.getUnidadesEnergiaProteccion()<=0)||(nave.getUnidadesCombustible()<=0)){
            ArteASCII dibujar = new ArteASCII();    
            System.out.println("\u001B[1m\u001B[31m");
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(System.in);
            limpiarConsola();
            dibujar.naveEspacial();
            System.out.println("\t¡¡¡SE HA ACTIVADO EL SISTEMA DE EMERGENCIA!!!\n\t   PRESIONE START PARA VOLVER AL PLANETA 0\u001B[0m");
            scanner.nextLine();

            //Reset al jugador y a la nave
            jugador.setUnidadesEnergiaProteccion(100.0f);
            jugador.setEficienciaEnergiaProteccion(0.0f);
            nave.setUnidadesCombustible(100.0f);
            nave.setEficienciaPropulsor(0.0f);
            jugador.setPlatino(0);
            jugador.setUranio(0);
            mapa.setPosicion(0);
            limpiarConsola();
            }
        //Si eficiencia >= 50% entonces gana la partida
        else if (planeta.getNombre() == "Centro Galactico" && nave.getEficienciaPropulsor() >= 0.5){
            youWin();
        }
    }
    //Pantalla de Victoria
    public static void youWin(){
        Scanner scanner = new Scanner(System.in);
        ArteASCII dibujar = new ArteASCII();
        dibujar.astronauta();
        System.out.println("\t\t¡MISION CUMPLIDA EXITOSAMENTE!");
        scanner.nextLine();
        scanner.close();
        System.exit(0);
    }
}