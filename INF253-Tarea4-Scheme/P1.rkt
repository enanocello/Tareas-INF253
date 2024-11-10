#lang racket
(define nl newline)

;;Se encarga de buscar el elemento en la lista
;;lista : lista donde se buscara el elemento
;;elemento : elemento a buscar 
(define (buscador lista elemento)
	;Se crea ambito let para usar variable indice en la recursividad
	(let ejecutar ((lista lista)(elemento elemento)(indice 1))
		(cond
			((null? lista) (display "Posicion: ")(display -1));Si no encuentra devuelve -1
			((equal? elemento (car lista)) (display "Posicion: ")(display indice));Si lo encuentra devuelve la posicion
			(else (ejecutar (cdr lista) elemento (+ indice 1)));Funcion recursiva
		)
	)
)

;;EJEMPLOS
(buscador '(1 2 3) 3)(nl)
(buscador '(ABC "ABC" 3.0 1234) "ABC")(nl)
(buscador '(ABC "ABC" 3.0 1234) 'ABC)(nl)
(buscador '(389 (2 4 5.0) (40 here 2)) '(40 here 2))(nl)
(buscador '() 'INF253)(nl)
