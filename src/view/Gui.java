package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Gui extends JFrame {

    public Gui() throws HeadlessException {
        setTitle("Projektdemo");
        setSize(800,1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Hier erzeugen wir unsere JPanels
        VerbrauchsDiagrammPanel verbrauchsDiagrammPanel = new VerbrauchsDiagrammPanel();
        ZaelerstandsDiagrammPanel zaelerstandsDiagrammPanel = new ZaelerstandsDiagrammPanel();

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

        JButton exportCSV_id735 = new JButton("exportCSV ID735");
        JButton exportCSV_id742 = new JButton("exportCSV ID742");
        JButton exportJSON = new JButton("exportJSON");

        exportCSV_id735.addActionListener(e -> copyToDownloads("ID_735.csv"));
        exportCSV_id742.addActionListener(e -> copyToDownloads("ID_742.csv"));
        exportJSON.addActionListener(e -> copyToDownloads("messwerte.json"));

        buttonPanel.add(exportCSV_id735);
        buttonPanel.add(exportCSV_id742);
        buttonPanel.add(exportJSON);

        add(buttonPanel,BorderLayout.NORTH);

        // Wir lassen unseren Dialog anzeigen
        setVisible(true);

    }


    public static void main(String[] args) {
        copyToDownloads("ID_735.csv");
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
