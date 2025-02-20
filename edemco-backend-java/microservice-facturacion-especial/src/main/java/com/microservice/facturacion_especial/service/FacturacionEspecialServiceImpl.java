package com.microservice.facturacion_especial.service;

import com.microservice.facturacion_especial.dto.FacturacionEspecialDTO;
import com.microservice.facturacion_especial.entities.FacturacionEspecial;
import com.microservice.facturacion_especial.exceptions.FacturacionEspecialException;
import com.microservice.facturacion_especial.persistence.FacturacionEspecialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para manejar la lógica de negocio de Facturación Especial.
 */
@Service
public class FacturacionEspecialServiceImpl implements IFacturacionEspecialService {

    @Autowired
    private FacturacionEspecialRepository facturacionEspecialRepository;

    /**
     * Recupera todas las entidades de facturación especial.
     *
     * @return Lista de entidades {@link FacturacionEspecial}.
     */
    @Override
    public List<FacturacionEspecial> findAll() {
        return (List<FacturacionEspecial>) facturacionEspecialRepository.findAll();
    }

    /**
     * Busca una facturación especial por su identificador.
     *
     * @param id Identificador de la facturación especial.
     * @return La entidad encontrada.
     * @throws RuntimeException Si no se encuentra la entidad.
     */
    @Override
    public FacturacionEspecial findById(Long id) {
        return facturacionEspecialRepository.findById(id).orElseThrow();
    }

    /**
     * Recupera el último valor de exportación asociado a un ID de planta.
     *
     * @param idPlanta Identificador de la planta.
     * @return El último valor de exportación.
     * @throws Exception Si no se encuentra el valor o si ocurre un error.
     */
    @Override
    public Double findLastValorExportacionByIdPlanta(String idPlanta) throws Exception {
        try {
            Optional<Double> valorExportacionOptional = facturacionEspecialRepository.findLastValorExportacionByIdPlanta(idPlanta);
            if (valorExportacionOptional.isPresent()) {
                return valorExportacionOptional.get();
            } else {
                throw new Exception("Valor exportación no encontrado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Guarda una nueva facturación especial en la base de datos.
     *
     * @param facturacionEspecialDTO DTO con los datos necesarios para crear la facturación especial.
     * @return La entidad de facturación especial guardada.
     * @throws FacturacionEspecialException Si los datos proporcionados no son válidos.
     */
    public FacturacionEspecial save(FacturacionEspecialDTO facturacionEspecialDTO) throws FacturacionEspecialException {
        if (facturacionEspecialDTO == null) {
            throw new FacturacionEspecialException("El DTO no puede ser nulo");
        }

        float cantidadkWh = facturacionEspecialDTO.getCantidadkWh();
        float excedente = facturacionEspecialDTO.getExcedente();
        float costoAgregado = facturacionEspecialDTO.getCostoAgregado();
        String idPlanta = facturacionEspecialDTO.getIdPlanta();

        if (excedente <= 0 || costoAgregado <= 0) {
            throw new FacturacionEspecialException("Los valores no pueden ser negativos");
        }

        if (costoAgregado > excedente) {
            throw new FacturacionEspecialException("El Costo Agregado debe ser menor o igual que el Excedente");
        }

        LocalDate currentDate = LocalDate.now();
        Integer mes = currentDate.getMonthValue() == 1 ? 12 : currentDate.getMonthValue() - 1;
        Integer anio = currentDate.getMonthValue() == 1 ? currentDate.getYear() - 1 : currentDate.getYear();

        float valorExportacion = formula(excedente, costoAgregado);

        FacturacionEspecial facturacionEspecial = FacturacionEspecial.builder()
                .cantidadkWh(cantidadkWh)
                .excedente(excedente)
                .costoAgregado(costoAgregado)
                .idPlanta(idPlanta)
                .valorExportacion(valorExportacion)
                .mes(mes)
                .anio(anio)
                .build();

        facturacionEspecialRepository.save(facturacionEspecial);

        return facturacionEspecial;
    }

    /**
     * Recupera la cantidad de kWh exportados por una planta en un año y mes específicos.
     *
     * @param idPlanta Identificador de la planta.
     * @param anio Año de la consulta.
     * @param mes Mes de la consulta.
     * @return La cantidad de kWh exportados.
     * @throws Exception Si ocurre algún error al recuperar los datos.
     */
    @Override
    public Float findCantidadKwhByIdPlantaAndDate(String idPlanta, Integer anio, Integer mes) throws Exception {
        try {
            return facturacionEspecialRepository.findCantidadKwhByIdPlantaAndDate(idPlanta, anio, mes);
        } catch (Exception e) {
            throw new FacturacionEspecialException(e.getMessage());
        }
    }

    /**
     * Calcula el valor de exportación usando la fórmula: excedente - costo agregado.
     *
     * @param excedente Valor del excedente.
     * @param costoAgregado Costo agregado.
     * @return Resultado del cálculo.
     */
    private float formula(float excedente, float costoAgregado) {
        return excedente - costoAgregado;
    }
}
