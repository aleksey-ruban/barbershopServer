package com.alekseyruban.barbershopServer.helpers;

import com.alekseyruban.barbershopServer.dto.AuthTokenDTO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailWorker {

    static private final String username = "";
    static private final String password = "";

    static public void sendConfirmEmail(AuthTokenDTO token) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                String email = token.getClient().getEmail();

                String host = "smtp.gmail.com";
                String from = "brillantbarbershop@gmail.com";
                String to = email;

                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.port", "587");

                String link = "10.0.1.2:8080/authorization/confirm-email/" + token.getToken() + "_" + token.getClient().getId();


                Session session = Session.getInstance(props,
                        new Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(to));
                    message.setSubject("Подтверждение аккаунта BRILLANT");

                    String htmlContent = """
                    <html>
                    <body>
                        <p>
                            Кто-то попытался создать аккаунт в сети барбершопов BRILLANT с помощью
                            <a href="mailto:%s">%s</a>. Если это были вы, перейдите по ссылке, чтобы активировать аккаунт.
                        </p>
                        <a href="http://%s">%s</a>
                        <p>Всего хорошего и до встречи в сети BRILLANT!</p>
                    </body>
                    """.formatted(email, email, link, link);

                    MimeBodyPart htmlPart = new MimeBodyPart();
                    htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");

                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(htmlPart);

                    message.setContent(multipart);

                    Transport.send(message);

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
    }

    static public void successEmail(String email) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                String host = "smtp.gmail.com";
                String from = "brillantbarbershop@gmail.com";
                String to = email;

                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props,
                        new Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(to));
                    message.setSubject("Аккаунт BRILLANT активирован");

                    String htmlContent = """
                    <html>
                    <body>
                        <p>
                            Вы успешно активировали свой аккаунт в сети барбершопов BRILLANT.
                            Ждём Вас на приёме у наших мастеров.
                        </p>
                        <p>
                            Также напоминаем, что у Вас есть скидка на первый визит 30%!
                        </p>
                    </body>
                    """;

                    MimeBodyPart htmlPart = new MimeBodyPart();
                    htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");

                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(htmlPart);

                    message.setContent(multipart);

                    Transport.send(message);

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
    }

    static public void passwordRestored(String email, String newPassword) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                String host = "smtp.gmail.com";
                String from = "brillantbarbershop@gmail.com";
                String to = email;

                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props,
                        new Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(to));
                    message.setSubject("Новый пароль");

                    String htmlContent = """
                    <html>
                    <body>
                        <p>
                            Вы сделали запрос на восстановление пароля.
                            Мы сгенерировали для Вас новый пароль ниже, никому его не сообщайте! 
                        </p>
                        <p>
                            %s
                        </p>
                        <p>
                            Просто скопируйте его и используйте для авторизации на сайте
                        </p>
                    </body>
                    """.formatted(newPassword);

                    MimeBodyPart htmlPart = new MimeBodyPart();
                    htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");

                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(htmlPart);

                    message.setContent(multipart);

                    Transport.send(message);

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
    }
}