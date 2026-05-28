package com.barbershop.service;

import com.barbershop.entity.Appointment;
import com.barbershop.entity.Client;
import com.barbershop.repository.AppointmentRepository;
import com.barbershop.repository.ClientRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada"));
    }

    public Appointment save(Appointment appointment) {
        Client client = findClientFromAppointment(appointment);
        appointment.setClient(client);
        return appointmentRepository.save(appointment);
    }

    public Appointment update(Long id, Appointment appointment) {
        Appointment existingAppointment = findById(id);

        existingAppointment.setDate(appointment.getDate());
        existingAppointment.setTime(appointment.getTime());
        existingAppointment.setService(appointment.getService());

        Client client = findClientFromAppointment(appointment);
        existingAppointment.setClient(client);

        return appointmentRepository.save(existingAppointment);
    }

    public void delete(Long id) {
        Appointment existingAppointment = findById(id);
        appointmentRepository.delete(existingAppointment);
    }

    private Client findClientFromAppointment(Appointment appointment) {
        if (appointment.getClient() == null || appointment.getClient().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cita debe incluir client.id");
        }

        return clientRepository.findById(appointment.getClient().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }
}