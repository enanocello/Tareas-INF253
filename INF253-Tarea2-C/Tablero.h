#ifndef Tablero
#define Tablero

//Define el tipo bool
typedef int bool;
#define False 0
#define True 1

extern int dificultad;
#define Facil 1
#define Medio 2
#define Dificil 3

#define Horizontal 0
#define Vertical 1

extern int tamaño;//Tamaño del tablero
extern int turnos;//Cantidad de turnos segun dificultad

//Tamaños de los barcos
extern int BarcoS;
extern int BarcoM;
extern int BarcoL;
extern int BarcoXL;
#define totalFacil 16;//Casillas ocupadas por barcos en FACIL
#define totalMedio 21;//Casillas ocupadas por barcos en MEDIO
#define totalDificil 30;//Casillas ocupadas por barcos en DIFICIL
extern int totalBarcos;

extern int aleatorio;//Numero que ayudara a mejorar la aleatoriedad

extern void *** tablero;

typedef struct{
	int largo;
	int direccion;
	int x; int y;
} Barco;

void inicializarTablero(int tamaño);

void liberarTablero(int tamaño);

void mostrarTablero();

void revelarTablero();

void generarBarcos();

void asignarPosicion(Barco *Barco);

void insertarBarco(Barco *Barco);

void verificarChoque(Barco *Barco);

int verificarTablero();

#endif