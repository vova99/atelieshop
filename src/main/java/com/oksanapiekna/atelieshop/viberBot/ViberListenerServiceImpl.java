package com.oksanapiekna.atelieshop.viberBot;

import com.viber.bot.message.Message;
import com.viber.bot.message.MessageKeyboard;
import com.viber.bot.message.TrackingData;
import org.springframework.stereotype.Service;

@Service
public class ViberListenerServiceImpl implements ViberListenerService {
    @Override
    public String listen(Message message) {
        MessageKeyboard keyboard = message.getKeyboard();
        TrackingData trackingData =  message.getTrackingData();
        return "null";
    }
}
