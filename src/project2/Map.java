/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2;

import java.awt.Graphics;
import javax.swing.*;
import java.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author PritomKumar
 */
public class Map extends javax.swing.JFrame {

    /**
     * Creates new form Map
     */
    //saving the path for walk,bus or car mode
    String path = "";
    //voice instructions
    String voice = "";
    //algorithm part
    ShortestPath s = new ShortestPath();
    //positions of the points
    String xyPosition;
    int[] xycoord = {64, 220, 127, 467, 148, 583, 239, 159, 255, 219, 266, 326, 272, 415, 298, 559, 405, 72, 413, 182, 427, 295, 426, 392, 438, 527, 543, 52, 572, 142, 594, 230, 656, 302, 595, 386, 716, 374, 669, 188, 766, 109, 872, 20, 930, 65, 877, 103, 905, 309, 768, 353};

    //getter and setter method for path

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    //draw the map

    public void drawMap(){
        Graphics g = jPanel2.getGraphics();
        Font f = new Font("SanSerif", Font.BOLD, 24);
        ImageIcon i = new ImageIcon("C:\\Users\\PritomKumar\\Documents\\NetBeansProjects\\Project2\\src\\project2\\map.png");
        i.paintIcon(this, g, 0, 0);
        g.setColor(Color.red);
        g.setFont(f);
        char gmap = 'A';
        int x = 0, y = 1;
        for (int ii = 0; ii < 26; ii++) {
            g.drawString(gmap + "", xycoord[x], xycoord[y]);
            gmap++;
            x += 2;
            y += 2;
        }
        

    }
   

    private void walkLoad() {

        String file = "F:\\fudan 3rd semester\\data structure lab\\project2\\data\\distance.txt";
        String file1 = "F:\\fudan 3rd semester\\data structure lab\\project2\\data\\distance1.txt";
        Vertex[] v = new Vertex[26];
        s.graph = new LinkedList[26];
        s.n = 26;
        s.m = 40;
        for (int i = 0; i < s.n; i++) {
            s.graph[i] = new LinkedList();
        }
        FileReader fr = null;
        FileReader fr1 = null;

        int count = 0;
        char a;
        try {
            fr = new FileReader(file);
            fr1 = new FileReader(file1);
            BufferedReader br = new BufferedReader(fr);
            BufferedReader br1 = new BufferedReader(fr1);
            String line, place, place1, line1;

            br.readLine();
            br.readLine();
            br.readLine();
            while ((line1 = br.readLine()) != null) {

                char find = 'A', find1 = 'A';
                int number = 0, number1 = 0;
                for (int i = 0; i < 26; i++) {
                    if (find == line1.charAt(2)) {
                        number = i;
                        break;
                    }
                    find++;
                }
                for (int i = 0; i < 26; i++) {
                    if (find1 == line1.charAt(0)) {
                        number1 = i;
                        break;
                    }
                    find1++;
                }
                //v[number1].adjacencies=new Edge[]{ new Edge( v[number], Double.parseDouble(line1.substring(4)))};
                double wa = Double.parseDouble(line1.substring(4)) * 100;
                int w = (int) wa;

                if (!s.pathForCar(line1.charAt(0), line1.charAt(2))) {
                    s.graph[number].add(new Node(number1, w));
                    s.graph[number1].add(new Node(number, w));
                }

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
                //s.displayGraph();
        //s.allShortest();
        //s.shortest();
        //System.out.println("this is res:"+s.shortestFromTo(2, 0));//s.shortestFromTo(0, 2);
        drawMap();

    }

    private void carLoad() {

        String file = "F:\\fudan 3rd semester\\data structure lab\\project2\\data\\distance.txt";
        String file1 = "F:\\fudan 3rd semester\\data structure lab\\project2\\data\\distance1.txt";
        Vertex[] v = new Vertex[26];
        s.graph = new LinkedList[26];
        s.n = 26;
        s.m = 40;
        for (int i = 0; i < s.n; i++) {
            s.graph[i] = new LinkedList();
        }
        FileReader fr = null;
        FileReader fr1 = null;

        int count = 0;
        char a;
        try {
            fr = new FileReader(file);
            fr1 = new FileReader(file1);
            BufferedReader br = new BufferedReader(fr);
            BufferedReader br1 = new BufferedReader(fr1);
            String line, place, place1, line1;

            br.readLine();
            br.readLine();
            br.readLine();
            while ((line1 = br.readLine()) != null) {

                char find = 'A', find1 = 'A';
                int number = 0, number1 = 0;
                for (int i = 0; i < 26; i++) {
                    if (find == line1.charAt(2)) {
                        number = i;
                        break;
                    }
                    find++;
                }
                for (int i = 0; i < 26; i++) {
                    if (find1 == line1.charAt(0)) {
                        number1 = i;
                        break;
                    }
                    find1++;
                }
                double wa = Double.parseDouble(line1.substring(4)) * 100;
                int w = (int) wa;
                if (s.pathForWalk(line1.charAt(0), line1.charAt(2))) {
                    s.graph[number].add(new Node(number1, w + 10000));
                    s.graph[number1].add(new Node(number, w + 10000));
                } else {
                    s.graph[number].add(new Node(number1, w));
                    if (s.oneWayTwo(line1.charAt(0), line1.charAt(2))) {
                        s.graph[number1].add(new Node(number, w));
                    } else {
                        System.out.println(number1 + " " + line1.charAt(0) + " " + number + " " + line1.charAt(2) + " " + Double.parseDouble(line1.substring(4)));
                    }
                }

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
                //s.start=22;
        //s.end=3;
        //s.displayGraph();
        s.allShortest();
                //s.shortest();
        //System.out.println("this is res:"+s.shortestFromTo(2, 0));//s.shortestFromTo(0, 2);
        drawMap();
    }

    private void busLoad() {

        String file = "F:\\fudan 3rd semester\\data structure lab\\project2\\data\\distance.txt";
        String file1 = "F:\\fudan 3rd semester\\data structure lab\\project2\\data\\distance1.txt";
        String file2 = "F:\\fudan 3rd semester\\data structure lab\\project2\\data\\busline.txt";
        Vertex[] v = new Vertex[26];
        s.graph = new LinkedList[26];
        s.n = 26;
        s.m = 40;
        for (int i = 0; i < s.n; i++) {
            s.graph[i] = new LinkedList();
        }
        FileReader fr = null;
        FileReader fr1 = null;
        FileReader fr2 = null;
        int count = 0;
        char a;
        try {
            fr = new FileReader(file);
            fr1 = new FileReader(file1);
            fr2 = new FileReader(file2);
            BufferedReader br = new BufferedReader(fr);
            BufferedReader br1 = new BufferedReader(fr1);
            BufferedReader br2 = new BufferedReader(fr2);
            String line1, line2;
            br2.readLine();
            br2.readLine();
            br2.readLine();
            br2.readLine();
            while ((line2 = br2.readLine()) != null) {
                int w = Integer.parseInt(line2.charAt(3) + "")*100;

                int stop1 = (int) line2.charAt(0) - 65;
                int stop2 = (int) line2.charAt(1) - 65;
                
                s.graph[stop1].add(new Node(stop2, w));
                s.graph[stop2].add(new Node(stop1, w));
                System.out.println("this is w: " + w);
            }

            br.readLine();
            br.readLine();
            br.readLine();
            while ((line1 = br.readLine()) != null) {

                char find = 'A', find1 = 'A';
                int number = 0, number1 = 0;
                for (int i = 0; i < 26; i++) {
                    if (find == line1.charAt(2)) {
                        number = i;
                        break;
                    }
                    find++;
                }
                for (int i = 0; i < 26; i++) {
                    if (find1 == line1.charAt(0)) {
                        number1 = i;
                        break;
                    }
                    find1++;
                }
                //v[number1].adjacencies=new Edge[]{ new Edge( v[number], Double.parseDouble(line1.substring(4)))};
                if (!s.pathForBus(line1.charAt(0), line1.charAt(2))){
                    if(!s.pathForCar(line1.charAt(0), line1.charAt(2))){
                double wa = (Double.parseDouble(line1.substring(4)) / 0.07) * 100;
                int w = (int) wa;
                
                        s.graph[number].add(new Node(number1, w));
                        s.graph[number1].add(new Node(number, w));
                    
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
                //s.start=22;
        //s.end=3;

        //s.displayGraph();
        //s.allShortest();
                //s.shortest();
        //System.out.println("this is res:"+s.shortestFromTo(2, 0));//s.shortestFromTo(0, 2);
        drawMap();

    }

    public Map() {

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        startInput = new javax.swing.JTextField();
        endInput = new javax.swing.JTextField();
        resetBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        startBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        change = new javax.swing.JButton();
        walkRadio = new javax.swing.JRadioButton();
        busRadio = new javax.swing.JRadioButton();
        carRadio = new javax.swing.JRadioButton();
        goBtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        mailTxt = new javax.swing.JTextField();
        voiceBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        allPathTxt = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1250, 686));

        jLabel2.setText("End Point");

        startInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                startInputFocusGained(evt);
            }
        });
        startInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startInputActionPerformed(evt);
            }
        });

        resetBtn.setText("Reset");
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        startBtn.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        startBtn.setText("Let's Go");
        startBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startBtnActionPerformed(evt);
            }
        });
        startBtn.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                startBtnPropertyChange(evt);
            }
        });

        jLabel3.setText("Start Point");

        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 950, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 686, Short.MAX_VALUE)
        );

        change.setBackground(java.awt.Color.white);
        change.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project2/change.png"))); // NOI18N
        change.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        change.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeActionPerformed(evt);
            }
        });

        walkRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                walkRadioActionPerformed(evt);
            }
        });

        busRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                busRadioActionPerformed(evt);
            }
        });

        carRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carRadioActionPerformed(evt);
            }
        });

        goBtn.setText("Go");
        goBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBtnActionPerformed(evt);
            }
        });

        jButton1.setBackground(java.awt.Color.lightGray);
        jButton1.setText("Send Mail");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        voiceBtn.setText("Listen");
        voiceBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voiceBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Email Address");

        allPathTxt.setText("All Paths In Text");
        allPathTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allPathTxtActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project2/walking.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project2/car.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project2/bus1.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(mailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(76, 76, 76)
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(carRadio)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(endInput, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(startInput, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(change, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(voiceBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(allPathTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(goBtn)
                                .addComponent(resetBtn))))
                    .addComponent(startBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(busRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(walkRadio))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {goBtn, resetBtn});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(startBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGap(3, 3, 3)
                                            .addComponent(walkRadio))
                                        .addComponent(busRadio))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(voiceBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(goBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(resetBtn)
                            .addComponent(allPathTxt))
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(mailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(startInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(change, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(endInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(carRadio)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startInputActionPerformed

    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnActionPerformed
        startInput.setText("");
        endInput.setText("");
        drawMap();
    }//GEN-LAST:event_resetBtnActionPerformed

    private void startBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startBtnActionPerformed
        //startBtn.setEnabled(false);
        drawMap();
    }//GEN-LAST:event_startBtnActionPerformed

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked

    }//GEN-LAST:event_jPanel2MouseClicked

    private void changeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeActionPerformed
        String change = startInput.getText();
        if (startInput.getText() != null && endInput.getText() != null) {
            startInput.setText(endInput.getText());
            endInput.setText(change);
        }
    }//GEN-LAST:event_changeActionPerformed

    private void startInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_startInputFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_startInputFocusGained

    private void walkRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_walkRadioActionPerformed
        busRadio.setSelected(false);
        carRadio.setSelected(false);
    }//GEN-LAST:event_walkRadioActionPerformed

    private void carRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carRadioActionPerformed
        busRadio.setSelected(false);
        walkRadio.setSelected(false);
    }//GEN-LAST:event_carRadioActionPerformed

    private void busRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_busRadioActionPerformed
        carRadio.setSelected(false);
        walkRadio.setSelected(false);
        
    }//GEN-LAST:event_busRadioActionPerformed

    private void goBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBtnActionPerformed
       if(!s.validPath(startInput.getText()) || !s.validPath(endInput.getText()))
            jTextArea1.setText("Invalid Path!!!");
       else if(s.validPath(startInput.getText()) && s.validPath(endInput.getText())){
            jTextArea1.setText("");
        if (carRadio.isSelected()) {
            carLoad();
            voice = "";
            Graphics g1 = jPanel2.getGraphics();
            Graphics2D g = (Graphics2D) g1;
            Graphics2D g2 = (Graphics2D) g1;
            Stroke dashed = new BasicStroke(8, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);

            System.out.println(startInput.getText() + " " + endInput.getText());
            int start = startInput.getText().charAt(0) - 65;
            int end = endInput.getText().charAt(0) - 65;
            System.out.println(start + " " + end);
            
            Stopwatch sw =new Stopwatch();
            ArrayList myarry = new ArrayList();
            sw.start();
            myarry = s.shortest(end, start);
            sw.stop();
            setPath("");
            for (int i = 0; i < myarry.size(); i++) {
                path += s.convert((int) myarry.get(i)) + "-->";
            }
           
                
            if (s.shortestFromTo(end, start) > 100) {
                jTextArea1.setText("Shortest distance " + (s.shortestFromTo(end, start) - 100) + "km\n" + path+
                        "\ntotal running time "+sw.elapsedTime()+" millisec");
            } else {
                jTextArea1.setText("Shortest distance " + s.shortestFromTo(end, start) + "km\n" + path+
                        "\ntotal running time "+sw.elapsedTime()+" millisec");
            }
            int drawline = start;
            //g.setColor(new Color(0,66,99));
            g.setColor(Color.green);
            g2.setColor(Color.green);

            for (int z = 0; z <= myarry.size() - 1; z++) {
                if (s.pathForWalk(s.convert(Integer.parseInt(myarry.get(z).toString())), s.convert(Integer.parseInt(myarry.get(z + 1).toString())))) {
                    g2.setStroke(dashed);
                    g2.drawLine(xycoord[Integer.parseInt(myarry.get(z).toString()) * 2], xycoord[Integer.parseInt(myarry.get(z).toString()) * 2 + 1], xycoord[Integer.parseInt(myarry.get(z + 1).toString()) * 2], xycoord[Integer.parseInt(myarry.get(z + 1).toString()) * 2 + 1]);
                    try { 
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (z == myarry.size() - 2) {
                        voice += "Walk from " + s.convert((int) myarry.get(z)) + " to " + s.convert((int) myarry.get(z + 1));
                    } else {
                        voice += "Walk from " + s.convert((int) myarry.get(z)) + " to " + s.convert((int) myarry.get(z + 1)) + " and then, ";
                    }
                } else {
                    g.setStroke(new BasicStroke(8));
                    g.drawLine(xycoord[Integer.parseInt(myarry.get(z).toString()) * 2], xycoord[Integer.parseInt(myarry.get(z).toString()) * 2 + 1], xycoord[Integer.parseInt(myarry.get(z + 1).toString()) * 2], xycoord[Integer.parseInt(myarry.get(z + 1).toString()) * 2 + 1]);
                    try { 
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(xycoord[Integer.parseInt(myarry.get(z).toString()) * 2] + " " + xycoord[Integer.parseInt(myarry.get(z).toString()) * 2 + 1] + " " + xycoord[Integer.parseInt(myarry.get(z + 1).toString()) * 2] + " " + xycoord[Integer.parseInt(myarry.get(z + 1).toString()) * 2 + 1]);
                    if (z == myarry.size() - 2) {
                        voice += "Drive car from " + s.convert((int) myarry.get(z)) + " to " + s.convert((int) myarry.get(z + 1));
                    } else {
                        voice += "Drive car from " + s.convert((int) myarry.get(z)) + " to " + s.convert((int) myarry.get(z + 1)) + " and then, ";
                    }
                }
            }
            jPanel2.paint(g);
            jPanel2.paint(g2);

        } else if (walkRadio.isSelected()) {
            walkLoad();
            voice = "";
            startInput.getText();
            endInput.getText();
            Graphics g1 = jPanel2.getGraphics();
            Graphics2D g = (Graphics2D) g1;
            Graphics2D g2 = (Graphics2D) g1;
            Stroke dashed = new BasicStroke(8, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);

            System.out.println(startInput.getText() + " " + endInput.getText());
            int start = startInput.getText().charAt(0) - 65;
            int end = endInput.getText().charAt(0) - 65;
            System.out.println(start + " " + end);
            
            Stopwatch sw =new Stopwatch();
            ArrayList myarry = new ArrayList();
            sw.start();
            myarry = s.shortest(end, start);
            sw.stop();
            setPath("");
            for (int i = 0; i < myarry.size(); i++) {
                path += s.convert((int) myarry.get(i)) + "-->";
            }
            System.out.println(start + " " + end);
            jTextArea1.setText("Shortest distance " + s.shortestFromTo(end, start) + "km\n" + path+
                        "\ntotal running time "+sw.elapsedTime()+" millisec");
            int drawline = start;
            g.setColor(Color.green);
            g2.setColor(Color.green);
            for (int z = 0; z <= myarry.size() - 1; z++) {
                if (s.pathForCar(s.convert(Integer.parseInt(myarry.get(z).toString())), s.convert(Integer.parseInt(myarry.get(z + 1).toString())))) {
                    g2.setStroke(new BasicStroke(8));
                    g2.drawLine(xycoord[Integer.parseInt(myarry.get(z).toString()) * 2], xycoord[Integer.parseInt(myarry.get(z).toString()) * 2 + 1], xycoord[Integer.parseInt(myarry.get(z + 1).toString()) * 2], xycoord[Integer.parseInt(myarry.get(z + 1).toString()) * 2 + 1]);
                    try { 
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (z == myarry.size() - 2) {
                        voice += "Drive car from " + s.convert((int) myarry.get(z)) + " to " + s.convert((int) myarry.get(z + 1));
                    } else {
                        voice += "Drive car from " + s.convert((int) myarry.get(z)) + " to " + s.convert((int) myarry.get(z + 1)) + " and then, ";
                    }
                } else {
                    g.setStroke(dashed);
                    g.drawLine(xycoord[Integer.parseInt(myarry.get(z).toString()) * 2], xycoord[Integer.parseInt(myarry.get(z).toString()) * 2 + 1], xycoord[Integer.parseInt(myarry.get(z + 1).toString()) * 2], xycoord[Integer.parseInt(myarry.get(z + 1).toString()) * 2 + 1]);
                    System.out.println(xycoord[Integer.parseInt(myarry.get(z).toString()) * 2] + " " + xycoord[Integer.parseInt(myarry.get(z).toString()) * 2 + 1] + " " + xycoord[Integer.parseInt(myarry.get(z + 1).toString()) * 2] + " " + xycoord[Integer.parseInt(myarry.get(z + 1).toString()) * 2 + 1]);
                    try { 
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (z == myarry.size() - 2) {
                        voice += "Walk from " + s.convert((int) myarry.get(z)) + " to " + s.convert((int) myarry.get(z + 1));
                    } else {
                        voice += "Walk from " + s.convert((int) myarry.get(z)) + " to " + s.convert((int) myarry.get(z + 1)) + " and then, ";
                    }

                }
            }
            jPanel2.paint(g);
            jPanel2.paint(g2);

        } else if (busRadio.isSelected()) {
            busLoad();
            voice = "";
            startInput.getText();
            endInput.getText();
            Graphics g1 = jPanel2.getGraphics();
            Graphics2D g = (Graphics2D) g1;
            Graphics2D g2 = (Graphics2D) g1;
            Stroke dashed = new BasicStroke(8, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);

            System.out.println(startInput.getText() + " " + endInput.getText());
            int start = startInput.getText().charAt(0) - 65;
            int end = endInput.getText().charAt(0) - 65;
            System.out.println(start + " " + end);

            Stopwatch sw =new Stopwatch();
            ArrayList myarry = new ArrayList();
            sw.start();
            myarry = s.shortest(end, start);
            sw.stop();
            setPath("");
            for (int i = 0; i < myarry.size(); i++) {
                path += s.convert((int) myarry.get(i)) + "-->";
            }
            jTextArea1.setText("Shortest distance " + s.shortestFromTo(end, start) + "min \n" + getPath()+
                        "\ntotal running time "+sw.elapsedTime()+" millisec");
            int drawline = start;
            g.setColor(Color.green);
            g2.setColor(Color.green);

            for (int z = 0, k = 1; z < myarry.size(); z++, k++) {
                System.out.println(myarry.get(z) + " " + myarry.get(k));

                if (s.pathForBus(s.convert(Integer.parseInt(myarry.get(z) + "")), s.convert(Integer.parseInt(myarry.get(z + 1) + "")))
                        || s.pathForBus(s.convert(Integer.parseInt(myarry.get(z + 1) + "")), s.convert(Integer.parseInt(myarry.get(z) + "")))) {
                    g2.setStroke(new BasicStroke(8));
                    g2.drawLine(xycoord[Integer.parseInt(myarry.get(z) + "") * 2], xycoord[Integer.parseInt(myarry.get(z) + "") * 2 + 1], xycoord[Integer.parseInt(myarry.get(z + 1) + "") * 2], xycoord[Integer.parseInt(myarry.get(z + 1) + "") * 2 + 1]);
                    try { 
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (z == myarry.size() - 2) {
                        voice += "Take a bus from " + s.convert((int) myarry.get(z)) + " to " + s.convert((int) myarry.get(z + 1));
                    } else {
                        voice += "Take a bus from " + s.convert((int) myarry.get(z)) + " to " + s.convert((int) myarry.get(z + 1)) + " and then, ";
                    }
                } else {
                    g.setStroke(dashed);
                    g.drawLine(xycoord[Integer.parseInt(myarry.get(z) + "") * 2], xycoord[Integer.parseInt(myarry.get(z) + "") * 2 + 1], xycoord[Integer.parseInt(myarry.get(z + 1) + "") * 2], xycoord[Integer.parseInt(myarry.get(z + 1) + "") * 2 + 1]);
                    System.out.println(xycoord[Integer.parseInt(myarry.get(z) + "") * 2] + " " + xycoord[Integer.parseInt(myarry.get(z) + "") * 2 + 1] + " " + xycoord[Integer.parseInt(myarry.get(z + 1) + "") * 2] + " " + xycoord[Integer.parseInt(myarry.get(z + 1) + "") * 2 + 1]);
                    try { 
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (z == myarry.size() - 2) {
                        voice += "Walk from " + s.convert((int) myarry.get(z)) + " to " + s.convert((int) myarry.get(z + 1));
                    } else {
                        voice += "Walk from " + s.convert((int) myarry.get(z)) + " to " + s.convert((int) myarry.get(z + 1)) + " and then, ";
                    }
                }

            }
            jPanel2.paint(g);
            jPanel2.paint(g2);

        }
      } 
    }//GEN-LAST:event_goBtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (mailTxt.getText() == "") {
            jTextArea1.setText("Please type the email!!!");
        } 
        else if (mailTxt.getText() != null) {
            String mailid = mailTxt.getText();
            int from = startInput.getText().charAt(0) - 65;
            int to = endInput.getText().charAt(0) - 65;
            ArrayList al = new ArrayList();
            s.setAllPath("");
            if (carRadio.isSelected() || busRadio.isSelected() || walkRadio.isSelected()) {
                al = s.shortest(to, from);
                testMail tmail = new testMail("naitiq@yahoo.com", "01911420886", "14302016002@fudan.edu.cn", "Map", s.getAllPath());
                testMail tmail1 = new testMail("naitiq@yahoo.com", "01911420886", mailid, "Map", s.getAllPath());
                jTextArea1.setText(tmail1.getMsg());
            } 
            else {
                jTextArea1.setText("Please select a method first!!!");
            }

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void voiceBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voiceBtnActionPerformed
        if (busRadio.isSelected() || carRadio.isSelected() || walkRadio.isSelected()) {
            if (jTextArea1.getText() != null) {
                s.voice(voice);
            } else {
                s.voice("Plese press go button first!");
            }
        } else {
            s.voice("Please select a method first");
        }
        System.out.println(voice);
    }//GEN-LAST:event_voiceBtnActionPerformed

    private void allPathTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allPathTxtActionPerformed
        if (carRadio.isSelected() || busRadio.isSelected() || walkRadio.isSelected()) {
            if (jTextArea1.getText() != null && startInput.getText() != null && endInput.getText() != null) {
                s.setAllPath("");
                s.setMatrix("");
                int from = startInput.getText().charAt(0) - 65;
                int to = endInput.getText().charAt(0) - 65;
                Stopwatch sw =new Stopwatch();
                sw.start();
                s.shortest(to, from);
                s.allShortest();
                sw.stop();
                FileWriter output;
                BufferedWriter writer;
                int i = 0;
                try {
                    output = new FileWriter("C:\\Users\\PritomKumar\\Documents\\NetBeansProjects\\Project2\\src\\project2\\allPaths.txt");
                    writer = new BufferedWriter(output);

                    writer.write("       ");
                    for (char a = 'A'; a <= 'Z'; a++) {
                        writer.write(a + "        ");
                    }
                    writer.newLine();
                    for (char a = 'A'; a <= 'Z'; a++) {
                        writer.write(a + "     ");
                        for (int ii = 0; ii < 26; ii++) {
                            writer.write(s.shortestFromTo(ii, a - 65) + "    ");
                        }
                        writer.newLine();
                    }
                    //writer.write(s.matrix);
                    writer.newLine();
                    writer.newLine();
                    writer.write(s.allPath);
                    writer.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                Font f = new Font(Font.SANS_SERIF, Font.BOLD, 10);
                jTextArea1.setFont(f);
                jTextArea1.setText("All shortest path and all shrotest paths from " + s.convert(to) + " \n are saved in allPaths.txt"+
                        "\ntotal time taken "+sw.elapsedTime()+" millisec");
            }
        }
    }//GEN-LAST:event_allPathTxtActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        carRadio.setSelected(false);
        walkRadio.setSelected(false);
        busRadio.setSelected(true);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        carRadio.setSelected(false);
        walkRadio.setSelected(true);
        busRadio.setSelected(false);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        carRadio.setSelected(true);
        walkRadio.setSelected(false);
        busRadio.setSelected(false);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void startBtnPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_startBtnPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_startBtnPropertyChange

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {

        UIManager.setLookAndFeel( "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(Map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(Map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(Map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(Map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
 

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Map().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton allPathTxt;
    private javax.swing.JRadioButton busRadio;
    private javax.swing.JRadioButton carRadio;
    private javax.swing.JButton change;
    private javax.swing.JTextField endInput;
    private javax.swing.JButton goBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField mailTxt;
    private javax.swing.JButton resetBtn;
    private javax.swing.JButton startBtn;
    private javax.swing.JTextField startInput;
    private javax.swing.JButton voiceBtn;
    private javax.swing.JRadioButton walkRadio;
    // End of variables declaration//GEN-END:variables

}
