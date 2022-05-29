# Practica_combinada_final
Practica combinada de Entornos de Desarrollo y Base de Datos para la Evaluación del 3º Trimestre

Comenzamos importando la base de datos "tienda" https://github.com/mariannsss/Practica_combinada_final/blob/main/tienda.sql a nuestro servidor MySQL.

A continuación para poder conectarse la base de datos MySQL o MariaDb desde eclipse, necesitaremos el driver mysql-connector de Java https://github.com/mariannsss/Practica_combinada_final/blob/main/mysql-connector-java-8.0.29.jar, deberás añadirlo a la libreria del proyecto de java.

A la hora de descargar y usar el programa tiene que tener en cuenta donde tiene la base de datos, el puerto de MySQL y su usuario y contraseña de los mismos.
Estos datos pueden ser cambiados al principio de la clase DBManager

CAMBIOS REALIZADOS:

RAMAS:
Separamos la rama gráfica de la rama principal

CLASES:
Sacamos el menu de la clase GestionClientes y le ponemos e su propia clase Menu
Metemos la clase DBManager en el paquete GestionBD
Creamos una nueva clase BBDD en el paquete GestionBD
Creamos una nueva clase MenuTest para realizar las pruebas JUnit
en propiedades del proyecto en la ruta de construccion de Java (java Build Path) hay que añadir la libreria JUnit 5 a la ruta de acceso de clases (classpath)

OPCIONES DEL MENU:
Creamos una nueva opción en el menu para mostrar los primeros 5 clientes usando un procedimiento almacenado y la Clase CallableStatement
Creamos una nueva opcion en el menu para crear una tabla nueva 
Creamos una nueva opcion en el menu para filtrar filas
Creamos una nueva opcion en el menu para seleccionar la base de datos y la tabla
Creamos una nueva opcion en el menu para volcar el contenido de una tabla en un fichero
Creamos 3 nuevas opcion en el menu para insertar, actualizar y borrar desde un fichero
y añadimos una carpeta "archivos" , con el fichero para cada opcion

CAMBIOS EN EL PROGRAMA:
Corregimos los errores en los comentarios y print generados al copiar el codigo y  no leer bien este las tildes
Editamos o añadimos los comentarios para javadoc
Cambiamos la clase Statement por PreparedStatement y los cambios necesarios derivados de ello
Modificamos las excepciones para que muestren un print en lugar de printStackTrace()
ya que estos no deberian verlos los usuarios
Modificamos el programa para que al inicio pregunte por la base de datos y la tabla
Modificamos la clase DBManager y las demas clases para poder trabajar con distintas bases de datos y tablas con 3 campos

Refactorizamos el codigo
eliminamos funciones que no se usan ya
atributos que tampoco se usan ya
simplificando el codigo
y cambiamos el nombre y demas valores a todas funciones de clientes 
ya que ahora puede trabajar con distintas bases de datos y tablas

OTRAS TAREAS:
Generamos la documentacion Javadoc
Realizamos las pruebas JUnit
Crear los diagramas UML 
En la carpeta esta el archivo .uml que puedes abrir desde cualquier aplicacion para crear UML o desde eclipse (puede que necesites descargar un plugin primero)
tambien esta en formato pdf para que puedas abrirlo con cualquier lector de pdf
Editamos el Readme para ordenarlo y presentarlo adecuadamente como memoria breve
