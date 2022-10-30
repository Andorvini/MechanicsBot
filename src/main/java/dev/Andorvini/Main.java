package dev.Andorvini;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {

        String token = "";
        TelegramBot bot = new TelegramBot(token);

        HashMap<Long, Boolean> userState = new HashMap();

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\JavaProjects\\MechanicsBot\\src\\main\\java\\dev\\Andorvini\\test.db");
            Statement statement = connection.createStatement();

            bot.setUpdatesListener(updates -> {


                for (Update update: updates) {

                    long chatId = update.message().chat().id();

                    if (!userState.containsKey(chatId)) {
                        userState.put(chatId,false);
                    }

                    if (Objects.equals(update.message().text(), "/tablecreate")) {

                        if (!userState.get(chatId)) {
                            SendResponse tableNameResponse = bot.execute(new SendMessage(chatId,"Enter table name"));
                            userState.put(chatId,true);
                        } else if (userState.get(chatId)) {
                            String tableName = update.message().text();
                            try {
                                statement.executeUpdate("CREATE TABLE" + tableName + "(cum INTEGER);");
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            userState.put(chatId,false);
                        }
                        //SendResponse valuesResponse = bot.execute(new SendMessage(chatId,"Send me values you want to be added into the table" + "Example:" + "(id INTEGER," + " name STRING)"));
                    }

                }

                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            });


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}