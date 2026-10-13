# Monitor de Procesamiento Empresarial — NicaData Solutions 🚀
> **Práctica de Concurrencia e Hilos en JavaFX**

Este proyecto es una aplicación desarrollada en **JavaFX** para simular y monitorear el procesamiento concurrente y en segundo plano de registros de **Clientes** y **Productos** para la empresa *NicaData Solutions*.

---

## 📌 Descripción de los Hilos Implementados

Para evitar el bloqueo de la interfaz gráfica (**JavaFX Application Thread**), la lógica de procesamiento pesado se desligó del hilo principal mediante el uso de hilos secundarios independientes (`java.lang.Thread` y `java.lang.Runnable`):

1. **Hilo de Clientes (`hiloClientes`):**
   - **Carga de trabajo:** Procesa **100 registros**.
   - **Tiempo de ejecución:** Simula un retraso de **60 ms** por registro (`Thread.sleep(60)`).
   - **Gestión visual:** Actualiza de forma progresiva la barra `barraClientes` y la etiqueta del porcentaje del `1%` al `100%`.

2. **Hilo de Productos (`hiloProductos`):**
   - **Carga de trabajo:** Procesa **50 registros**.
   - **Tiempo de ejecución:** Simula un retraso de **120 ms** por registro (`Thread.sleep(120)`).
   - **Gestión visual:** Actualiza de forma progresiva la barra `barraProductos` y el porcentaje proporcional (`2%` por cada iteración hasta el `100%`).

### 🔑 Conceptos Clave de Concurrencia Utilizados
* **`Platform.runLater()`:** Permite enviar las actualizaciones visuales desde los hilos secundarios al hilo principal de JavaFX de manera segura sin congelar la aplicación.
* **`Thread.setDaemon(true)`:** Configura los hilos como demonios para garantizar que finalicen automáticamente si la aplicación se cierra inesperadamente.
* **`Thread.interrupt()`:** Implementado para permitir la detención individual y limpia de cada hilo cuando el usuario presiona el botón "Detener" o cierra la ventana.
* **Validación de estado (`isAlive()`):** Previene que un proceso vuelva a ser iniciado si ya se encuentra actualmente en ejecución.

---

## 📷 Capturas del Funcionamiento y Evidencia

> *Nota: Reemplaza las rutas `docs/images/...` por las ubicaciones reales de tus imágenes.*

### 1. Estado Inicial
La aplicación inicia con los componentes listos para la simulación.
![Hilo inicial]<img width="687" height="580" alt="image" src="https://github.com/user-attachments/assets/e6a84d80-ee2f-4701-baf7-968329dacf45" />


### 2. Procesos Concurrentes en Ejecución
Ambos hilos (`Clientes` y `Productos`) avanzan a velocidades distintas de forma simultánea e independiente sin congelar la ventana.
![Hilo en proceso]<img width="683" height="573" alt="image" src="https://github.com/user-attachments/assets/9d19d4a0-ddfd-43b4-b184-5afeaf534734" />


### 3. Detención Individual de Hilo
Demostración de la capacidad de detener el hilo de *Clientes* mientras el hilo de *Productos* continúa su procesamiento normal.
![Hilo Detenido]<img width="687" height="577" alt="image" src="https://github.com/user-attachments/assets/2d02c2b1-0720-46fc-8628-87af62bd9fa0" />


### 4. Procesamiento Completado
Resultado final cuando ambos hilos culminan el 100% de sus registros.
![Procesos Completados]<img width="692" height="591" alt="image" src="https://github.com/user-attachments/assets/ab2f4402-d0aa-4656-9ed5-c11478e19d11" />


---

## ✅ Cumplimiento de Requerimientos (Evidencia del Reto)

| # | Requerimiento | Implementación en Código |
|---|---|---|
| **1** | Objetos `Thread` usando `Runnable` | Se crearon lambdas `Runnable` asignadas a `hiloClientes` e `hiloProductos`. |
| **2** | 100 registros de clientes | Bucle `for` de 1 a 100 en el proceso de clientes. |
| **3** | 50 registros de productos | Bucle `for` de 1 a 50 en el proceso de productos. |
| **4** | Tiempos de ejecución diferentes | Clientes a `60ms` vs. Productos a `120ms` por iteración. |
| **5** | Dos barras de progreso | Implementación de `barraClientes` y `barraProductos` en FXML. |
| **6** | Actualización correcta de la UI | Uso de `Platform.runLater()` en cada iteración de los hilos. |
| **7** | Detener hilos individualmente | Métodos `detenerClientes()` y `detenerProductos()` con `interrupt()`. |
| **8** | Evitar ejecuciones dobles | Control de estados mediante `isAlive()` antes de iniciar cada hilo. |

---

## 🛠️ Requisitos del Sistema
* **Java SDK:** 17 o superior
* **JavaFX:** 17 o superior
* **IDE:** IntelliJ IDEA / Eclipse / NetBeans
* **Gestor de proyectos:** Maven / Gradle
