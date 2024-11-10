import re

#Se separan las ER en variables mas pequeñas para asi facilitar la lectura al programador usando el formateo
entero      =   "([1-9][0-9]*|0)"
booleano    =   "(True|False)"
string      =   "\#([^#])*\#"
variable    =   "(\$_([A-Z])([A-Z]|[a-z])*)"
asignacion  =   "(ASIG)"
operador    =   "(\+|\*|>|==)"
operando    =   f"({entero}|{booleano}|{string}|{variable})"
esp = "( )+" ; fin = "( )*"

#Patrones de sintaxis
sintaxis = {
    "Declaracion"       : rf"DEFINE{esp}{variable}",
    "Salida"            : rf"MOSTRAR\({variable}\)",
    "Operacion"         : rf"DP{esp}{variable}{esp}(({asignacion}{esp}{operando})|({operador}{esp}{operando}{esp}{operando}))",
    "Condicional"       : rf"(if( )+\({variable}\)( )+\{{)|(\}}( )+else( )+\{{)|(\}})"
}

'''
***
linea : Linea a verificar la sintaxis
num_linea : Numero de la linea
...
***
Sin retorno
***
Se asume que la linea tiene un error de sintaxis, luego se recorre cada
patron, y si encuentra una coincidencia, entonces la sintaxis cumple con
al menos uno de los patrones, por lo tanto se asigna False al error y asi 
no entra en el condicional y continua la ejecucion del codigo
'''
def verificarSintaxis(linea,num_linea):
    error = True
    for tipo in sintaxis:
        if re.fullmatch(sintaxis[tipo],linea): error = False #Si coincide con uno entonces se cumple la sintaxis
    if error:
            print("-"*48 + f"\nError de Sintaxis en la linea {num_linea}\nVerifique la Sintaxis:\n-> \"{linea}\" <-")
            exit()

'''
***
variable : Variable a verificar
variables : Diccionario de variables del codigo
num_linea : Numero de linea de la variable
...
***
Sin retorno
***
Verifica que la variable no este definida anteriormente
'''
def verificarDEFINE(variable,variables,num_linea):
    if variable in variables: #Verifica que la variable no este ya declarada
        print("-"*48 + f"\nError de Asignacion de Variable en la linea {num_linea}\n-> La variable \"{variable[2:]}\" ya existe <-")
        exit()

'''
***
variable : Variable a verificar
variables : Diccionario de variables del codigo
num_linea : Numero de linea de la variable
...
***
Sin retorno
***
Verifica que la variable exista y que tenga un valor
asignado, para asi poder mostrarlo en el output
'''
def verificarMOSTRAR(variable,variables,num_linea):
    if variable not in variables: #Verifica que la variable exista
        print("-"*48 + f"\nError de Salida en la linea {num_linea}\n-> La variable \"{variable[2:]}\" no ha sido definida <-")
        exit()
    elif variables[variable] == None: #Verifica que la variable tenga valor definido
        print("-"*48 + f"\nError de Salida en la linea {num_linea}\n-> A la variable \"{variable[2:]}\" no se le ha asignado un valor <-")
        exit()

'''
***
tokens : Lista de tokens de la linea del codigo
variables: Diccionario de variables del codigo
asignacion : Booleano que define si es operacion unaria o binaria
num_linea : Numero de linea del codigo
...
***
Si es asignacion, retorna el valor para asignar a la variable,
y si es operacion, retorna el resultado de la operacion
***
Si es asignacion entonces verifica que la variable exista,
y si el valor es otra variable entonces verifica que esa
exista y este definida para poder otorgarle el valor.
Si es operacion binaria entonces verifica que los operandos
puedan ser otras variables, y luego efectua la operacion
dependiendo del operador.
'''
def ejecutarDP(tokens,variables,asignacion,num_linea):
    if asignacion:
        variable = tokens[1]; valor = tokens[3]
        if variable not in variables: #Verifica que la variable exista
            print("-"*48 + f"\nError de Asignacion en la linea {num_linea}\n-> La variable \"{variable[2:]}\" no ha sido definida <-")
            exit()
        if valor in variables: #Si el valor es una variable entonces toma el valor 
            if variables[valor] == None: #Verifica que la variable tenga valor definido
                print("-"*48 + f"\nError de Operacion en la linea {num_linea}\n-> A la variable \"{variable[2:]}\" no se le ha asignado un valor <-")
                exit()
            valor = variables[valor]
        if type(valor) == str: #Comprueba que tipo de valor es para efectuar la asignacion
            if "#" in valor: return valor[1:-1]
            elif valor == "True": return True
            elif valor == "False": return False
            else: return int(valor)
        return valor

    else:
        _,variable,operador,operando1,operando2 = tokens
        if operando1 in variables: operando1 = variables[operando1] #Si el operando es una variables entonces toma el valor de la variable
        if operando2 in variables: operando2 = variables[operando2]

        if operador == "+":
            return sumar(operando1,operando2,num_linea)
        elif operador == "*":
            return multiplicar(operando1,operando2,num_linea)
        elif operador == ">":
            return mayorque(operando1,operando2,num_linea)
        elif operador == "==":
            return igualque(operando1,operando2,num_linea)

'''
***
operacion1 : Primer operando
operacion2 : Segundo operando
num_linea : Numero de linea de la operacion
...
***
Resultado de la operacion
***
Verifica las reglas de operacion de la suma, y si se cumplen
se operan
'''
def sumar(operacion1,operacion2,num_linea):
    if (type(operacion1)==bool) or (type(operacion2)==bool): #Verifica que no hay booleanos
        print("-"*48 + f"\nError de Operacion en la linea {num_linea}\n-> No se permite la suma de Booleanos <-")
        exit()
    elif (type(operacion1)==int) and (type(operacion2)==str): #Verifica que no se esta sumando un string a un entero
        print("-"*48 + f"\nError de Operacion en la linea {num_linea}\n-> No se permite la suma de String a Entero <-")
        exit()
    elif (type(operacion1)==str) and (type(operacion2)==int): #Verifica que se esta sumando un entero a un string
        return (f"{operacion1}{str(operacion2)}")
    else:
        return (operacion1 + operacion2) 
    
'''
***
operacion1 : Primer operando
operacion2 : Segundo operando
num_linea : Numero de linea de la operacion
...
***
Resultado de la operacion
***
Verifica las reglas de operacion de la multiplicacion, y si se cumplen
se operan
'''
def multiplicar(operacion1,operacion2,num_linea):
    if (type(operacion1)==bool) or (type(operacion2)==bool): #Verifica que no hay booleanos
        print("-"*48 + f"\nError de Operacion en la linea {num_linea}\n-> No se permite la multiplicacion de Booleanos <-")
        exit()
    elif (type(operacion1)==str) or (type(operacion2)==str): #Verifica que no hay strings
        print("-"*48 + f"\nError de Operacion en la linea {num_linea}\n-> No se permite la multiplicacion de Strings <-")
        exit()
    else:
        return(operacion1 * operacion2)

'''
***
operacion1 : Primer operando
operacion2 : Segundo operando
num_linea : Numero de linea de la operacion
...
***
Resultado de la operacion
***
Verifica las reglas de operacion del mayor que, y si se cumplen
se operan
'''
def mayorque(operacion1,operacion2,num_linea):
    if (type(operacion1)==bool) or (type(operacion2)==bool): #Verifica que no hay booleanos
        print("-"*48 + f"\nError de Operacion en la linea {num_linea}\n-> No se permite la comparacion de Booleanos <-")
        exit()
    elif (type(operacion1)==str) or (type(operacion2)==str): #Verifica que no hay strings
        print("-"*48 + f"\nError de Operacion en la linea {num_linea}\n-> No se permite la comparacion de Strings <-")
        exit()
    else:
        return(operacion1>operacion2)

'''
***
operacion1 : Primer operando
operacion2 : Segundo operando
num_linea : Numero de linea de la operacion
...
***
Resultado de la operacion
***
Verifica las reglas de operacion del igual que, y si se cumplen
se operan
'''
def igualque(operacion1,operacion2,num_linea):
    if (type(operacion1)==bool) or (type(operacion2)==bool): #Verifica que no hay booleanos
        print("-"*48 + f"\nError de Operacion en la linea {num_linea}\n-> No se permite la comparacion de Booleanos <-")
        exit()
    elif (type(operacion1)==str) and (type(operacion2)==str): #Verifica que solo sean strings
        return(operacion1==operacion2)
    elif (type(operacion1)==int) and (type(operacion2)==int): #Verifica que solo sean enteros
        return(operacion1==operacion2)
    else:
        print("-"*48 + f"\nError de Operacion en la linea {num_linea}\n-> No se permite la comparacion de Entero con String <-")
        exit()

'''
***
codigo : Codigo que se esta ejecutando
num_linea : Numero de linea del condicional
linea : Linea del condicional
condicion : Variable a comprobar su valor(True o False)
variables : Diccionario de variables del codigo
...
***
Retorna una lista con los tres indices del condicional,
el if, el else, y el fin del if
***
Primero verifica que la variable exista y que sea de tipo
booleana, luego se calculan los indices a traves de dos While.
Se calcula el else respectivo del if si es que este coincide con los if 
encontrados, por ejemplo si hay 3 if, entonces el else del primer if
es el tercer else, y lo mismo con el fin, el cual compara la cantidad
de else con la cantidad de corchetes cerrados
'''
def ejecutarCondicional(codigo,num_linea,linea,condicion,variables):

    if condicion not in variables: #Verifica que la variable este definida
        print("-"*48 + f"\nError de Ejecucion en la linea {num_linea}\n-> La variable \"{condicion[2:]}\" no ha sido definida <-")
        exit()
    if type(variables[condicion]) != bool: #Verifica que la condicion sea booleana
        print("-"*48 + f"\nError de Ejecucion en la linea {num_linea}\n-> La variable no es de tipo Booleano <-")
        exit()

    #En este bloque se calcula el indice del if con el indice de su respectivo else
    cantidad_if = 0; cantidad_else = 0
    indice_if = codigo.index((num_linea,linea)); indice_else = 0
    indice = indice_if
    while indice < len(codigo):
        linea = codigo[indice][1]
        if "if" in linea[:2]: cantidad_if += 1
        elif "else" in linea[:6]: cantidad_else += 1
        if (cantidad_else == cantidad_if) and (cantidad_if != 0):
            indice_else = indice
            break
        indice += 1

    #En este bloque se toma el indice del else para calcular el indice del respectivo fin
    cantidad_else = 0; cantidad_fin = 0;
    indice_fin = 0
    indice = indice_else
    while indice < len(codigo):
        linea = codigo[indice][1]
        if "else" in linea[:6]: cantidad_else += 1
        elif "}" in linea[:1]: cantidad_fin += 1
        if (cantidad_fin == cantidad_else) and (cantidad_else != 0):
            indice_fin = indice
            break
        indice += 1

    return [indice_if,indice_else,indice_fin] #Se retornan los tres indices principales del bloque del condicional


'''
***
linea : Linea a tokenizar
...
***
Retorna una lista de tokens
***
Divide la linea por espacios, tomando en cuenta
los strings ya que estos vienen entre dos "#", y tomando
en cuenta que el mostrar no viene separado por espacios
'''
def tokenizar(linea):
    if "#" in linea: #Para evitar que divida el string
        indice = linea.index("#")
        string = linea[indice:]; linea = linea[:indice].strip()
        tokens = re.split(r'\s+', linea); tokens.append(string)
    else:
        tokens = re.split(r'\s+', linea)
    if "MOSTRAR" in tokens[0]: #Para que separe el MOSTRAR de la variable (ya que no vienen separadas por espacio)
        variable = tokens[0][8:-1]
        tokens[0] = tokens[0][:7]
        tokens.append(variable)
    return tokens

'''
***
Sin parametros
...
***
Sin retorno
***
Si se superan los ciclos anidados salta este error y
termina la ejecucion
'''
def ciclos():
    print("-"*48 + f"\nSe ha superado la cantidad de ciclos anidados")
    exit()

'''
***
Sin parametros
...
***
Sin retorno
***
Si encuentra un error de cualquier tipo entonces se acompaña de este
mensaje. Ademas, si hay un error que no haya sido cubierto por el 
programador (un bug), entonces salta solo este mensaje.
'''
def error():
    print("-"*48 + f"\nTerminando la ejecucion...\n" + "-"*48)
    exit()