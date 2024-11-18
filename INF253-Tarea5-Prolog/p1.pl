%Predicado Principal
esPalindroma(Lista, [Izq, Der]) :-
    obtenerSublista(Lista, Izq, Der, Sublista),
    reverse(Sublista, Sublista).

%Inicia el proceso de extraccion de la lista
obtenerSublista(Lista, Izq, Der, Sublista) :-
    Izq > 0,
    IzqAux is Izq-1,
    obtenerSublistaAux(Lista, IzqAux, Der, Sublista).

%Se encarga de saltar los elementos antes del limite izquierdo
obtenerSublistaAux([_|UltimoEl], Izq, Der, Sublista) :-
    Izq > 0,
    IzqAux is Izq-1,
    obtenerSublistaAux(UltimoEl, IzqAux, Der, Sublista).

%Comienza a agregar elementos
obtenerSublistaAux([PrimerEl|UltimoEl], 0, Der, [PrimerEl|Sublista]) :-
    Der > 0,
    DerAux is Der-1,
    obtenerSublistaAux(UltimoEl, 0, DerAux, Sublista).

%Detiene la recursion
obtenerSublistaAux([], _, _, []).
