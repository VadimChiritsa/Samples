

import com.epam.drools.Product;
import com.epam.drools.User;


import java.io.IOException;
import java.util.*;


public class Main {

    public static void main(String[] args) throws ClassNotFoundException, IOException {


        /*List list = new ArrayList();
        LinkedHashMap map = new LinkedHashMap();
        LinkedHashMap map2 = new LinkedHashMap();

        map2.put("id", null);
        map2.put("name", "roman");
        map2.put("status", "lead");
        map.put("com.epam.drools.User", map2);

        list.add(map);*/


        User user = new User();
        user.setName("roman");
        User user2 = new User();
        user2.setName("vitalik");
        Product product = new Product();
        product.setType("gold");

        List list = new ArrayList();
        list.add(user);
        list.add(user2);
        //list.add(product);

        DecisionServerTest2 serverTestSecond = new DecisionServerTest2();
        serverTestSecond.initialize();

        for (int j = 0; j <= 3; j++) {
            for (int i = 0; i <= 5000; i++) {
                list.add(new User("vitalik", ""));
            }
                serverTestSecond.executeCommands(list);

            System.out.println("##########################################################   " + list.size());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        //System.out.println(serverTestSecond.executeCommands(list));

    }
}
