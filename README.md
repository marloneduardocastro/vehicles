# Vehicles
## Prueba
Meep '' Prueba Tecnica.
Marlon Eduardo Castro
marloncastro77@gmail.com
Entrega: 11 de Septiembre del 2022

## Required Setup
* Java 1.13
Codigo:
```
Java 1.13
Framework: SprintBoot 
Test: JUnit
IDE: IntelliJ IDEA 2021.2.3
Manejo de dependencias: Gradlew
Contenedor: Docker
Repositorio: Github
Se uso programación funcional (Project reactor)
Para correrlo localmente con la clase VehiclesApplication o generar la imagen Docker
Realizado con WebSockets y cron, se debe subscribir al servicio url:http://localhost:9092/vehicles
Listener: lisboaChanges
Arquitectura DDD.
```

## Preguntas

* ¿Cómo de escalable es tu solución propuesta?
```
La solucion usando WebSockets es escalable porque es optima para aplicaciones orientadas a eventos (esta arquitectura se 
caracteriza por ser escalable), tener comunicaciones en tiempo real y ofrecen la fácilidad de poder integrarse con un 
microservicio nuevo en nuestro ecosistema, permitiendo a cualquier microservicio el envío de mensajes a los distintos 
topics de nuestros websockets. Adicionalmente se implementa con programación reactiva que es elstica y escalable porque
ofrece la posibilidad de poder combinar y trabajar con una gran cantidad de datos de manera asincrona  y reaccionar a 
variaciones en la carga de trabajo y a cambios en la frecuencia de peticiones incrementando o reduciendo los recursos 
asignados para servir dichas peticiones.
```

* ¿Qué problemas a futuro podría presentar? Si has detectado alguno, ¿qué
alternativas propones para solventar dichos problemas?

```
EL trafico puede generar inconvenientes para su optimo funcionamiento en terminos de latencia y disponibilidad, para esto es 
necesario interactuar con aplicaciones que permiten gestionar y distribuir trafico como APIgateway o balanceadores de carga.
```
