package com.oksanapiekna.atelieshop.viberBot;

import com.oksanapiekna.atelieshop.entity.OrderDetails;
import com.oksanapiekna.atelieshop.entity.OrderInfo;
import com.oksanapiekna.atelieshop.service.ViberTokenService;
import com.oksanapiekna.atelieshop.service.ViberUserProfileService;
import com.viber.bot.Response;
import com.viber.bot.api.MessageDestination;
import com.viber.bot.event.incoming.IncomingMessageEvent;
import com.viber.bot.message.Message;
import com.viber.bot.message.TextMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViberListenerServiceImpl implements ViberListenerService {
    private final ViberUserProfileService viberUserProfileService;
    private final ViberTokenService viberTokenService;
    private final ViberBotConfig viberBot;

    public ViberListenerServiceImpl(ViberUserProfileService viberUserProfileService, ViberTokenService viberTokenService,@Lazy ViberBotConfig viberBot) {
        this.viberUserProfileService = viberUserProfileService;
        this.viberTokenService = viberTokenService;
        this.viberBot = viberBot;
    }

    @Override
    public void messageReceived(IncomingMessageEvent event, Message message, Response response) {
        TextMessage responseText = null;
        System.out.println(event.getChatId());
        System.out.println(event.getToken());
        System.out.println(event.getSender());
        System.out.println(message.toString());
        if(message.getType().equals("text")){
            TextMessage textMessage = (TextMessage) message;
            if(textMessage.getText().contains(viberTokenService.getToken().toString())){
                if(viberUserProfileService.findById(event.getSender().getId())==null) {
                    viberUserProfileService.save(event.getSender(), event.getChatId());
                }
                responseText = new TextMessage("Ви успішно підписані на сповіщення.");
            }

            if(textMessage.getText().contains("Stop") || textMessage.getText().contains("Стоп")){
                viberUserProfileService.deleteByID(event.getSender().getId());
                responseText = new TextMessage("Сповіщення вимкнено.");
            }
            if(responseText!=null) {
                response.send(responseText);
            }else {
                response.send(message);
            }
        }
    }

    @Override
    public void sendForAll(OrderInfo orderInfo) {
        String str = "Ім'я: "+orderInfo.getNameOfClient()+
                "\nТелефон: "+orderInfo.getPhone()+
                "\nОпис: "+orderInfo.getDescription();
        for(OrderDetails details:orderInfo.getOrderDetails()){
            str+="\n\nТовар: "+details.getProduct().getName()+
                    "\nКількість: "+details.getCount()+
                    "\nСума: "+details.getCount()*details.getProduct().getPrice();
        }

        TextMessage message = new TextMessage(str);
        for(MessageDestination msg : viberUserProfileService.findAllAndConvert()){
            viberBot.sendMessage(msg,message);
        }
    }

    @Override
    public void sendForAll(String name, String phone, String description) {
        TextMessage message = new TextMessage(
                "Ім'я: "+name+
                "\nТелефон: "+phone+
                "\nОпис: "+description
        );
        for(MessageDestination msg : viberUserProfileService.findAllAndConvert()){
            viberBot.sendMessage(msg,message);
        }
    }
}
