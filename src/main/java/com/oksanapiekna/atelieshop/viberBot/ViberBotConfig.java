package com.oksanapiekna.atelieshop.viberBot;

import com.google.common.base.Preconditions;
import com.google.common.net.MediaType;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.Uninterruptibles;
import com.sun.istack.Nullable;
import com.viber.bot.Request;
import com.viber.bot.ViberSignatureValidator;
import com.viber.bot.api.ViberBot;
import com.viber.bot.message.TextMessage;
import com.viber.bot.profile.BotProfile;
import fi.iki.elonen.NanoHTTPD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
//@Component
public class ViberBotConfig extends NanoHTTPD {
    @Autowired
    private ViberListenerService viberListenerService;

    private static final int PORT =15001;

    private static final String AUTH_TOKEN = "4cc3fede4d67de74-d83368003d5b2c32-db48269e48e4be64";
    private static final String WEBHOOK_URL = "https://be7371825da3.ngrok.io";


    private final ViberBot bot;
    private final ViberSignatureValidator signatureValidator;

    ViberBotConfig() throws IOException, ExecutionException, InterruptedException {
        super(PORT);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);

        bot = new ViberBot(new BotProfile("Echo Bot"), AUTH_TOKEN);
        signatureValidator = new ViberSignatureValidator(AUTH_TOKEN);


        bot.setWebhook(WEBHOOK_URL).get();
        bot.onMessageReceived((event, message, response) -> response.send(
                viberListenerService.listen(message)
        )); // echos everything back
        bot.onConversationStarted(event -> Futures.immediateFuture(Optional.of(
                new TextMessage("Hi " + event.getUser().getName()))));
    }

    public static void init() throws IOException, ExecutionException, InterruptedException {
        new ViberBotConfig();
    }

    @Override
    public Response serve(final IHTTPSession session) {
        try {
            final String json = parsePostData(session);
            final String serverSideSignature = session.getHeaders().get("x-viber-content-signature");
            Preconditions.checkState(signatureValidator.isSignatureValid(serverSideSignature, json), "invalid signature");

            final Request request = Request.fromJsonString(json);
            final InputStream inputStream = Uninterruptibles.getUninterruptibly(bot.incoming(request));

            return newChunkedResponse(Response.Status.OK, MediaType.JSON_UTF_8.toString(), inputStream);
        } catch (final ExecutionException e) {
            e.printStackTrace();
            return newFixedLengthResponse("Error, sorry");
        }
    }

    @Nullable
    private String parsePostData(final IHTTPSession session) {
        final Map<String, String> body = new HashMap<>();
        try {
            session.parseBody(body);
        } catch (IOException | ResponseException e) {
            e.printStackTrace();
        }
        return body.get("postData");
    }



}
