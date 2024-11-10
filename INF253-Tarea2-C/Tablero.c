#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include "Cartas.h"
#include "Tablero.h"

//Se inicializan en 0 para evitar problemas
int dificultad = 0;
int tamaño = 0;
int turnos = 0;

int BarcoS = 0;
int BarcoM = 0;
int BarcoL = 0;
int BarcoXL = 0;
int totalBarcos = 0;

int aleatorio = 1;//Se va aumentado cada vez que se ocupa

//Se define el tablero y se aloja espacio en memoria para cada
//arreglo de punteros, y luego de pedir la memoria se asigna
//caracter
void ***tablero;
void inicializarTablero(int tamaño) {
    tablero = (void ***)malloc(tamaño * sizeof(void **));
    for (int fila = 0; fila < tamaño; fila++) {
        tablero[fila] = (void **)malloc(tamaño * sizeof(void *));
        for (int columna = 0; columna < tamaño; columna++) {
            tablero[fila][columna] = malloc(sizeof(char));
            *((char *)tablero[fila][columna]) = ' ';
        }
    }
}
//Libera la memoria solicitada para el tablero
void liberarTablero(int tamaño) {
    for (int fila = 0; fila < tamaño; fila++) {
        for (int columna = 0; columna < tamaño; columna++) {
            free(tablero[fila][columna]);
        }
        free(tablero[fila]);
    }
    free(tablero);
}


//Imprime el tablero en pantalla, preocupandose de que sea
//legible para el usuario y amigable a la vista, ademas agregando
//las coordenadas para que el usuario pueda escoger de manera
//facil su casilla y su estrategia de juego
void mostrarTablero(){
    for (int i=0;i<tamaño*4+6;i++){printf("-");}
    printf("\n   ");
    for (int fila = 0; fila < tamaño; fila++) {
        if (fila < 9) {printf(" %d  ",fila+1);}
        else {printf(" %d ",fila+1);}
    }
    printf("\e[1m  X\e[0m\n");
	for (int fila = 0; fila < tamaño; fila++) {
        if (fila < 9) {printf(" %d ",fila+1);}
        else {printf("%d ",fila+1);}
		for (int columna = 0; columna< tamaño; columna++) {
            if (*((char *)tablero[fila][columna]) == 'o' || *((char *)tablero[fila][columna]) == 'X' || *((char *)tablero[fila][columna]) == ' ' || *((char *)tablero[fila][columna]) == '+'){
                printf("|\e[1m %c \e[0m",*((char *)tablero[fila][columna]));
            }
            else {
                printf("|   ");
            }
		}
		printf("| \n");
	}
    printf("\e[1m Y\e[0m\n");
    
    for (int i=0;i<tamaño*4+6;i++){printf("-");}
}

//No imprime en pantalla, pero si revela todos los barcos que no han
//sido destruidos. Esta funcion se ocupa para el fin de la partida
void revelarTablero(){
    for (int fila = 0; fila < tamaño; fila++) {
        for (int columna = 0; columna < tamaño; columna++) {
            char coordenada = *((char *)tablero[fila][columna]);
            if (coordenada == ' '){
                *((char *)tablero[fila][columna]) = '-';
            }
            if (coordenada == 'B'){
                *((char *)tablero[fila][columna]) = '+';
            }
        }
    }
}

//Se encarga de generar x cantidad de barcos segun la dificultad
//que escogio el usuario. Una vez que un barco ha sido asignado
//al tablero, se crea otro barco, reemplazando el anterior, evitando
//asi tener que usar arreglos u otro tipo de estructuras, y ademas
//evitando el uso innecesario de memoria
void generarBarcos() {
    for (int cantidad = 0; cantidad<BarcoS; cantidad++){
        Barco Barco;
        Barco.largo = 2;
        asignarPosicion(&Barco);
        insertarBarco(&Barco);
    }
    for (int cantidad = 0; cantidad<BarcoM; cantidad++){
        Barco Barco;
        Barco.largo = 3;
        asignarPosicion(&Barco);
        insertarBarco(&Barco);
    }
    for (int cantidad = 0; cantidad<BarcoL; cantidad++){
        Barco Barco;
        Barco.largo = 4;
        asignarPosicion(&Barco);
        insertarBarco(&Barco);
    }
    for (int cantidad = 0; cantidad<BarcoXL; cantidad++){
        Barco Barco;
        Barco.largo = 5;
        asignarPosicion(&Barco);
        insertarBarco(&Barco);
    }
}

//Esta funcion se encarga de asegurarse que la posicion es aleatoria,
//y si esta dentro del tablero. De lo contrario se llama a una funcion
//encargada de resolver la colision. En todo este proceso se usa funcion
//recursiva, y el ciclo no terminara hasta que el barco este dentro del
//tablero y que ademas no choque con otros barcos.
void asignarPosicion(Barco *Barco) {
    srand(time(NULL));
    int direccion = (rand()*aleatorio) % 2;
    int x = (rand()*aleatorio) % tamaño;
    int y = (rand()*aleatorio+1) % tamaño;
    if (x < 0) {x = x*-1;}//Transforma a positivo si es que es menor que 0
    if (y < 0) {y = y*-1;}//Transforma a positivo si es que es menor que 0
    if (direccion < 0) {direccion = direccion*-1;}//Transforma a positivo si es que es menor que 0
    aleatorio += 1;

    bool dentro = True;
    if (direccion == Horizontal) {
        if (x + Barco->largo > tamaño) {
            return asignarPosicion(Barco);
            dentro = False;
        }
    } else if (direccion == Vertical) {
        if (y + Barco->largo > tamaño) {
            return asignarPosicion(Barco);
            dentro = False;
        }
    }

    if (dentro == True){
        Barco->direccion = direccion;
        Barco->x = x;
        Barco->y = y;
    }
    verificarChoque(Barco);
}

//Se encarga de verificar que el barco no choque con otro barco,
//recorriendo el largo del barco y verificando casilla por casilla.
//Si choca entonces vuelve a llamar la funcion asignarPosicion, para
//que vuelva a asignarse un lugar aleatorio.
void verificarChoque(Barco *Barco){
    int direccion = Barco->direccion;
    int x = Barco->x; int y = Barco->y;
    int largo = Barco->largo;

    bool choque = False;
    if (direccion == Horizontal){
        for (int columna = x; (columna < largo+x) && (choque == False); columna++){
            if (*((char *)tablero[y][columna]) != ' '){
                choque = True;
            }
        }
    }
    else if (direccion == Vertical){
        for (int fila = y; (fila < largo+y) && (choque == False); fila++){
            if (*((char *)tablero[fila][x]) != ' '){
                choque = True;
            }
        }
    }
    if (choque == True){
        asignarPosicion(Barco);
        insertarBarco(Barco);
    }
}

//Si sale bien del ciclo de funcion recursiva, entonces el barco
//puede ingresarse bien en el tablero
void insertarBarco(Barco *Barco){
    int direccion = Barco->direccion;
    int x = Barco->x; int y = Barco->y;
    int largo = Barco->largo;

    if (direccion == Horizontal){
        for (int columna = x; columna < largo+x; columna++){
            *((char *)tablero[y][columna]) = 'B';
        }
    }
    else if (direccion == Vertical){
        for (int fila = y; fila < largo+y; fila++){
            *((char *)tablero[fila][x]) = 'B';
        }
    }
}

//Funcion que se encarga por cada turno verificar el estado
//de juego. Si la cantidad de X es igual a la cantidad de casillas
//que ocupan los barcos, entonces todos han sido hundidos
int verificarTablero(){
    int numHundido = 0;
    for (int fila = 0; fila < tamaño; fila++) {
        for (int columna = 0; columna < tamaño; columna++) {
            char coordenada = *((char *)tablero[fila][columna]);
            if (coordenada == 'X'){
                numHundido += 1;
            }
        }
    }
    return numHundido;
}