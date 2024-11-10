#lang racket
(define nl newline)

;Lista donde se guardaran los valores
(define lista (list))

;;Se define la funcion rotar, que se encarga de dejar el primer elemento al final cuando se requiera
;;funciones : Lista de funciones a iterar
(define (rotar funciones)
	(append (cdr funciones) (list (car funciones)))
)

;;Se encarga de tomar la lista de funciones y hacer la composicion segun el orden
;;Una vez la variable haya sido iterada por todas las funciones se guarda en la lista
;;funciones : Lista de funciones a iterar
;;variable : Variable a iterar a lo largo de las funciones
(define (evaluar funciones variable)
	(cond
		((null? funciones)
			(set! lista (append lista (list variable)));Si no hay mas funciones, se agrega a la lista
		)
		(else
			(evaluar (cdr funciones) ((car funciones) variable));Funcion recursiva
		)
	)
)

;;Se encarga de evaluar las funciones por cada numero
;;Si no quedan mas numeros retorna la lista
;;funciones : Lista de funciones a iterar
;;numeros : Lista de "variables" a iterar en las funciones
(define (evaluador funciones numeros)
	(cond
		((null? numeros)
			(display "Valores obtenidos: ")(display lista)
			(set! lista '());Reset a la lista debido a su ambito global
		)
		(else
			(begin
			(evaluar funciones (car numeros))
			(evaluador (rotar funciones) (cdr numeros));Rota la lista de funciones y aplica funcion recursiva
			)
		)
	)
)

;;EJEMPLOS
(evaluador (list (lambda (x) (+ x 1)) (lambda (x) (* x x)) (lambda (x) (- x 2))) '(2 5 7))	(nl)
(evaluador (list (lambda (x) (/ x 3.2)) (lambda (x) (+ (* x 2) x)) (lambda (x) (- x (* 5.40 (* x x))))) '(5 2 -7))	(nl)
(evaluador '() '())	(nl)