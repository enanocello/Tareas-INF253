All:CityDefender
	./CityDefender
	make clean
	clear


CityDefender: CityDefender.o Cartas.o Tablero.o
	gcc -Wall -w -g -o CityDefender CityDefender.o Cartas.o Tablero.o
CityDefender.o: CityDefender.c Cartas.h Tablero.h
	gcc -Wall -w -g -c CityDefender.c
Cartas.o: Cartas.c Cartas.h Tablero.h
	gcc -Wall -w -g -c Cartas.c
Tablero.o: Tablero.c Cartas.h Tablero.h
	gcc -Wall -w -g -c Tablero.c
clean:
	rm -f CityDefender CityDefender.o Cartas.o Tablero.o