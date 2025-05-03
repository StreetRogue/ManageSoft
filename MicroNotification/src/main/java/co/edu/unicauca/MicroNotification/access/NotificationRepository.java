package co.edu.unicauca.MicroNotification.access;

import co.edu.unicauca.MicroNotification.entities.Notification;
import co.edu.unicauca.MicroNotification.entities.NotificationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,NotificationId>{
}
