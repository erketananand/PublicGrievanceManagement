package com.scaler.notification.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvUtil {

    private static final Dotenv dotenv;

    static {
        try {
            dotenv = Dotenv.load();
        } catch (Exception e) {
            // Printing error to console and throwing a runtime exception.
            System.err.println("Error loading .env file.");
            e.printStackTrace();
            throw new RuntimeException("Failed to load .env file", e);
        }
    }

    public static String get(String key) {
        try {
            return dotenv.get(key);
        } catch (Exception e) {
            // Printing error to console
            System.err.println("Error fetching value for key: " + key);
            e.printStackTrace();
            return null;
        }
    }

    public static void loadEnvironmentVariables() {
        try {
            // Mailgun email  Credentials
            String username = get("MAILGUN_MAIL_USERNAME");
            String password = get("MAILGUN_MAIL_PASSWORD");
            String smtpFrom = get("MAILGUN_MAIL_SMTP_FROM");

            if (username != null) {
                System.setProperty("MAILGUN_MAIL_USERNAME", username);
            }
            if (password != null) {
                System.setProperty("MAILGUN_MAIL_PASSWORD", password);
            }
            if (smtpFrom != null) {
                System.setProperty("MAILGUN_MAIL_SMTP_FROM", smtpFrom);
            }

            // Twilio SMS  Credentials
            String twilioAccountSid = get("TWILIO_ACCOUNT_SID");
            String twilioAuthToken = get("TWILIO_AUTH_TOKEN");
            String twilioSenderPhoneNumber = get("TWILIO_PHONE_NUMBER");

            if (twilioAccountSid != null) {
                System.setProperty("twilio.accountSid", twilioAccountSid);
            }
            if (twilioAuthToken != null) {
                System.setProperty("twilio.authToken", twilioAuthToken);
            }
            if (twilioSenderPhoneNumber != null) {
                System.setProperty("twilio.phoneNumber", twilioSenderPhoneNumber);
            }

        } catch (Exception e) {
            System.err.println("Error loading environment variables.");
            e.printStackTrace();
        }
    }
}
