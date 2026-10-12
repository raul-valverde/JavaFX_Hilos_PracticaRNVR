package ni.edu.uam.practicahilos.practicahilosjavafxrnvr;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProcesadorController {

    // === Componentes GUI para Clientes ===
    @FXML private Label lblEstadoClientes;
    @FXML private Label lblPorcentajeClientes;
    @FXML private ProgressBar barraClientes;
    @FXML private Button btnIniciarClientes;
    @FXML private Button btnDetenerClientes;

    // === Componentes GUI para Productos ===
    @FXML private Label lblEstadoProductos;
    @FXML private Label lblPorcentajeProductos;
    @FXML private ProgressBar barraProductos;
    @FXML private Button btnIniciarProductos;
    @FXML private Button btnDetenerProductos;

    // === Botones Globales ===
    @FXML private Button btnSimularTodo;
    @FXML private Button btnReiniciar;

    // === NUEVO: Dos variables Thread independientes para manejar la concurrencia ===
    private Thread hiloClientes;
    private Thread hiloProductos;

    // =========================================================================
    // 1. PROCESO DE CLIENTES (100 Registros)
    // =========================================================================
    @FXML
    private void iniciarClientes() {
        // REQUERIMIENTO 8: Evitar iniciar si el hilo de clientes ya está vivo
        if (hiloClientes != null && hiloClientes.isAlive()) {
            return;
        }

        // Estado inicial de la UI para Clientes
        barraClientes.setProgress(0);
        lblPorcentajeClientes.setText("0%");
        lblEstadoClientes.setText("Procesando...");
        btnIniciarClientes.setDisable(true);
        btnDetenerClientes.setDisable(false);

        // REQUERIMIENTO 1: Crear Runnable para el proceso de Clientes
        Runnable tareaClientes = () -> {
            int totalRegistros = 100; // REQUERIMIENTO 2: 100 registros
            int tiempoEsperaMs = 60;  // REQUERIMIENTO 4: Tiempo de ejecución #1

            try {
                for (int i = 1; i <= totalRegistros; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }

                    Thread.sleep(tiempoEsperaMs);

                    final int avance = i;
                    final double progreso = (double) avance / totalRegistros;

                    // REQUERIMIENTO 6: Actualizar JavaFX Application Thread de forma segura
                    Platform.runLater(() -> {
                        barraClientes.setProgress(progreso);
                        lblPorcentajeClientes.setText(avance + "%");
                    });
                }

                Platform.runLater(() -> {
                    lblEstadoClientes.setText("Completado");
                    btnIniciarClientes.setDisable(false);
                    btnDetenerClientes.setDisable(true);
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Platform.runLater(() -> {
                    lblEstadoClientes.setText("Detenido");
                    btnIniciarClientes.setDisable(false);
                    btnDetenerClientes.setDisable(true);
                });
            }
        };

        // REQUERIMIENTO 1: Instanciación del Thread
        hiloClientes = new Thread(tareaClientes, "Hilo-Clientes");
        hiloClientes.setDaemon(true); // Se cierra automáticamente al salir de la app
        hiloClientes.start();
    }

    // REQUERIMIENTO 7: Permitir detener el hilo de Clientes individualmente
    @FXML
    private void detenerClientes() {
        if (hiloClientes != null && hiloClientes.isAlive()) {
            btnDetenerClientes.setDisable(true);
            lblEstadoClientes.setText("Deteniendo...");
            hiloClientes.interrupt(); // Cancela la ejecución del hilo
        }
    }

    // =========================================================================
    // 2. PROCESO DE PRODUCTOS (50 Registros)
    // =========================================================================
    @FXML
    private void iniciarProductos() {
        // REQUERIMIENTO 8: Evitar iniciar si el hilo de productos ya está vivo
        if (hiloProductos != null && hiloProductos.isAlive()) {
            return;
        }

        // Estado inicial de la UI para Productos
        barraProductos.setProgress(0);
        lblPorcentajeProductos.setText("0%");
        lblEstadoProductos.setText("Procesando...");
        btnIniciarProductos.setDisable(true);
        btnDetenerProductos.setDisable(false);

        // REQUERIMIENTO 1: Crear Runnable para el proceso de Productos
        Runnable tareaProductos = () -> {
            int totalRegistros = 50;  // REQUERIMIENTO 3: 50 registros
            int tiempoEsperaMs = 120; // REQUERIMIENTO 4: Tiempo de ejecución #2 (diferente al de Clientes)

            try {
                for (int i = 1; i <= totalRegistros; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }

                    Thread.sleep(tiempoEsperaMs);

                    final int avance = i;
                    final double progreso = (double) avance / totalRegistros;
                    final int porcentaje = (int) (progreso * 100);

                    // REQUERIMIENTO 6: Actualización segura de la UI
                    Platform.runLater(() -> {
                        barraProductos.setProgress(progreso);
                        lblPorcentajeProductos.setText(porcentaje + "%");
                    });
                }

                Platform.runLater(() -> {
                    lblEstadoProductos.setText("Completado");
                    btnIniciarProductos.setDisable(false);
                    btnDetenerProductos.setDisable(true);
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Platform.runLater(() -> {
                    lblEstadoProductos.setText("Detenido");
                    btnIniciarProductos.setDisable(false);
                    btnDetenerProductos.setDisable(true);
                });
            }
        };

        // REQUERIMIENTO 1: Instanciación del segundo Thread
        hiloProductos = new Thread(tareaProductos, "Hilo-Productos");
        hiloProductos.setDaemon(true);
        hiloProductos.start();
    }

    // REQUERIMIENTO 7: Permitir detener el hilo de Productos individualmente
    @FXML
    private void detenerProductos() {
        if (hiloProductos != null && hiloProductos.isAlive()) {
            btnDetenerProductos.setDisable(true);
            lblEstadoProductos.setText("Deteniendo...");
            hiloProductos.interrupt();
        }
    }

    // =========================================================================
    // 3. CONTROLES GLOBALES Y LIMPIEZA
    // =========================================================================

    // Inicia la simulación de ambos hilos al mismo tiempo
    @FXML
    private void simularTodo() {
        iniciarClientes();
        iniciarProductos();
    }

    // Detiene hilos activos y resetea las dos barras de progreso
    @FXML
    private void reiniciarTodo() {
        detenerClientes();
        detenerProductos();

        barraClientes.setProgress(0);
        lblPorcentajeClientes.setText("0%");
        lblEstadoClientes.setText("En espera");
        btnIniciarClientes.setDisable(false);
        btnDetenerClientes.setDisable(true);

        barraProductos.setProgress(0);
        lblPorcentajeProductos.setText("0%");
        lblEstadoProductos.setText("En espera");
        btnIniciarProductos.setDisable(false);
        btnDetenerProductos.setDisable(true);
    }

    // NUEVO: Método para detener ambos hilos si el usuario cierra la ventana de la app
    public void detenerAlCerrar() {
        if (hiloClientes != null && hiloClientes.isAlive()) {
            hiloClientes.interrupt();
        }
        if (hiloProductos != null && hiloProductos.isAlive()) {
            hiloProductos.interrupt();
        }
    }
}