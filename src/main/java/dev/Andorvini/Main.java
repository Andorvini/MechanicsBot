package dev.Andorvini;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        String token = "";

        TelegramBot bot = new TelegramBot(token);

        bot.setUpdatesListener(updates -> {

            for (Update update: updates) {
                long chatId = update.message().chat().id();
                if (Objects.equals(update.message().text(), "as")) {
                    SendResponse response = bot.execute(new SendMessage(1264782440,"ass"));
                } else {
                SendResponse response = bot.execute(new SendMessage(1264782440,String.valueOf(chatId))); }

            }

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });





    }
}