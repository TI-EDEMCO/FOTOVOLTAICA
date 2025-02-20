package com.IntegracionSiesa.Service;

import com.IntegracionSiesa.Entities.DocumentoVentaServicio;
import com.IntegracionSiesa.Entities.MovimientoVentaServicio;
import com.IntegracionSiesa.Entities.VentasServicios;
import com.IntegracionSiesa.client.FacturaClient;
import com.IntegracionSiesa.client.FacturacionEspecialClient;
import com.IntegracionSiesa.client.GeneracionClient;
import com.IntegracionSiesa.dto.FacturaFADto;
import com.IntegracionSiesa.dto.FacturaRequestDTO;
import com.IntegracionSiesa.dto.IniciarFacturacionDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SiesaService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders httpHeaders;

    @Autowired
    private PlantaServiceImpl plantaService;

    @Autowired
    private ClienteServiceImpl clienteService;

    @Autowired
    private GeneracionClient generacionClient;

    @Autowired
    private FacturaClient facturaClient;

    @Autowired
    private FacturacionEspecialClient facturacionEspecialClient;

    private final String baseUrl = "http://integrador.edemco.co:8089/v3/";

    public ResponseEntity<String> getIntDatosFacturaClienteCen(){
        String url = baseUrl + "EjecutarConsulta?idCompania=3306&descripcion=IntDatosFacturaClienteCen";
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
    }

    public ResponseEntity<String> getIntDatosFacturaDocumentoCen(){
        String url = baseUrl + "EjecutarConsulta?idCompania=3306&descripcion=IntDatosFacturaDocumentoCen";
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
    }

    public ResponseEntity<String> getIntDatosFacturaMvtoCen(){
        String url = baseUrl + "EjecutarConsulta?idCompania=3306&descripcion=IntDatosFacturaMvtoCen";
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
    }

    public ResponseEntity<String> getInformacionFACen(){
        String url = baseUrl + "EjecutarConsulta?idCompania=3306&descripcion=InformaFacCen";
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
    }

    public ResponseEntity<String> postConectoresImportar(VentasServicios ventasServicios) {

        // Convertir el ObjectNode a JSON

        String jsonBody;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonBody = objectMapper.writeValueAsString(ventasServicios);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al convertir a JSON");
        }

        // Configurar la URL y las cabeceras
        String url = baseUrl + "conectoresimportar?idCompania=3306&idInterface=4212&idDocumento=183015&nombreDocumento=VENTAS_SERV_INT";
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // Crear la entidad HTTP
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, httpHeaders);

        // Realizar la solicitud POST
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    public ResponseEntity<List<String>> getNitsByCentroOperacion(List<IniciarFacturacionDto> centroOperacionList){
        List<String> nitClienteList = new ArrayList<>();
        ResponseEntity<String> responseEntityNits = getIntDatosFacturaClienteCen();
        String responseBodyNits = responseEntityNits.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        for (IniciarFacturacionDto centroOperacion : centroOperacionList){
            try {
                JsonNode jsonNode = objectMapper.readTree(responseBodyNits);
                JsonNode detalleNode = jsonNode.get("detalle");

                if (detalleNode != null && !detalleNode.isNull()){
                    JsonNode tableNode = detalleNode.get("Table");
                    if (tableNode != null && !tableNode.isNull()){
                        for (int i = 0; i < tableNode.size(); i++) {
                            String centroOperacion1 = tableNode.get(i).get("f284_id_co").asText();
                            if (centroOperacion.getIdPlanta().equals(centroOperacion1)){
                                // Obtener el valor de "NitCliente" del elemento actual
                                String nitCliente = tableNode.get(i).get("f200_nit").asText();

                                // Agregar el valor de NitCliente a la lista
                                nitClienteList.add(nitCliente);
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(nitClienteList);
    }

    public ResponseEntity<List<VentasServicios>> llenarDatos2(List<IniciarFacturacionDto> centroOperacionList, LocalDate date) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> nitList = getNitsByCentroOperacion(centroOperacionList).getBody();
        int c = 0;
        Integer numeroDocumento = 1;
        List<VentasServicios> ventasServiciosList = new ArrayList<>();

        System.out.println("Iniciando método llenarDatos2");

        for (IniciarFacturacionDto centroOperacion : centroOperacionList) {
            System.out.println("Procesando centro de operación: " + centroOperacion.getIdPlanta());

            List<MovimientoVentaServicio> movimientoVentaServicioList = new ArrayList<>();
            DocumentoVentaServicio documentoVentaServicio = new DocumentoVentaServicio();

            // Verificación: Validar que 'c' no excede el tamaño de 'nitList'
            if (c >= nitList.size()) {
                System.out.println("Error: índice 'c' fuera de los límites de 'nitList'. No hay suficientes elementos en la lista para procesar.");
                continue; // Saltar este centro de operación si no hay un NIT correspondiente
            }

            String nitCliente = nitList.get(c);
            System.out.println("NIT Cliente: " + nitCliente);

            // Datos adicionales
            ResponseEntity<String> responseEntityDatos = getIntDatosFacturaDocumentoCen();
            String responseBody = responseEntityDatos.getBody();
            System.out.println("Datos adicionales recibidos: " + responseBody);

            String fecha_sin_guiones = date.toString().replace("-", "");
            String fechaInicialObser = date.minusMonths(1).withDayOfMonth(1).toString();
            String fechaFinalObser = YearMonth.from(date.minusMonths(1)).atEndOfMonth().toString();
            Integer anio = date.minusMonths(1).getYear();
            Integer mes = date.minusMonths(1).getMonthValue();
            String idPlanta = centroOperacion.getIdPlanta();

            System.out.println("Fecha sin guiones: " + fecha_sin_guiones);

            Double generacionActual = findGeneracionActualByIdPlantaAndDate(idPlanta, anio, mes);
            Double valorUnidad = plantaService.findValorUnidadByIdPlanta(idPlanta);
            String nombrePlanta = centroOperacion.getNombrePlanta();
            String observaciones = String.format("Facturación de Energía Fotovoltaica Planta %s consumo de %.1f kWh con valor por unidad de unidad de $%.1f, correspondiente al periodo %s - %s.",
                    nombrePlanta, generacionActual, valorUnidad, fechaInicialObser, fechaFinalObser);

            System.out.println("Observaciones: " + observaciones);

            try {
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                JsonNode detalleNode = jsonNode.get("detalle");

                if (detalleNode != null && !detalleNode.isNull()) {
                    JsonNode tableNode = detalleNode.get("Table");
                    if (tableNode != null && !tableNode.isNull()) {
                        for (int i = 0; i < tableNode.size(); i++) {
                            String nitCliente1 = tableNode.get(i).get("f200_nit").asText();
                            if (nitCliente1.equals(nitCliente)) {
                                System.out.println("Encontrado cliente con NIT: " + nitCliente1);
                                String numeroSucursal = tableNode.get(i).get("f201_id_sucursal").asText();
                                String tipeDeCliente = tableNode.get(i).get("f201_id_tipo_cli").asText();
                                String condicionPago = tableNode.get(i).get("f201_id_cond_pago").asText();
                                String nit = tableNode.get(i).get("f200_nit").asText();

                                documentoVentaServicio = DocumentoVentaServicio.builder()
                                        .F350_ID_CO("001")
                                        .F350_ID_TIPO_DOCTO("FES")
                                        .F350_CONSEC_DOCTO(numeroDocumento.toString())
                                        .F350_FECHA(fecha_sin_guiones)
                                        .F350_ID_TERCERO(nit)
                                        .F350_NOTAS(observaciones)
                                        .F311_ID_SUCURSAL_CLI(numeroSucursal)
                                        .F311_ID_TIPO_CLI(tipeDeCliente)
                                        .F311_ID_COND_PAGO(condicionPago).build();
                            }
                        }
                    } else {
                        System.out.println("Table node es null o está vacío.");
                    }
                } else {
                    System.out.println("Detalle node es null o está vacío.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            ResponseEntity<String> responseEntityDatosFactura = getIntDatosFacturaClienteCen();
            String responseBodyFactura = responseEntityDatosFactura.getBody();

            System.out.println("Datos de factura recibidos: " + responseBodyFactura);

            try {
                JsonNode jsonNode = objectMapper.readTree(responseBodyFactura);
                JsonNode detalleNode = jsonNode.get("detalle");

                if (detalleNode != null && !detalleNode.isNull()) {
                    JsonNode tableNode = detalleNode.get("Table");
                    if (tableNode != null && !tableNode.isNull()) {
                        for (int i = 0; i < tableNode.size(); i++) {
                            String centroOperacion1 = tableNode.get(i).get("f284_id_co").asText();
                            if (centroOperacion1.equals(centroOperacion.getIdPlanta())) {
                                System.out.println("Procesando datos de factura para centro de operación: " + centroOperacion1);

                                for (Integer j = 1; j <= 2; j++) {
                                    Double valorBruto;

                                    if (j == 2) {
                                        valorBruto = (Double) findLastValorExportacionByIdPlanta(idPlanta).getBody();
                                        System.out.println("Valor Bruto Exportación (j=2) para la planta " + idPlanta + ": " + valorBruto);
                                    } else {
                                        valorBruto = findValorTotalByIdPlantaAndDate(idPlanta, anio, mes);
                                        System.out.println("Valor Total (j=1) para la planta " + idPlanta + ": " + valorBruto);
                                    }

                                    String codigoUnidadNegocio = tableNode.get(i).get("f284_id_un").asText();
                                    String codigoCentroCostos = tableNode.get(i).get("f284_id").asText();
                                    String sucursal = tableNode.get(i).get("f201_id_sucursal").asText();

                                    // Formatear valorBruto solo si es un número válido
                                    DecimalFormat df = new DecimalFormat("#");
                                    String valorBrutoString;
                                    try {
                                        valorBrutoString = df.format(valorBruto);
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("Error al formatear valorBruto: " + valorBruto);
                                        valorBrutoString = "0"; // Fallback si hay un error
                                    }


                                    movimientoVentaServicioList.add(MovimientoVentaServicio.builder()
                                            .F350_ID_CO("001")
                                            .F350_ID_TIPO_DOCTO("FES")
                                            .F350_CONSEC_DOCTO(numeroDocumento.toString())
                                            .F320_ROWID(j.toString())
                                            .F320_ID_SERVICIO("VS909002")
                                            .F320_ID_CO_MOVTO(centroOperacion.getIdPlanta())
                                            .F320_ID_UN_MOVTO(codigoUnidadNegocio)
                                            .F320_ID_CCOSTO_MOVTO(codigoCentroCostos)
                                            .F320_ID_TERCERO_MOVTO(nitList.get(c))
                                            .F320_ID_SUCURSAL_CLIENTE(sucursal)
                                            .F320_CANTIDAD("1")
                                            .F320_VLR_BRUTO(valorBrutoString) // cambiar a variable valorBrutoString para el funcionamiento real de la aplicacion
                                            .F320_NOTAS(observaciones).build());

                                    if (clienteService.findEspecialCustomerByIdPlanta(centroOperacion1).isEmpty()) {
                                        System.out.println("Cliente especial no encontrado, saliendo del bucle.");
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("Table node en datos de factura es null o está vacío.");
                    }
                } else {
                    System.out.println("Detalle node en datos de factura es null o está vacío.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            VentasServicios ventasServicios = VentasServicios.builder()
                    .doctoVentasServicios(Arrays.asList(documentoVentaServicio))
                    .movtoVentasServicios(movimientoVentaServicioList)
                    .build();
            ventasServiciosList.add(ventasServicios);
            c++;
            numeroDocumento++;
        }

        System.out.println("Finalizando método llenarDatos2");

        return ResponseEntity.ok(ventasServiciosList);
    }

    public ResponseEntity<List<FacturaFADto>> getNumberFactura() {
        System.out.println("Método getNumberFactura() iniciado.");
        ResponseEntity<String> responseEntityNits = getInformacionFACen();
        String responseBodyNits = responseEntityNits.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        List<FacturaFADto> facturasInfo = new ArrayList<>();

        try {
            // Log de respuesta JSON
            System.out.println("Respuesta JSON: " + responseBodyNits);

            JsonNode rootNode = objectMapper.readTree(responseBodyNits);
            JsonNode detalleNode = rootNode.get("detalle");

            if (detalleNode == null || detalleNode.isNull()) {
                throw new Exception("El nodo 'detalle' no está presente o es nulo en el JSON.");
            }

            JsonNode tableNode = detalleNode.get("Table");
            if (tableNode == null || !tableNode.isArray()) {
                throw new Exception("El nodo 'Table' no está presente o no es un array en el JSON.");
            }


            for (JsonNode facturaNode : tableNode) {

                FacturaFADto facturaFADto = new FacturaFADto();
                facturaFADto.setF350_id_cia(facturaNode.get("f350_id_cia").asInt());
                facturaFADto.setF350_id_co(facturaNode.get("f350_id_co").asText());
                facturaFADto.setF350_id_tipo_docto(facturaNode.get("f350_id_tipo_docto").asText());
                facturaFADto.setF350_consec_docto(facturaNode.get("f350_consec_docto").asInt());
                facturaFADto.setF284_id(facturaNode.get("f284_id").asText().trim());
                String fecha = facturaNode.get("f350_fecha_ts_creacion").asText();
                LocalDateTime fechaHora = LocalDateTime.parse(fecha, DateTimeFormatter.ISO_DATE_TIME);
                facturaFADto.setF350_fecha_ts_creacion(fechaHora);

                // Log de ID Planta y Número de Factura
                System.out.println("Factura procesada: ID Planta = " + facturaFADto.getF284_id()
                        + ", Número de Factura = " + facturaFADto.getF350_id_cia() + facturaFADto.getF350_id_co()
                        + facturaFADto.getF350_id_tipo_docto() + facturaFADto.getF350_consec_docto());

                facturasInfo.add(facturaFADto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }

        return ResponseEntity.ok(facturasInfo);
    }


    public Double findGeneracionActualByIdPlantaAndDate(String idPlanta, Integer anio, Integer mes) {
        try {
            Double generacionActual = generacionClient.findGeneracionActualByIdPlantaAndDate(idPlanta, anio, mes);
            if (generacionActual == null) {
                System.out.println("No se encontraron datos de generación actual para la planta " + idPlanta + " en el año " + anio + " y mes " + mes + ". Asignando valor predeterminado de 0.0.");
                generacionActual = 0.0; // Asignar un valor predeterminado si no hay datos
            }
            return generacionActual;
        } catch (Exception e) {
            System.out.println("Error al obtener la generación actual para la planta " + idPlanta + ": " + e.getMessage());
            return 0.0; // En caso de excepción, devolver un valor predeterminado
        }
    }

    public Double findValorTotalByIdPlantaAndDate(String idPlanta, Integer anio, Integer mes) {
        try {
            Double valorTotal = generacionClient.findValorTotalByIdPlantaAndDate(idPlanta, anio, mes);
            if (valorTotal == null) {
                System.out.println("No se encontraron datos de valor total para la planta " + idPlanta + " en el año " + anio + " y mes " + mes + ". Asignando valor predeterminado de 0.0.");
                valorTotal = 0.0; // Asignar un valor predeterminado si no hay datos
            }
            return valorTotal;
        } catch (Exception e) {
            System.out.println("Error al obtener el valor total para la planta " + idPlanta + ": " + e.getMessage());
            return 0.0; // En caso de excepción, devolver un valor predeterminado
        }
    }

    public ResponseEntity<?> findLastValorExportacionByIdPlanta(String idPlanta){
        return facturacionEspecialClient.findLastValorExportacionByIdPlanta(idPlanta);
    }

    public ResponseEntity<?> addFactura(FacturaRequestDTO facturaRequestDTO){
        return facturaClient.addFactura(facturaRequestDTO);
    }

    private int obtenerConsecutivoFactura(String idPlanta, List<FacturaFADto> facturasFADto) {
        // Buscar en el JSON el consecutivo basado en el ID de la planta
        return facturasFADto.stream()
                .filter(f -> f.getF284_id().trim().equals(idPlanta))
                .map(FacturaFADto::getF350_consec_docto)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró un consecutivo para la planta: " + idPlanta));
    }

    private String generarNumeroFactura(String idPlanta, List<FacturaFADto> facturasFADto) {
        // Buscar en el JSON los datos necesarios
        FacturaFADto facturaFADto = facturasFADto.stream()
                .filter(f -> f.getF284_id().trim().equals(idPlanta))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró información de factura para la planta: " + idPlanta));

        // Extraer los valores desde el FacturaFADto
        int idCompania = facturaFADto.getF350_id_cia();
        String tipoDocumento = facturaFADto.getF350_id_tipo_docto();
        String idCo = facturaFADto.getF350_id_co();
        int consecutivo = facturaFADto.getF350_consec_docto();

        // Construir el número de factura
        return String.format("%05d%s%s%04d", idCompania, idCo, tipoDocumento, consecutivo);
    }

    public ResponseEntity<?> envioFacturas(List<IniciarFacturacionDto> centroOperacionList, LocalDate date) throws Exception {
        try {
            Map<String, String> mapeoPlantas = crearMapeoPlantas();

            List<VentasServicios> ventasServiciosList = llenarDatos2(centroOperacionList, date).getBody();

            for (VentasServicios ventasServicios : Objects.requireNonNull(ventasServiciosList)) {
                String idPlanta = ventasServicios.getMovtoVentasServicios().get(0).getF320_ID_CO_MOVTO().trim();
                String f284_id = mapeoPlantas.getOrDefault(idPlanta, idPlanta).trim();
                LocalDate fechaInicialLocal = date.minusMonths(1).withDayOfMonth(1);
                Integer nitCliente = Integer.valueOf(ventasServicios.getDoctoVentasServicios().get(0).getF350_ID_TERCERO());
                Long idCliente = clienteService.findIdClienteByNit(nitCliente);
                LocalDate fechaFinalLocal = YearMonth.from(date.minusMonths(1)).atEndOfMonth();
                Long diasEntreFechas = ChronoUnit.DAYS.between(fechaInicialLocal, fechaFinalLocal) + 1;

                // Crear el DTO de la factura con el número generado
                FacturaRequestDTO facturaRequestDTO = FacturaRequestDTO.builder()
                        .diasFacturados(Math.toIntExact(diasEntreFechas))
                        .fechaFinal(fechaFinalLocal)
                        .fechaInicial(fechaInicialLocal)
                        .idPlanta(idPlanta)
                        .idCliente(idCliente)
                        .build();

                System.out.println("Enviando datos a postConectoresImportar...");
                ResponseEntity<String> responseImportar = postConectoresImportar(ventasServicios);
                System.out.println("Respuesta de postConectoresImportar: " + responseImportar.getBody());
                ResponseEntity<List<FacturaFADto>> responseEntityFacturas = getNumberFactura();
                List<FacturaFADto> facturasFADto = responseEntityFacturas.getBody();


                if (facturasFADto == null || facturasFADto.isEmpty()) {
                    throw new Exception("No se encontraron números de factura en el JSON proporcionado.");
                }

                // Filtrar las facturas para la planta específica
                List<FacturaFADto> facturasParaPlanta = facturasFADto.stream()
                        .filter(f -> f.getF284_id() != null && f.getF284_id().trim().equals(f284_id))
                        .sorted(Comparator.comparing(FacturaFADto::getF350_fecha_ts_creacion))
                        .toList();

                if (facturasParaPlanta.isEmpty()) {
                    System.out.println("No se encontró un número de factura para la planta: " + idPlanta);
                    continue; // Pasar a la siguiente planta
                }

                // Procesar solo la primera factura disponible
                FacturaFADto facturaSeleccionada = facturasParaPlanta.get(facturasParaPlanta.size()-1);
                facturasFADto.remove(facturaSeleccionada);

                // Generar el número de factura
                String numeroFactura = String.format("%d%s%s%04d",
                        facturaSeleccionada.getF350_id_cia(),
                        facturaSeleccionada.getF350_id_co(),
                        facturaSeleccionada.getF350_id_tipo_docto(),
                        facturaSeleccionada.getF350_consec_docto());

                System.out.println("Número de factura generado: " + numeroFactura);

                facturaRequestDTO.setNumeroFactura(numeroFactura);

                System.out.println("Guardando factura en la base de datos...");
                ResponseEntity<?> responseAddFactura = addFactura(facturaRequestDTO);
                System.out.println("Respuesta de addFactura: " + responseAddFactura.getStatusCode());
            }

            return ResponseEntity.ok("Facturas enviadas correctamente");
        } catch (Exception e) {
            throw new Exception("Error en envioFacturas: " + e.getMessage(), e);
        }
    }

    private Map<String, String> crearMapeoPlantas() {
        Map<String, String> mapeo = new HashMap<>();
        mapeo.put("505", "PUNTOCLAVESSFV");
        mapeo.put("506", "CEIPABARRANQUIL");
        mapeo.put("507", "CEIPASABANETASS");
        mapeo.put("508", "LICEOFRANCESSFV");
        mapeo.put("511", "LEMONT1PORTERIA");
        mapeo.put("512", "LEMONT1SALONSOC");
        mapeo.put("513", "POLLOCOASSFV");
        mapeo.put("514", "INCUBANSSFV");
        return mapeo;
    }

}
