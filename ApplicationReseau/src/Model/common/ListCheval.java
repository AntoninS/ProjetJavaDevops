package Model.common;

import java.util.ArrayList;
import java.util.List;

public class ListCheval {




    public static List<Cheval> getChevaux()
    {
        ArrayList listChevalCourse = new ArrayList<>();
        Cheval ch = new Cheval("dada", 100 ,1,0.0, 500.0);
        listChevalCourse.add(ch);
        Cheval ch1 = new Cheval("dada1", 100 ,10,0.0,350.0);
        listChevalCourse.add(ch1);
        Cheval ch2 = new Cheval("dada2", 100 ,11,0.0,400.0);
        listChevalCourse.add(ch2);
        Cheval ch3 = new Cheval("dada3", 100 ,12,0.0,350.0);
        listChevalCourse.add(ch3);
        Cheval ch4 = new Cheval("dada4", 100 ,13,0.0,750.0);
        listChevalCourse.add(ch4);
        Cheval ch5 = new Cheval("dada5", 100 ,14,0.0,1000.0);
        listChevalCourse.add(ch5);

        return listChevalCourse;
    }
}
