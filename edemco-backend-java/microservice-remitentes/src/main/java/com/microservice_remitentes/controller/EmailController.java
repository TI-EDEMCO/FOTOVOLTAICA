package com.microservice_remitentes.controller;

import com.microservice_remitentes.entities.Email;
import com.microservice_remitentes.exceptions.EmailConflictException;
import com.microservice_remitentes.exceptions.EmailNotFoundException;
import com.microservice_remitentes.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
public class EmailController {

    @Autowired
    private EmailServiceImpl emailService;

    /**
     * Obtiene todos los registros de correos electrónicos.
     * @return ResponseEntity con la lista de todos los correos electrónicos.
     */
    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(emailService.findAll());
    }

    /**
     * Crea un nuevo registro de correo electrónico.
     * @param email Objeto Email que contiene los datos del correo a crear.
     * @return ResponseEntity con el correo electrónico creado.
     * @throws EmailConflictException Si el correo electrónico ya existe.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createEmail(@RequestBody Email email) throws EmailConflictException {
        return ResponseEntity.ok(emailService.createEmail(email));
    }

    /**
     * Elimina un registro de correo electrónico por su ID.
     * @param idEmail ID del correo electrónico a eliminar.
     * @return ResponseEntity indicando si la operación fue exitosa.
     * @throws EmailNotFoundException Si no se encuentra el correo electrónico con el ID proporcionado.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEmail(@RequestParam("idEmail") Long idEmail) throws EmailNotFoundException {
        return ResponseEntity.ok(emailService.deleteEmail(idEmail));
    }

    /**
     * Actualiza un registro de correo electrónico existente.
     * @param email Objeto Email que contiene los nuevos datos del correo.
     * @return ResponseEntity con el correo electrónico actualizado.
     * @throws EmailNotFoundException Si no se encuentra el correo electrónico a actualizar.
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateEmail(@RequestBody Email email) throws EmailNotFoundException {
        return ResponseEntity.ok(emailService.updateEmail(email));
    }

    /**
     * Obtiene todos los correos electrónicos relacionados con una planta específica.
     * @param idPlanta ID de la planta para filtrar los correos electrónicos.
     * @return ResponseEntity con la lista de correos electrónicos relacionados con la planta.
     * @throws EmailNotFoundException Si no se encuentran correos electrónicos relacionados con el ID de la planta.
     */
    @GetMapping("/find")
    public ResponseEntity<?> findAll(@RequestParam("idPlanta") String idPlanta) throws EmailNotFoundException {
        return ResponseEntity.ok(emailService.findByIdPlanta(idPlanta));
    }
}
