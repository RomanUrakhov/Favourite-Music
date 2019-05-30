package com.urakhov;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public final class DatabaseHandler {

    private static ApplicationContext contextForAdmin = null;
    private static ApplicationContext contextForGuest = null;

    public static ApplicationContext getInstanceForAdminConnection() {
        if (contextForAdmin == null) {
            contextForAdmin = new ClassPathXmlApplicationContext("Spring-Module.xml");
        }
        return contextForAdmin;
    }

    public static ApplicationContext getInstanceForGuestConnection() {
        if (contextForGuest == null) {
            contextForGuest = new ClassPathXmlApplicationContext("Spring-Guest.xml");
        }
        return contextForGuest;
    }

}
