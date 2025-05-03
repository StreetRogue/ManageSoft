    package co.edu.unicauca.MicroNotification.services;


    import co.edu.unicauca.MicroNotification.access.NotificationRepository;
    import co.edu.unicauca.MicroNotification.entities.Notification;
    import co.edu.unicauca.MicroNotification.infra.config.RabbitMQConfig;
    import co.edu.unicauca.MicroNotification.infra.dto.NotificationDTO;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import org.springframework.amqp.rabbit.annotation.RabbitListener;

    @Service
    @RequiredArgsConstructor
    public class NotificationService {

        private final NotificationRepository notificacionRepository;


        @RabbitListener(queues = "cola.notificacion")
        public void guardarNotificacion(NotificationDTO dto) {
            try {
                // Crear la notificación a partir del DTO recibido
                Notification noti = Notification.builder()
                        .emailEstudiante(dto.getEmailEstudiante())
                        .emailCoordinador(dto.getEmailCoordinador())
                        .idProyecto(dto.getIdProyecto())
                        .asunto(dto.getAsunto())
                        .motivo(dto.getMotivo())
                        .estado("ENVIADO")
                        .build();

                // Guardamos la notificación en la base de datos
                notificacionRepository.save(noti);

                System.out.println("Notificación procesada y guardada: " + noti);

            } catch (Exception e) {
                // Si ocurre un error, lo manejamos aquí
                e.printStackTrace();
            }
        }
    }
