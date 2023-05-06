package com.ashok.demoes.producer.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerDemoController {

    private MessageChannel controlChannel;

    public ProducerDemoController(@Qualifier("controlChannel") MessageChannel controlChannel){
        this.controlChannel = controlChannel;
    }

    @GetMapping("/stopProducer")
    public  void stopProducer(){
        controlChannel.send(new GenericMessage<>("@kafkaProducerBean.stop()"));
    }

    @GetMapping("/startProducer")
    public void startProducer(){
        controlChannel.send(new GenericMessage<>("@kafkaProducerBean.start()"));
    }
}
