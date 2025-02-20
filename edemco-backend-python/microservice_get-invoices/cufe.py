import os
import xml.etree.ElementTree as ET
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler
from sqlalchemy import create_engine, text
from sqlalchemy.orm import sessionmaker
import subprocess
import time
import logging
from pathlib import Path

# Configurar el registro con codificación UTF-8 y modo sobrescritura
logger = logging.getLogger('registro_facturas')
logger.setLevel(logging.INFO)
file_handler = logging.FileHandler('microservice_get-invoices/registro_facturas.log', mode='a', encoding='utf-8')
formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s', datefmt='%Y-%m-%d %H:%M:%S')
file_handler.setFormatter(formatter)
logger.addHandler(file_handler)

class XMLHandler(FileSystemEventHandler):
    def __init__(self, session):
        """
        * session: Objeto de sesión SQLAlchemy para interactuar con la base de datos.
        * Inicializa el manejador para procesar eventos en el sistema de archivos.
        """
        self.session = session
        self.counter = 0  # Contador de facturas procesadas

    def process_xml(self, file_path):
        """
        * file_path: Ruta completa del archivo XML a procesar.
        * Procesa un archivo XML, verificando si la factura existe en la base de datos
          y actualiza los campos necesarios.
        """
        try:
            invoice_number = os.path.basename(file_path).replace(".xml", "")

            exists_query = text("""
                SELECT COUNT(*) FROM factura WHERE numero_factura = :invoice_number
            """)
            result = self.session.execute(exists_query, {'invoice_number': invoice_number}).scalar()

            if result > 0:
                tree = ET.parse(file_path)
                root = tree.getroot()

                namespaces = {
                    'cbc': 'urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2',
                }

                cufe = None
                fecha_dian = None
                fecha_pago = None
                concepto_facturado = None
                note_counter = 0

                for element in root.findall("cbc:UUID", namespaces):
                    cufe = element.text
                    break

                for element in root.findall("cbc:IssueDate", namespaces):
                    fecha_dian = element.text
                    break

                for element in root.findall("cbc:DueDate", namespaces):
                    fecha_pago = element.text
                    break

                for element in root.findall("cbc:Note", namespaces):
                    concepto_facturado = element.text
                    note_counter += 1
                    if note_counter == 1:
                        break

                if cufe and fecha_dian and fecha_pago and concepto_facturado:
                    self.update_database(invoice_number, cufe, fecha_dian, fecha_pago, concepto_facturado)
                else:
                    logger.info(f"Campos requeridos no encontrados en el archivo: {file_path}")
            else:
                logger.info(f"Factura {invoice_number} no encontrada en la base de datos.")

        except PermissionError as e:
            logger.error(f"PermissionError al intentar abrir el archivo: {e}")
        except Exception as e:
            logger.error(f"Error procesando el archivo XML: {e}")

    def update_database(self, invoice_number, cufe, fecha_dian, fecha_pago, concepto_facturado):
        """
        * invoice_number: Número de la factura.
        * cufe: CUFE de la factura.
        * fecha_dian: Fecha de emisión de la factura en la DIAN.
        * fecha_pago: Fecha de vencimiento de la factura.
        * concepto_facturado: Concepto facturado según el XML.
        * Actualiza la base de datos con la información de la factura procesada.
        """
        try:
            query = text("""
                UPDATE factura
                SET cufe = :cufe, fecha_dian = :fecha_dian, fecha_pago = :fecha_pago, concepto_facturado = :concepto_facturado
                WHERE numero_factura = :invoice_number
            """)
            self.session.execute(query, {
                'cufe': cufe,
                'fecha_dian': fecha_dian,
                'fecha_pago': fecha_pago,
                'concepto_facturado': concepto_facturado,
                'invoice_number': invoice_number
            })
            self.session.commit()

            self.counter += 1
            logger.info(f"Factura {invoice_number} actualizada con CUFE: {cufe}, fecha_dian: {fecha_dian}, fecha_pago: {fecha_pago}, concepto_facturado: {concepto_facturado}")

            if self.counter >= 1:
                time.sleep(1)
                self.clear_console()
                self.counter = 0

        except Exception as err:
            logger.error(f"Error: {err}")

    def clear_console(self):
        """
        * Limpia la consola para una mejor visualización en sistemas interactivos.
        """
        subprocess.run("cls" if os.name == "nt" else "clear", shell=True)

    def on_created(self, event):
        """
        * event: Evento del sistema de archivos capturado.
        * Verifica si un nuevo archivo XML relevante ha sido creado y lo procesa.
        """
        try:
            if event.is_directory:
                return
            filename = os.path.basename(event.src_path)
        
            if filename.startswith("13001fes") or filename.startswith("13001FES"):
                if filename.endswith(".xml"):
                    self.process_xml(event.src_path)
        except:
            print(f'No se encontró la factura: {filename}')

def main():
    """
    * Punto de entrada principal del microservicio.
    * Configura la base de datos, inicia el observador y procesa eventos del sistema de archivos.
    """
    DATABASE_URL = "mssql+pyodbc://temptech:Edemco2024*+@10.255.252.2/edemco?driver=ODBC+Driver+17+for+SQL+Server"

    engine = create_engine(DATABASE_URL)
    SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
    session = SessionLocal()

    # Ruta fija a vigilar
    path_to_watch = Path("Z:")
    if path_to_watch.exists() and path_to_watch.is_dir():
        event_handler = XMLHandler(session)
        observer = Observer()
        observer.schedule(event_handler, path=str(path_to_watch), recursive=False)
        observer.start()

        logger.info(f"Observador iniciado, vigilando el directorio: {path_to_watch}")

        try:
            while True:
                time.sleep(1)
        except KeyboardInterrupt:
            observer.stop()
            logger.info("Observador detenido.")
        observer.join()
    else:
        logger.error(f"No se encontró la ruta: {path_to_watch}")

if __name__ == "__main__":
    main()
