import multiprocessing
import subprocess
import signal
import sys


def run_get_invoices():
    """
    * Función encargada de ejecutar el microservicio 'get_invoices.py'.
    * Ejecuta un script Python en un subproceso y espera su finalización.
    """
    process = subprocess.Popen(["python", "C:\\edemco\\edemco-backend-python\\microservice_get-invoices\\get_invoices.py"])
    try:
        process.wait()
    except KeyboardInterrupt:
        process.terminate()
        process.wait()


def run_obser():
    """
    * Función encargada de ejecutar el microservicio 'cufe.py'.
    * Ejecuta un script Python en un subproceso y espera su finalización.
    """
    process = subprocess.Popen(["python", "C:\\edemco\\edemco-backend-python\\microservice_get-invoices\\cufe.py"])
    try:
        process.wait()
    except KeyboardInterrupt:
        process.terminate()
        process.wait()


def signal_handler(sig, frame):
    """
    * sig: Señal capturada.
    * frame: Contexto del frame donde ocurrió la señal.
    * Manejador de señales para interrumpir la ejecución de los procesos
      de forma controlada cuando se presiona Ctrl+C.
    """
    print("\nEjecución detenida.")
    p1.terminate()
    p2.terminate()
    p1.join()
    p2.join()
    sys.exit(0)


if __name__ == "__main__":
    """
    * Punto de entrada principal.
    * Inicia dos procesos paralelos para ejecutar los microservicios y captura
      señales de interrupción (Ctrl+C) para detenerlos de forma controlada.
    """
    p1 = multiprocessing.Process(target=run_get_invoices)
    p2 = multiprocessing.Process(target=run_obser)

    p1.start()
    p2.start()

    # Capturar la señal de interrupción (Ctrl+C)
    signal.signal(signal.SIGINT, signal_handler)

    try:
        p1.join()
        p2.join()
    except KeyboardInterrupt:
        print("\nEjecución detenida.")
        p1.terminate()
        p2.terminate()
        p1.join()
        p2.join()
        sys.exit(0)
