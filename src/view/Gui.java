package view;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Gui extends JFrame {

    public Gui() throws HeadlessException {
        setTitle("Projektdemo");
        setSize(1000,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Hier erzeugen wir unsere JPanels
        VerbrauchsDiagrammPanel verbrauchsDiagrammPanel = new VerbrauchsDiagrammPanel();
        ZaehlerstandsDiagrammPanel zaelerstandsDiagrammPanel = new ZaehlerstandsDiagrammPanel();

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        // Erzeugung eines JTabbedPane-Objektes
        JTabbedPane tabpane = new JTabbedPane
                (JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT );

        // Hier werden die JPanels als Registerkarten hinzugefügt
        tabpane.addTab("Verbrauchsdiagramm", verbrauchsDiagrammPanel);
        tabpane.addTab("Zählerstandsdiagramm", zaelerstandsDiagrammPanel);

        // JTabbedPane wird unserem Dialog hinzugefügt
        add(tabpane,BorderLayout.CENTER);

        JPanel buttonPanel=new JPanel();

        JButton exportCSV_id735 = new JButton("exportCSV Einspeisung");
        JButton exportCSV_id742 = new JButton("exportCSV Bezug");
        JButton exportJSON = new JButton("exportJSON");

        exportCSV_id735.addActionListener(e -> copyToDownloads("ID_Einspeisung.csv"));
        exportCSV_id742.addActionListener(e -> copyToDownloads("ID_Bezug.csv"));
        exportJSON.addActionListener(e -> copyToDownloads("messwerte.json"));

        buttonPanel.add(exportCSV_id735);
        buttonPanel.add(exportCSV_id742);
        buttonPanel.add(exportJSON);

        add(buttonPanel,BorderLayout.NORTH);

        // Wir lassen unseren Dialog anzeigen
        setVisible(true);

    }


    public static void main(String[] args) {
        copyToDownloads("ID_Einspeisung.csv");
    }

    public static void copyToDownloads(String fileName) {
        try {

            String home = System.getProperty("user.home");

            InputStream in = null;
            in = new FileInputStream(new File("data/" + fileName));

            OutputStream out = new FileOutputStream(new File(home + "/Downloads/" + fileName));


            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
