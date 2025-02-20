package com.IntegracionSiesa.Service;

import com.IntegracionSiesa.Entities.Planta;
import com.IntegracionSiesa.Repository.PlantaRepository;
import com.IntegracionSiesa.dto.PlantaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para gestionar operaciones relacionadas con plantas.
 */
@Service
public class PlantaServiceImpl implements IPlantaService {

    @Autowired
    private PlantaRepository plantaRepository;

    /**
     * Obtiene una lista de todas las plantas registradas.
     *
     * @return Optional que contiene una lista de PlantaDto con los detalles de las plantas.
     */
    @Override
    public Optional<List<PlantaDto>> findAllPlantas() {
        return Optional.ofNullable(plantaRepository.findAllPlantas());
    }

    /**
     * Obtiene el valor de la unidad asociado a una planta específica.
     *
     * @param idPlanta Identificador de la planta.
     * @return Valor de la unidad como un Double.
     */
    @Override
    public Double findValorUnidadByIdPlanta(String idPlanta) {
        return plantaRepository.findValorUnidadByIdPlanta(idPlanta);
    }

    /**
     * Obtiene el nombre de una planta específica a partir de su identificador.
     *
     * @param idPlanta Identificador de la planta.
     * @return Nombre de la planta como un String.
     */
    @Override
    public String findNombrePlantaByIdPlanta(String idPlanta) {
        return plantaRepository.findNombrePlantaByIdPlanta(idPlanta);
    }

    /**
     * Obtiene el identificador de una planta a partir de su nombre.
     *
     * @param nombrePlanta Nombre de la planta.
     * @return Identificador de la planta como un String.
     */
    @Override
    public String findIdPlantaByNombrePlanta(String nombrePlanta) {
        return plantaRepository.findIdPlantaByNombrePlanta(nombrePlanta);
    }

    /**
     * Obtiene el identificador del operador asociado a una planta específica.
     *
     * @param idPlanta Identificador de la planta.
     * @return Identificador del operador como un Long.
     */
    @Override
    public Long findIdOperadorByIdPlanta(String idPlanta) {
        return plantaRepository.findIdOperadorByIdPlanta(idPlanta);
    }

    /**
     * Modifica los detalles de una lista de plantas según los datos proporcionados.
     * Los campos que se pueden modificar son: imagen, asunto, porcentaje de aumento y valor de unidad.
     *
     * @param plantaDtoList Lista de objetos PlantaDto con los nuevos datos.
     * @return Lista actualizada de PlantaDto.
     */
    @Override
    public List<PlantaDto> modifyPlanta(List<PlantaDto> plantaDtoList) {
        List<PlantaDto> plantaDtoList1 = new ArrayList<>();
        for (PlantaDto plantaDto : plantaDtoList) {
            Optional<Planta> planta = plantaRepository.findById(plantaDto.getIdPlanta());
            if (planta.isPresent()) {
                if (plantaDto.getUrlImg() != null) {
                    planta.get().setUrlImg(plantaDto.getUrlImg());
                }
                if (plantaDto.getAsunto() != null) {
                    planta.get().setAsunto(plantaDto.getAsunto());
                }
                if (plantaDto.getPorcentajeAumento() != null) {
                    planta.get().setPorcentajeAumento(plantaDto.getPorcentajeAumento());
                    Double incremento = planta.get().getValorUnidad() + plantaDto.getPorcentajeAumento() * planta.get().getValorUnidad();
                    planta.get().setValorUnidad(incremento);
                }
                plantaRepository.save(planta.get());
                plantaDtoList1.add(PlantaDto.builder()
                        .idPlanta(planta.get().getIdPlanta())
                        .asunto(planta.get().getAsunto())
                        .centroCosto(planta.get().getCentroCosto())
                        .nombrePlanta(planta.get().getNombrePlanta())
                        .urlImg(planta.get().getUrlImg())
                        .idOperador(planta.get().getIdOperador())
                        .idCliente(planta.get().getCliente().getIdCliente())
                        .valorUnidad(planta.get().getValorUnidad())
                        .porcentajeAumento(planta.get().getPorcentajeAumento())
                        .build());
            }
        }
        return plantaDtoList1;
    }

    /**
     * Verifica si una planta está registrada en facturación especial.
     *
     * @param idPlanta Identificador de la planta.
     * @return Un String que indica si la planta pertenece a facturación especial.
     * @throws Exception Si ocurre un error durante la verificación.
     */
    @Override
    public String verifyPlantaInFacturacionEspecial(String idPlanta) throws Exception {
        try {
            return plantaRepository.verifyPlantaInFacturacionEspecial(idPlanta);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
