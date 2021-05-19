package br.com.ealonso.dojo;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class ConsumerJMS {

	@RabbitListener(queues = {"${jms.queue.name}"})
	public void consumer(Message message) throws Exception {
		log.info("Consumer message: {}", message);
		throw new Exception("Mensagem invalida");
	}

//	@RabbitListener(queues = {"${jms.queue.wait}"})
	public void waitConsumer(Message message) throws Exception {
		log.info("WAIT Consumer message: {}", message);
		throw new Exception("WAIT Mensagem invalida: POSTAR MSG NA FILA DE ERRO");
	}
	
}
