# Practica_combinada_final
Practica combinada de Entornos de Desarrollo y Base de Datos para la Evaluación del 3º Trimestre

Comenzamos importando la base de datos "tienda" https://github.com/mariannsss/Practica_combinada_final/blob/main/tienda.sql a nuestro servidor MySQL.

A continuación para poder conectarse la base de datos MySQL o MariaDb desde eclipse, necesitaremos el driver mysql-connector de Java https://github.com/mariannsss/Practica_combinada_final/blob/main/mysql-connector-java-8.0.29.jar, deberás añadirlo a la libreria del proyecto de java.

Corregimos los errores en los comentarios y print generados al copiar el codigo y  no leer bien este las tildes

Editamos o añadimos los comentarios para javadoc

Separamos la rama gráfica de la rama principal

Metemos DBManager en un paquete

Cambiamos la clase Statement por PreparedStatement y los cambios necesarios derivados de ello.
Añadimos prints a las excepciones para saber cual salta (esto luego puede que desaparesca)

Creamos una nueva opción en el menu para mostrar los primeros 5 clientes usando un procedimiento almacenado y la Clase CallableStatement.

Creamos una nueva opcion en el menu para crear una tabla nueva 
Creamos una nueva opcion en el menu para filtrar filas
Creamos un nuevo menu para elegir base de datos y una nueva clase BBDD

Sacamos el menu de la clase GestionClientes y le ponemos e su propia clase Menu
Creamos nueva opcion en el menu para seleccionar la base de datos
Modificamos el programa para que al inicio pregunte por la base de datos y la tabla
Creamos una nueva opcion en el menu para volcar el contenido de una tabla en un fichero
Creamos una nueva clase MenuTest para realizar las pruebas JUnit
