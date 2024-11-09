# ProyectoTarea
## Autor
- **Nombre:** José David Guevara Rodriguez
- **Alias:** Goldini
- **Correo:** josedavidguevara@paucasesnovescifp.cat
- **Curso:** 2n DAM

## Descripción
La aplicación es una interfaz gráfica de usuario (GUI) desarrollada en Java 
utilizando Swing y VLCJ para la reproducción de videos.

## Documentación del proceso

### 1. Creación del proyecto
Al crear el proyecto lo he hecho desde netbeans como un proyecto Maven, he tenido un problema
al principio con el archivo nbactions.xml, ya que no me dejaba ejecutar el proyecto en un
ordenador distinto al de clase, lo he solucionado eliminando un elemento del archivo que marcaba
una ruta absoluta.

### 2. Creación de la interfaz
La interfaz ha sido creada con el editor visual de Netbeans, al principio no he sabido como
distribuir los elementos de la interfaz, luego de un comentario del profesor me he dado cuenta
de que se puede colocar un panel sobre otro e ir alternando su visibilidad.
Otro problema relacionado con el diseño que he tenido es que no sabía como distribuir los datos
que pide el ejercicio, al final he decidido solo 2 tablas, una para usuarios y otro para los intentos.
En cuanto a los comentarios de los ejercicios, planeo presentarlos en el text area de la derecha.

### 3. Desarrollo del código
El código ha sido desarrollado en su mayoría en la clase principal, había pensado hacer una clase
aparte para la interfaz, pero al final he decidido hacerlo todo en la misma clase, ya que he ido corto de tiempo.
He creado una ventana para hacer el login y un apartado de recursos varios para algunas clases funcionales.

- En cuanto a la reproducción de videos, he tenido problemas con la librería VLCJ, porque no me dejaba reproducir
debido a que la última versión de VLC no es compatible con la librería, he tenido que buscar una versión antigua.

- En cuanto a rutas de archivos, he tenido problemas con las rutas relativas, al elegir archivos de cualquier tipo
en el IDE netbeans, la ruta que devuelve es incorrecta, he tenido que buscar una solución para que las rutas relativas sean correctas, 
la solución fue agregarlas desde la capeta principal del proyecto.

- En cuanto a las tablas, me he encontrado con que si una tabla es editable, a veces da problemas al convertir
valores en int, he tenido que buscar una solución para que no se puedan editar las tablas. Encontré que
se puede alterar el modelo de la tabla para que no sea editable.

  public boolean isCellEditable(int row, int column) {
  return false; // La celda no es editable
  }
## Bibliografías
### Código
Me gustaría poner una referencia a la página de la que he sacado algunos códigos; 
sin embargo, en su mayoría he escrito con distintas inteligencias artificiales,
también he buscado en stackoverflow y no he guardado las referencias.

