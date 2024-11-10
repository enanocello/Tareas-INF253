#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include "Cartas.h"
#include "Tablero.h"

//Se inicializan en 0 para evitar problemas
int x = 0;
int y = 0;
int numCarta = 0;
bool disparoUltra = True; //Define si 500KG esta disponible
bool mensaje = True;

Mano cartas;//Inicializa la mano

//Se pide espacio en memoria para cada carta de la mano
//Son 5 cartas, sin embargo yo pedi 6 espacios, para que asi
//sea mas visual al programador y no empezar la cuenta desde 0
void inicializarMano() {
    cartas.disponibles = 5;
    cartas.carta = malloc((cartas.disponibles+1) * sizeof(int *));
    for (int num = 0; num <= cartas.disponibles; num++) {
        cartas.carta[num] = malloc(sizeof(int));
        *(int *)cartas.carta[num] = Simple;//Empieza la partida con solo disparoSimple
    }
}

//Libera el espacio en memoria solicitado 
void liberarMano() {
    for (int num = 0; num <= cartas.disponibles; num++) {
        free(cartas.carta[num]);
    }
    free(cartas.carta);
}

//Muestra en pantalla las cartas disponibles
void mostrarMano() {
    if (dificultad == Facil){printf("\n->");}
    else if (dificultad == Medio){printf("\n           ->");}
    else if (dificultad == Dificil){printf("\n                   ->");}
    for (int num = 1; num <= 5; num++){
        if (*(int *)cartas.carta[num] == NoDisp) {printf("| ------ ");}
        else if (*(int *)cartas.carta[num] == Simple) {printf("| Simple " );}
        else if (*(int *)cartas.carta[num] == Grande) {printf("| Grande " );}
        else if (*(int *)cartas.carta[num] == Lineal) {printf("| Lineal " );}
        else if (*(int *)cartas.carta[num] == Radar) {printf("| Radar " );}
        else if (*(int *)cartas.carta[num] == Ultra) {printf("| 500KG " );}
    }
    printf("|<-\n");
}

//Se usa la carta y se efectua el disparo correspondiente
//Ademas dentro de la funcion se actualiza la carta con un
//nuevo disparo, el cual varia segun las probabilidades
void usarCarta(){
    srand(time(NULL));//Se genera la semilla segun la hora
    int tipoDisparo = *(int *)cartas.carta[numCarta];
    int num;//Numero aleatorio
    if (disparoUltra == False){
        if (tipoDisparo == Radar){
            num = (rand()*aleatorio) % 98;    
        }
        num = (rand()*aleatorio) % 99;
    }
    else{
        num = (rand()) % 101;
    }
    aleatorio += 1;//Aleatorio se usa para aumentar la aleatoriedad

    switch (tipoDisparo){
    case NoDisp://Cuando se usa el 500KG entonces el cañon queda NoDisponible
        printf("\e[1;33m        MISS\e[0m");
        fflush(stdout);
        break;
    case Simple:
        disparoSimple(x,y);
        if (num>=0 && num<=65){*(int *)cartas.carta[numCarta] = Simple;}//65%
        else if (num>=66 && num<=85){*(int *)cartas.carta[numCarta] = Grande;}//20%
        else if (num>=86 && num<=95){*(int *)cartas.carta[numCarta] = Radar;}//5%
        else {*(int *)cartas.carta[numCarta] = Lineal;}//10% etc...
        break;
    case Grande:
        disparoGrande(x,y);
        if (num>=0 && num<=80){*(int *)cartas.carta[numCarta] = Simple;}
        else if (num>=81 && num<=83){*(int *)cartas.carta[numCarta] = Grande;}
        else if (num>=84 && num<=93){*(int *)cartas.carta[numCarta] = Lineal;}
        else if (num>=94 && num<=98){*(int *)cartas.carta[numCarta] = Radar;}
        else if (num>=99 && num<=10){*(int *)cartas.carta[numCarta] = Ultra;}
        break;
    case Lineal:
        disparoLineal(x,y);
        if (num>=0 && num<=85){*(int *)cartas.carta[numCarta] = Simple;}
        else if (num>=86 && num<=90){*(int *)cartas.carta[numCarta] = Grande;}
        else if (num>=91 && num<=92){*(int *)cartas.carta[numCarta] = Lineal;}
        else if (num>=93 && num<=98){*(int *)cartas.carta[numCarta] = Radar;}
        else if (num>=99 && num<=10){*(int *)cartas.carta[numCarta] = Ultra;}
        break;
    case Radar:
        disparoRadar(x,y);
        if (num>=0 && num<=75){*(int *)cartas.carta[numCarta] = Simple;}
        else if (num>=76 && num<=90){*(int *)cartas.carta[numCarta] = Grande;}
        else if (num>=91 && num<=95){*(int *)cartas.carta[numCarta] = Lineal;}
        else if (num>=96 && num<=97){*(int *)cartas.carta[numCarta] = Radar;}
        else if (num>=98 && num<=100){*(int *)cartas.carta[numCarta] = Ultra;}
        break;
    case Ultra:
        disparo500KG(x,y);
        *(int *)cartas.carta[numCarta] = NoDisp;
        disparoUltra = False;//Queda inhabilitado
        break;
    }
    sleep(1);
}

//Verifica si hay espacio,  un barco o si ya ha sido explotado
//Si no encuentra entonces es un HIT
void *disparoSimple(int x, int y){
    char coordenada = *((char *)tablero[y][x]);
    if (coordenada == ' ' || coordenada == 'o' || coordenada == 'X'){
        miss(x,y);
    }
    else {
        hit(x,y);
    }
}

//Verifica que los limites esten bien, y luego verifica que este desocupado
//o si es un barco ya explotado. Si no, es HIT
void *disparoGrande(int x, int y){
    int finX = x + 1;
    int finY = y + 1;
    x = x - 1;
    y = y - 1;
    if (x < 0) {x = 0;}
    if (y < 0) {y = 0;}
    if (finX >= tamaño) {finX = tamaño - 1;}
    if (finY >= tamaño) {finY = tamaño - 1;}

    for (int fila = y; fila <= finY; fila++) {
        for (int columna = x; columna <= finX; columna++) {
            char coordenada = *((char *)tablero[fila][columna]);
            if (coordenada == ' ' || coordenada == 'o' || coordenada == 'X'){
                miss(columna,fila);
            }
            else {
                hit(columna,fila);
            }
        }
    }
}

//Pide la direccion del misil, y luego verifica los limites, igual
//que el anterior. Si es horizontal modifica solo en las columnas,
//y si es vertical solo en las filas
void *disparoLineal(int x, int y){
    int direccionLinea;
    printf("Direccion del misil: 1. Horizontal | 2. Vertical\n");
    scanf("%d", &direccionLinea); direccionLinea = direccionLinea - 1;

    int finX = x + 4;
    int finY = y + 4;
    if (finX >= tamaño) {finX = tamaño - 1;}
    if (finY >= tamaño) {finY = tamaño - 1;}

    if (direccionLinea == Horizontal) {
        for (int columna = x; columna <= finX; columna++){
            char coordenada = *((char *)tablero[y][columna]);
            if (coordenada == ' ' || coordenada == 'o' || coordenada == 'X'){
                miss(columna,y);
            }
            else {
                hit(columna,y);
            }
        }
    }
    else if (direccionLinea == Vertical) {
        for (int fila = y; fila <= finY; fila++){
            char coordenada = *((char *)tablero[x][fila]);
            if (coordenada == ' ' || coordenada == 'o' || coordenada == 'X'){
                miss(x,fila);
            }
            else {
                hit(x,fila);
            }
        }
    }
}

//Este disparo no ejecuta misiles, por lo tanto no tiene
//HIT ni MISS, pero si revela la posicion de los barcos.
//Si encuentra una B, entonces lo reemplaza por un '+', el
//cual al no estar dentro de las condiciones para ocultarse,
//se muestra.
void *disparoRadar(int x, int y){
    int finX = x + 1;
    int finY = y + 1;
    x = x - 1;
    y = y - 1;
    if (x < 0) {x = 0;}
    if (y < 0) {y = 0;}
    if (finX >= tamaño) {finX = tamaño - 1;}
    if (finY >= tamaño) {finY = tamaño - 1;}

    for (int fila = y; fila <= finY; fila++) {
        for (int columna = x; columna <= finX; columna++) {
            char coordenada = *((char *)tablero[fila][columna]);
            if (coordenada == 'B'){
                *((char *)tablero[fila][columna]) = '+';
            }
        }
    }
    printf("\e[1;33m     Rastreando...\e[0m\n");
}

//Este disparo funciona igual que el disparo grande, solo que
//el area es mas grande. Los limites los verifica igual que en
//los anteriores disparos.
void *disparo500KG(int x, int y){
    int finX = x + 5;
    int finY = y + 5;
    x = x - 5;
    y = y - 5;
    if (x < 0) {x = 0;}
    if (y < 0) {y = 0;}
    if (finX >= tamaño) {finX = tamaño - 1;}
    if (finY >= tamaño) {finY = tamaño - 1;}

    mensaje = False;//Se desactiva para omitir los HIT y MISS y asi imprimir ULTRAHIT
    for (int fila = y; fila <= finY; fila++) {
        for (int columna = x; columna <= finX; columna++) {
            char coordenada = *((char *)tablero[fila][columna]);
            if (coordenada == ' ' || coordenada == 'o' || coordenada == 'X'){
                miss(columna,fila);
            }
            else {
                hit(columna,fila);
            }
        }
    }
    printf("\e[1;31m      ULTRA HIT\e[0m\n");
    fflush(stdout);
    mensaje = True;
    usleep(80000);
}

//Funcion que imprime HIT y cambia el caracter del tablero
void hit(int x, int y){
    fflush(stdout);
    if (mensaje == True){
        printf("\e[1;31m        HIT\e[0m\n");
        usleep(80000);
    }
    *((char *)tablero[y][x]) = 'X';
}
//Funcion que imprime MISS y cambia el caracter del tablero
void miss(int x, int y){
    fflush(stdout);
    if (mensaje == True){
        printf("\e[1;33m        MISS\e[0m\n");
        usleep(80000);
    }
    if (*((char *)tablero[y][x]) != 'X'){
        *((char *)tablero[y][x]) = 'o';
    }
}