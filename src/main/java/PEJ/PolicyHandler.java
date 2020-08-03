package PEJ;

import PEJ.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{

    @Autowired
    PurchaseRepository purchaseRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOutOfStock_Cancel(@Payload OutOfStock outOfStock){

        System.out.println("#####START");

        if(outOfStock.isMe()){
            System.out.println("#####START_1 + " + outOfStock.getPurchaseId());

            Optional<Purchase> purchaseOptional = purchaseRepository.findAllByPurchaseIdEquals(outOfStock.getPurchaseId());

            Purchase purchase = purchaseOptional.get();

            System.out.println("#####START_2 + "  + purchase.getId());

            purchaseRepository.delete(purchase);

        }

    }

}
