package br.com.ealonso.dojo.application.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

	@Value("${jms.queue.name}")
	private String filaDeTrabalho;
	
	@Value("${jms.queue.error}")
	private String filaDeErro;
	
	@Value("${jms.exchange.work}")
	private String exchange;
	
	@Value("${jms.exchange.error}")
	private String exchangeDeErro;
	
	@Bean
	public TopicExchange createExchange() {
		return new TopicExchange(exchange);
	}

	@Bean
	public TopicExchange createExchangeDeErro() {
		return new TopicExchange(exchangeDeErro);
	}
	
	@Bean
	public Queue createFila() {
		return QueueBuilder.durable(filaDeTrabalho)
				.withArgument("x-dead-letter-exchange", exchangeDeErro)
				.withArgument("durable", true)
				.build();
	}
	
	@Bean
	public Queue createFilaDeErro() {
		return QueueBuilder.durable(filaDeErro)
				.withArgument("durable", true)
				.build();
	}
	
	@Bean
	public Binding bindingDeTrabalho() {
		return BindingBuilder.bind(createFila()).to(createExchange()).with("");
	}
	
	@Bean
	public Binding bindingDeErro() {
		return BindingBuilder.bind(createFilaDeErro()).to(createExchangeDeErro()).with("");
	}

	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}
	
	
}
