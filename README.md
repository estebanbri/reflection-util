# Clases importantes para reflection

### Class.class 
`Tenemos varias alternativas de obtenerla, dos de ellas mas comunmente usadas son:
- De un objeto la obtenemos usando **obj.getClass()** 
- De una clase **NombreClase.class**`

| Metodo | Descripcion                                                                     |
|--------|---------------------------------------------------------------------------------|
|   getDeclaredField(s): Field     | retorna field sin importar modificador de acceso del field dentro de la clase   |
|   getField(s): Field     | retorna field unicamente public                                                 |
|   getDeclaredFields(): Field[]     | todos los field sin importar modificador de acceso del field dentro de la clase |
|   getDeclaredMethod(s,Class...param): Method | retorna metodo sin importar modif de acceso                                     |
|   getSuperclass(): Class.class | retorna la superclase de la Class actual                                        |
|   getDeclaredConstructor() | retorna el constructor de la clase                                              |
|   newInstance() | crea una instancia(objeto) de la clase                                          |

### Field.class
| Metodo | Descripcion |
|--------|-------------|
| isAnnotationPresent(Class)       |  Devuelve true si dicha annotation esta sobre el field           |
|  getAnnotation(Class)      |    Retorna la anotation del field         |
|  setAccessible(true)      | para que cuando el modif de acceso del field no es publico para que te permite setearle valores            |

### Method.class

| Metodo                                     | Descripcion |
|--------------------------------------------|-------------|
| invoke(objeto, Class... paramType): Object |  permite invocar metodos|