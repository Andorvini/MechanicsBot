package dev.Andorvini;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import java.sql.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        String token = "";
        TelegramBot bot = new TelegramBot(token);

        HashMap<Long, Boolean> userState = new HashMap();

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db");

            bot.setUpdatesListener(updates -> {

                for (Update update: updates) {

                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                        new InlineKeyboardButton("cum").callbackData("cum"));

//                      if (update.message() != null) {
//                          long chatId = update.message().chat().id();
//                      } else if (update.callbackQuery() != null) {
//                          long chatId = update.callbackQuery().
//                      }

                    long chatId = 483452411;

                    if (!userState.containsKey(chatId)) {
                        userState.put(chatId,false);
                    }

                    if  (!userState.get(chatId)) {

                        if (update.message() != null) {
                            if (update.message().text().equals("/tablecreate")) {
                                SendResponse tableNameResponse = bot.execute(new SendMessage(chatId, "Enter table name").replyMarkup(inlineKeyboard));
                                userState.put(chatId, true);
                            }
                        }

                    } else if (update.message() != null){

                        String tableName = update.message().text();

                        try {
                            Statement statement = connection.createStatement();
                            statement.executeUpdate("CREATE TABLE " + tableName + " (cum INTEGER);");
                            statement.closeOnCompletion();
                            System.out.println("succ");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                        userState.put(chatId,false);

                    } else if (update.callbackQuery() != null) {
                        if (update.callbackQuery().data().equals("cum")) {
                            SendResponse response = bot.execute(new SendMessage(chatId,"sad"));
                            userState.put(chatId,false);
                        }
                    }
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}