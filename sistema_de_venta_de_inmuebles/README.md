# Sistema de venta de inmuebles

## Requisitos

- JDK 25 (el proyecto compila con `--release 25`).
- Apache Maven 3.9 o posterior.

## Preparar Windows

1. Instala un JDK 25, por ejemplo Eclipse Temurin 25, desde
   <https://adoptium.net/temurin/releases/?version=25>.
2. Instala Apache Maven 3.9 o posterior desde
   <https://maven.apache.org/download.cgi> y descomprime el archivo.
3. Configura `JAVA_HOME` con la carpeta raíz del JDK 25 (la carpeta que contiene
   `bin`) y agrega `%JAVA_HOME%\bin` y la carpeta `bin` de Maven al `Path`.
   Cierra y vuelve a abrir PowerShell para que tome los cambios.
4. Comprueba las versiones:

   ```powershell
   java -version
   javac -version
   mvn -version
   ```

   Java y `javac` deben indicar la versión 25; Maven debe indicar 3.9 o posterior
   y mostrar que está usando Java 25.

## Compilar y ejecutar

Desde esta carpeta (`sistema_de_venta_de_inmuebles`):

```powershell
mvn clean compile
mvn exec:java
```

También se puede ejecutar ambas fases con `mvn clean compile exec:java`.

Actualmente, el punto de entrada es `co.edu.uptc.inmobiliaria.Run.Main`. Ejecuta
operaciones de prueba y no abre una ventana gráfica. `InmobiliariaGUI` todavía
no tiene una interfaz implementada ni es invocada desde `Main`.
