%GRAFO
puente(p12,p5,2).
puente(p11,p12,3).
puente(p5,p1,7).
puente(p11,p4,7).
puente(p1,p4,1).
puente(p1,c,4).
puente(p2,p1,3).
puente(p6,p2,2).
puente(p4,c,3).
puente(p7,p6,4).
puente(p10,p4,6).
puente(p10,p3,3).
puente(p3,c,2).
puente(p2,p3,7).
puente(p7,p2,3).
puente(p9,p10,10).
puente(p8,p9,3).
puente(p8,p3,4).

%Comienza a buscar los caminos
camino(S, Res) :-
	camino(S, c, [S], Res).

%Si es centro entonces se detiene
camino(C, C, Camino, Camino).

%Agrega a camino acumulado si planeta no ha sido visitado
camino(PlanetaActual, Destino, CaminoAcumulado, Res) :-
    puente(PlanetaActual, ProximoPlaneta, _),
    \+ member(ProximoPlaneta, CaminoAcumulado),
    camino(ProximoPlaneta, Destino, [ProximoPlaneta | CaminoAcumulado], Res).

%Comienza a buscar los caminos con combustible inicial V
combustible(S, V, Res) :-
    combustible(S, c, V, [S-V], Res).

%Si es centro se detiene
combustible(C, C, _, Res, Res).

%Se encarga de verificar recursivamente que el combustible alcanza
combustible(PlanetaActual, Destino, CombustibleRestante, CaminoAcumulado, Res) :-
    puente(PlanetaActual, ProximoPlaneta, Costo),
    CombustibleRestante >= Costo,
    NuevoCombustible is CombustibleRestante - Costo,
    \+ member(ProximoPlaneta, CaminoAcumulado),
    combustible(ProximoPlaneta, Destino, NuevoCombustible, 
               [[ProximoPlaneta-NuevoCombustible] | CaminoAcumulado], Res).