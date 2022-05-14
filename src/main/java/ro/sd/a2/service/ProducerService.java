package ro.sd.a2.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.AppUserDTO;
import ro.sd.a2.dto.UserLoginDTO;
import ro.sd.a2.entity.AppUser;

@Service
public class ProducerService {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public ProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    public void sendMessage(UserLoginDTO user) {
        rabbitTemplate.convertAndSend(exchange,routingkey, user);
    }

}
