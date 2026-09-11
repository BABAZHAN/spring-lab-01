package kz.iitu.spring_lab_01.web;

import kz.iitu.spring_lab_01.lifecycle.LifecycleDemo;
import kz.iitu.spring_lab_01.notify.NotificationService;
import kz.iitu.spring_lab_01.notify.Notifier;
import kz.iitu.spring_lab_01.scope.TicketOffice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab2")
public class Lab2Controller {

    private final NotificationService notifications;
    private final LifecycleDemo lifecycle;
    private final TicketOffice ticketOffice;
    private final Notifier maskingNotifier;

    public Lab2Controller(NotificationService notifications,
                          LifecycleDemo lifecycle,
                          TicketOffice ticketOffice,
                          @Qualifier("masking") Notifier maskingNotifier) {
        this.notifications = notifications;
        this.lifecycle = lifecycle;
        this.ticketOffice = ticketOffice;
        this.maskingNotifier = maskingNotifier;
    }

    @GetMapping("/notify")
    public Map<String, Object> notify(@RequestParam(defaultValue = "Hello") String text) {
        return Map.of("primary",   notifications.viaPrimary(text),
                "console",   notifications.viaConsole(text),
                "all",       notifications.viaAll(text),
                "beanNames", notifications.names());
    }

    @GetMapping("/lifecycle")
    public List<String> lifecycle() {
        return lifecycle.events();
    }

    @GetMapping("/scopes")
    public Map<String, Object> scopes() {
        return ticketOffice.demo();
    }

    // Индивидуальное задание (Вариант 6)
    @GetMapping("/custom")
    public String custom(@RequestParam(defaultValue = "user123phone89") String text) {
        return maskingNotifier.send(text);
    }
}