#lang racket
(define nl newline)

;;Se define el factorial de un numero
;;RECURSION SIMPLE
;;n : Numero a operar
(define (factorialS n)
	(if (= n 0)
		1
		(* n (factorialS (- n 1)))
	)
)

;;Se define el factorial de un numero
;;RECURSION DE COLA
;;n : Numero a operar
(define (factorialC n)
	(let ciclo ((n n) (i 1))
		(if (= n 0)
			i
			(ciclo (- n 1) (* n i))
		)
	)
)

;;Se define el termino de la Serie en Seno
;;x : Numero a operar
;;k : Iterador de la sumatoria
(define (terminoSeno x k)
	(/
		(* (expt -1 k) (expt x (+ (* 2 k) 1))) ; -1^k * x^(2k+1)
		(factorialS (+ (* 2 k) 1)) ; (2k+1)!
	)
)

;;Se defibe el termino de la Serie en Coseno
;;x : Numero a operar
;;k : Iterador de la sumatoria
(define (terminoCoseno x k)
	(/
		(* (expt -1 k) (expt x (* 2 k))) ; -1^k * x^(2k)
		(factorialC (* 2 k)) ; (2k)!
	)
)

;;Se encarga de realizar la suma desde k=0 hasta n
;;RECURSION SIMPLE
;;n : Cantidad de iteraciones de la sumatoria
;;x : Numero a operar
(define (taylorSenoSimple n x)
	(let ciclo ((n n)(x x)(k 0))
		(cond
			((> k n) 0);Deja de sumar terminos y termina la funcion recursiva
			(else (+ (terminoSeno x k) (ciclo n x (+ k 1))));Funcion recursiva
		)
	)
)

;;Se encarga de realizar la suma desde k=0 hasta n
;;RECURSION DE COLA
;;n : Cantidad de iteraciones de la sumatoria
;;x : Numero a operar
(define (taylorCosenoCola n x)
	(let ciclo ((n n)(x x)(total 0)(k 0))
		(cond
			((> k n) total);Termina la serie si k es mayor que n
			(else (ciclo n x (+ total (terminoCoseno x k)) (+ k 1)));Funcion recursiva
		)
	)
)

;;EJEMPLOS
(display(taylorSenoSimple 300 3.14))(nl)
(display(taylorCosenoCola 300 3.14))(nl)
(display(taylorSenoSimple 1 2.14))(nl)
(display(taylorCosenoCola 1 2.14))(nl)
(display(taylorSenoSimple 0 20.3))(nl)
(display(taylorCosenoCola 0 20.3))(nl)