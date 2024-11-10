import pysimplex
import re
ciclo = 0

'''
***
Sin parametros
...
***
Sin retorno
***
Funcion main del programa. Toma la direccion del codigo y recorre cada
linea, agregando a la lista "codigos" una tupla, que contiene el numero de linea
y la linea respectiva. Luego se inicializa el output y dentro se ejecuta
la funcion principal.
'''
def main():
    direccion = "codigo.txt"
    codigo = []; codigo.append((0,"")) #Se agrega uno vacio para que el indice coincida con el numero de linea
    num_linea = 0; variables = {} #En este diccionario se guardaran las variables
    with open(direccion) as archivo: #Se usa with para que se cierre de forma segura en caso de error
        for linea in archivo:
            num_linea+=1
            codigo.append((num_linea,linea.strip()))
    with open("output.txt","w") as salida:
        procesarCodigo(codigo,salida,variables) #Se necesitara las variables como parametro para poder trabajar en diferentes ambitos

'''
***
codigo : Lista de tuplas
salida : Direccion del output para escribir
variables : Diccionario con las variables del codigo
...
***
Sin retorno
***
Funcion principal del programa. Primero verifica los if anidados,
para que no sean mas que 3 (Cabe mencionar que esta funcion se llama
a si misma varias veces). Luego recorre el codigo para verificar
la sintaxis, tokenizar y hacer las operaciones respectivas.
Si topa con un if, toma el bloque completo del condicional, para
asi llamar a la misma funcion y que se creen variables dentro del
ambito. Una vez terminado el recorrido del condicional y que haya vuelto
al codigo original, se eliminan todas las lineas del condicional, para
que asi el programa continue con la ejecucion del resto del codigo.
'''
def procesarCodigo(codigo,salida,variables):
    global ciclo #La variable ciclo cuenta los if anidados
    ciclo += 1
    indice = 0
    while indice < len(codigo): 
        num_linea,linea = codigo[indice]
        if ciclo > 3: pysimplex.ciclos() #Si son mas de 3 if anidados se detiene la ejecucion
        indice += 1
        if linea == "": continue #Omite las lineas vacias

        pysimplex.verificarSintaxis(linea,num_linea) #Verifica la sintaxis de la linea
        tokens = pysimplex.tokenizar(linea) #Divide la linea en tokens

        if "DEFINE" in tokens[0]:
            variable = tokens[1]
            pysimplex.verificarDEFINE(variable,variables,num_linea)
            variables[variable] = None #Inicializa la variable en None

        elif "MOSTRAR" in tokens[0]:
            variable = tokens[1]
            pysimplex.verificarMOSTRAR(variable,variables,num_linea)
            salida.write(str(variables[variable])+"\n")

        elif "DP" in tokens[0]:
            variable = tokens[1]

            if "ASIG" in tokens[2]: #Comprueba si es operacion unaria
                resultado = pysimplex.ejecutarDP(tokens,variables,True,num_linea)
                variables[variable] = resultado
            else: #Ejecuta operacion binaria
                resultado = pysimplex.ejecutarDP(tokens,variables,False,num_linea)
                variables[variable] = resultado

        elif "if" in tokens[0]:
            variable = tokens[1][1:-1]
            valor_condicional = variables[variable]

            indices = pysimplex.ejecutarCondicional(codigo,num_linea,linea,variable,variables)
            indice_if,indice_else,indice_fin = indices

            bloque_condicional = codigo[indice_if+1:indice_fin+1]
            nuevas_variables = {}
            for variable in variables: #Se crea una copia de las variables para asi operarlas en el ambito del if y que luego se eliminen
                 nuevas_variables[variable] = variables[variable]

            if valor_condicional: #Si se cumple la condicion ejecuta las lineas del if usando funcion recursiva
                procesarCodigo(codigo[indice_if+1:indice_else],salida,nuevas_variables)
            else: #Si no se cumple la condicion ejecuta las lineas del else usando funcion recursiva
                procesarCodigo(codigo[indice_else+1:indice_fin],salida,nuevas_variables)
            
            nuevo_codigo = [linea for linea in codigo if linea not in bloque_condicional]
            codigo = nuevo_codigo #Se eliminan del codigo el bloque completo del condicional, para que una vez terminado siga con el resto del codigo
    ciclo -= 1


if __name__ == "__main__":
    try: main()
    except: pysimplex.error()
    print("-"*48 + f"\nLa ejecución del codigo se completo sin errores.\n" + "-"*48)