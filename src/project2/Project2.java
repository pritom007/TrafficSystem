package project2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import javax.swing.UIManager;

/**
 *
 * @author PritomKumar
 */
public class Project2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        //going to gui
        Map map = new Map();
        map.setTitle("Transport ");
        map.setSize(1250, 650);
        map.setVisible(true);
        map.drawMap();
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    }

}
