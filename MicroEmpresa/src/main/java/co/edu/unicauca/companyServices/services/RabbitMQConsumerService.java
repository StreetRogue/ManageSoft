package co.edu.unicauca.companyServices.services;

import co.edu.unicauca.companyServices.dtos.EmpresaDTO;
import co.edu.unicauca.companyServices.mappers.ProyectoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQConsumerService {

    private final EmpresaService empresaService;
    private final ProyectoMapper proyectoMapper;

    @RabbitListener(queues = "empresas_queue")
    public void receiveEmpresaRegistration(EmpresaDTO empresaDTO) {
        try {
            empresaService.guardarEmpresaDesdeCola(empresaDTO);
            System.out.println("Empresa recibida y procesada: " + empresaDTO.getNitEmpresa());
        } catch (Exception e) {
            System.err.println("Error al procesar empresa: " + e.getMessage());
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}