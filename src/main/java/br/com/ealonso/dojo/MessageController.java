package br.com.ealonso.dojo;

import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("api/message")
@RestController
public class MessageController {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@CrossOrigin
	@PostMapping
	public String createMessage(@RequestBody Mensagem mensagem) {
		log.info("Mensagem recebida: {} ", mensagem.toString());
		
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setHeader("app_id", "onboardingec");
		messageProperties.setHeader("persistent", true);
		messageProperties.setHeader("message_id", UUID.randomUUID().toString());

		Message message = new Jackson2JsonMessageConverter().toMessage(mensagem, messageProperties);
		rabbitTemplate.convertAndSend("x.dojorabbit.notificacao", "", message);
		return "Ok";
			
	}
	
}
