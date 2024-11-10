#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include "Cartas.h"
#include "Tablero.h"

//Pantalla de Exito revelando los barcos
void defenzaExitosa(){
    system("clear");
    printf("\e[1;32m¡TODOS LOS BARCOS DESTRUIDOS, DEFENZA EXITOSA!\e[0m\n\n");
    revelarTablero();
    mostrarTablero();
    liberarTablero(tamaño);
    liberarMano();
    fflush(stdout);
    sleep(5);
    exit(0);
}
//Pantalla de Fracaso revelando los barcos
void defenzaFallida(){
    system("clear");
    printf("\e[1;31m¡DEFENZA FALLIDA!\e[0m\n\n");
    revelarTablero();
    mostrarTablero();
    liberarTablero(tamaño);
    liberarMano();
    fflush(stdout);
    sleep(5);
    exit(0);
}
//Funcion principal
int main(int argc, char const *argv[]) {
    system("clear");
    //Seleccionar dificultad
    printf("\e[1mSelecciona la Dificultad:\e[0m\n1. \e[1;32mFacil  \e[0m -> 11X11, 5 Barcos\n2. \e[1;33mMedio  \e[0m -> 17X17, 7 Barcos\n3. \e[1;31mDificil\e[0m -> 21X21, 9 Barcos\n");
    printf("Ingrese un numero: ");
    scanf("%d", &dificultad);
    //Asignar tamaño y barcos segun la dificultad
    if (dificultad == Facil) {
        tamaño = 11; turnos = 30;
        BarcoS = 2; BarcoM = 1;
        BarcoL = 1; BarcoXL = 1;
        totalBarcos = totalFacil; //Se usa para verificar el estado de juego
    } else if (dificultad == Medio) {
        tamaño = 17; turnos = 25;
        BarcoS = 3; BarcoM = 2;
        BarcoL = 1; BarcoXL = 1;
        totalBarcos = totalMedio;
    } else if (dificultad == Dificil) {
        tamaño = 21; turnos = 15;
        BarcoS = 3; BarcoM = 2;
        BarcoL = 2; BarcoXL = 2;
        totalBarcos = totalDificil;
    }

    //Generando tablero, insertando barcos y generando la mano de cartas
    inicializarTablero(tamaño);
    inicializarMano();
    generarBarcos();

    for (int turno = 0; turno <= turnos; turno++){
        system("clear");
        printf("\e[1mTurno %d:\e[0m\n",turno);
        
        //Se muestra tablero y mano por pantalla
        mostrarTablero();
        mostrarMano();

        //Selecciona una carta y verifica que este dentro del rango
        //Si no esta dentro del rango pasa al siguiente turno sin efectos
        printf("\n\nSelecciona una Carta: ");
        scanf("%d", &numCarta);
        if (numCarta <= 0){exit(0);}
        if (numCarta > 5){continue;}

        //Verifica que la carta seleccionada sea 500KG
        if (*(int *)cartas.carta[numCarta] == Ultra){
            fflush(stdout);
            printf("Gran Misil Preparado!!! Se perdera el cañon\n");
            sleep(1);
        }
        //Selecciona coordenadas y verifica que esten dentro del rango
        //Si no estan dentro del rango pasa al siguiente turno sin efectos
        printf("Selecciona Coordenadas:\nX : ");
        scanf("%d", &x);
        printf("Y : ");
        scanf("%d", &y);
        if (x < 0 || x > tamaño-1){continue;}
        if (y < 0 || y > tamaño-1){continue;}
        x = x-1; y = y-1;

        //Usa la carta seleccionada en las coordenadas seleccionadas
        usarCarta();

        //Verifica el estado de juego
        //Si los barcos hundidos es igual a la cantidad de barcos entonces
        //la defenza ha sido exitosa
        int numHundido = verificarTablero();
        if (numHundido == totalBarcos) {
            defenzaExitosa();
        }
    }
    //Si terminan los turnos sin vencer entonces la defenza ha fallado
    defenzaFallida();
    return 0;
}