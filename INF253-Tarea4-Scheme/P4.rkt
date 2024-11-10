#lang racket
(define nl newline)

;Lista donde se guardaran las posiciones
(define tesoros (list))

;;Se define la funcion profundidades. Dentro se recorrera el arbol en PRE-ORDEN
;;arbol : Arbol a recorrer
(define (profundidades arbol)
	(let recorrer ((nodo arbol) (altura 0))
		(cond
			[(null? nodo)
				'()
			]
			[(not (list? (car nodo))) ;Verifica si es elemento o lista
				(if (equal? (car nodo) 'T) ;Verifica si es un tesoro
					(set! tesoros (append tesoros (list altura)))
					#f
				)
				(cons (car nodo) (recorrer (cdr nodo) (+ altura 1))) ;Si es elemento recorre el siguiente
			]
			[else
				(append (recorrer (car nodo) altura) (recorrer (cdr nodo) altura)) ;Si es lista la recorre
			]
		)
	)
	(display "Tesoros: ")(display (sort tesoros <))(nl) ;Se imprimen tesoros de menor a mayor
	(set! tesoros (list)) ;Setea la lista para siguientes iteraciones
)

;;EJEMPLOS
(profundidades '(1 (T (2 (T) (3))) (4 (5) (6)) (7 (T (8)))))
(profundidades '(1 (6 (3) (2 (5))) (4 (7 (8) (9)))))
(profundidades '())
(profundidades '(1 (2) (T (3))))
(profundidades '(1 (2 (T) (3)) (4 (5 (T) (6)) (T (7 (8 (T)))))))