package com.IntegracionSiesa.Service;

import java.io.*;

/**
 * Servicio para manejar un contador persistente, utilizando un archivo para mantener el estado del contador entre ejecuciones.
 */
public class NumDocumentoService {

    private static final String COUNTER_FILE = "execution_counter.txt"; // Nombre del archivo para almacenar el contador
    private int counter;

    /**
     * Constructor que inicializa el contador leyendo el valor desde el archivo.
     * Si el archivo no existe o tiene un formato inválido, el contador comienza desde cero.
     */
    public NumDocumentoService() {
        this.counter = readCounterFromFile();
    }

    /**
     * Incrementa el contador en 1 y guarda el nuevo valor en el archivo.
     */
    public void incrementCounter() {
        counter++;
        writeCounterToFile();
    }

    /**
     * Lee el valor del contador desde el archivo.
     * Si el archivo no existe o tiene un formato inválido, retorna 0.
     *
     * @return Valor actual del contador leído desde el archivo o 0 si ocurre algún error.
     */
    private int readCounterFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(COUNTER_FILE))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            // Si hay algún error, asumimos que es la primera ejecución
            return 0;
        }
    }

    /**
     * Escribe el valor actual del contador en el archivo.
     * Si ocurre un error durante la escritura, se imprime un mensaje de error.
     */
    private void writeCounterToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COUNTER_FILE))) {
            writer.write(String.valueOf(counter));
        } catch (IOException e) {
            System.err.println("Error al escribir el contador en el archivo: " + e.getMessage());
        }
    }

    /**
     * Obtiene el valor actual del contador.
     *
     * @return Valor actual del contador.
     */
    public int getCounter() {
        return counter;
    }
}
