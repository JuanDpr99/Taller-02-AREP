<h1 align="center">Taller 02 – Framework Web (AREP)</h1>

## Descripción
Este proyecto implementa un **mini framework web en Java**, desarrollado desde cero como parte del curso **Arquitecturas Empresariales (AREP)**.  
El framework permite:
- Definir servicios REST con lambdas usando el método `get()`.
- Extraer parámetros de consulta (`query params`) desde la URL.
- Servir archivos estáticos (HTML, CSS, JS, imágenes) con el método `staticfiles()`.

El objetivo es comprender la arquitectura básica de un servidor web y los protocolos HTTP, sin depender de frameworks externos como Spring o Spark.

---

## Tabla de Contenidos
1. [Características](#características)  
2. [Requisitos](#requisitos)  
3. [Instalación](#instalación)  
4. [Uso](#uso)  
5. [Arquitectura](#arquitectura)  
6. [Pruebas](#pruebas)
   
---

## Características
- **`get(path, lambda)`**: define rutas REST asociadas a lambdas que reciben `HttpRequest` y `HttpResponse`.
- **Extracción de parámetros**: `req.getValue("name")`.
- **`staticfiles(dir)`**: expone archivos estáticos desde un directorio dado.
- Manejo básico de errores: respuestas `404 Not Found` y `500 Internal Server Error`.
- Soporte de tipos MIME para HTML, CSS, JS, PNG, JPG, GIF, JSON.
- Implementado en Java estándar, sin dependencias externas.

---

## Requisitos
- **Java 17+**
- **Maven 3.8+**
- Sistema operativo: Windows, Linux o macOS

---

## Instalación
Clonar el repositorio:

```bash
git clone https://github.com/JuanDpr99/Taller-02-AREP.git
cd Taller-02-AREP
```
## Uso
```bash
public static void main(String[] args) throws Exception {
    WebServer.staticfiles("webroot");

    WebServer.get("/hello", (req, res) -> "Hello " + req.getValue("name"));
    WebServer.get("/pi", (req, res) -> String.valueOf(Math.PI));

    WebServer.start(35000);
}
```
### Ejemplos de endpoints
```bash
* http://localhost:35000/app/hello?name=JuanParroquiano → Hello JuanParroquiano
* http://localhost:35000/app/pi → 3.141592653589793
* http://localhost:35000/index.html → Página HTML estática
* http://localhost:35000/img/logo.png → Imagen estática
* http://localhost:35000/css/styles.css → Archivo plano styles.css
```
## Arquitectura
```bash
- HttpServer: servidor principal, acepta conexiones y despacha solicitudes.
- HttpRequest: parsea parámetros de consulta de la URL.
- HttpResponse: construye respuestas HTTP.
- Service: interfaz funcional para definir lambdas REST.
- WebApp: ejemplo de uso y punto de entrada.
```
# Diagrama
```bash
mavenprojectserver/
 └── src/
     └── main/
         └── java/
             └── escuelaing
                 ├── WebServer.java
                 ├── HttpRequest.java
                 └── HttpResponse.java
             └── webexample/
                 └── WebApplication.java                 
```

# Pruebas
<p align="center">
  <img width="544" height="91" alt="image" src="https://github.com/user-attachments/assets/42d86588-5401-4246-81a4-66838126e8b6" />
  <img width="471" height="87" alt="image" src="https://github.com/user-attachments/assets/aab43b38-8d23-4348-a648-3542828f00cc" />
  <img width="749" height="451" alt="image" src="https://github.com/user-attachments/assets/c498233b-425c-47ff-8801-0e97aac8d33d" />
  <img width="714" height="473" alt="image" src="https://github.com/user-attachments/assets/73c0ce8d-daf4-47ba-b0ff-711e4a202245" />
  <img width="644" height="173" alt="image" src="https://github.com/user-attachments/assets/dbcff121-2df3-445f-9ca8-2eb6a53dc0a1" />
</p>



