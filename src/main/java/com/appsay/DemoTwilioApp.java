package com.appsay;

/**
 * Created by appsay02 on 10-05-2017.
 */
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class DemoTwilioApp {
    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "AC6eb6f96b29a13d8bd2a85671ddb97a05";
    public static final String AUTH_TOKEN = "82d69172cf80d39d01267ea06932be44";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);


        Message message = Message.creator(new PhoneNumber("+919503476400"),
                new PhoneNumber("+19703001979"),
                "Hi Sachin,\n Welcome to Twilio.").create();

        System.out.println(message.getSid());
    }
}

