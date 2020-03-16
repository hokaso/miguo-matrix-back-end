package com.miguo.matrix.config;

import org.springframework.amqp.core.Binding;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2020-03-07 13:26
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue videoSendingQueue(){
        return new Queue("videoSendingQueue",true);
    }

    DirectExchange videoSendingExchange(){
        return new DirectExchange("videoSendingExchange");
    }

    @Bean
    Binding bindingVideoSending(){
        return BindingBuilder.bind(videoSendingQueue()).to(videoSendingExchange()).with("videoSendingRouting");
    }

}
