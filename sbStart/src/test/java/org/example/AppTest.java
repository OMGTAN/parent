package org.example;

import static org.junit.Assert.assertTrue;

import org.example.pojo.PeopleForm;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    public static void main(String[] args) {
        PeopleForm p1 = new PeopleForm();
        PeopleForm p2 = new PeopleForm();
        List<PeopleForm> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.stream().forEach(a->a.setAge(1));
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getAge());
        }
    }
}
