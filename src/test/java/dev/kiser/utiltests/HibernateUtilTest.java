package dev.kiser.utiltests;

import dev.kiser.utils.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

public class HibernateUtilTest {

    @Test
    void creates_sessionFactory(){
        SessionFactory sf = HibernateUtil.getSessionFactory();
        System.out.println(sf);
    }
}
