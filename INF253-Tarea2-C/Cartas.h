#ifndef Cartas
#define Cartas

//Define el tipo bool
typedef int bool;
#define False 0
#define True 1


extern int x;
extern int y;
extern int numCarta;//Aqui se guarda el numero de carta que se escogio en el juego
extern bool disparoUltra;//Define si 500KG esta disponible

//Tipos de disparo
#define NoDisp 0
#define Simple 1
#define Grande 2
#define Lineal 3
#define Radar  4
#define Ultra  5


typedef struct {
	void **carta;
	int disponibles;
} Mano;
extern Mano cartas;



void *disparoSimple(int x, int y);

void *disparoGrande(int x, int y);

void *disparoLineal(int x, int y);

void *disparoRadar(int x, int y);

void *disparo500KG(int x, int y);

void inicializarMano();

void liberarMano();

void mostrarMano();

void usarCarta();

void hit(int x, int y);

void miss(int x, int y);

#endif