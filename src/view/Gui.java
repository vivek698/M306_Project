package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        JButton exportCSV = new JButton("exportCSV");
        JButton exportJSON = new JButton("exportJSON");
        buttonPanel.add(exportCSV);
        buttonPanel.add(exportJSON);
        add(buttonPanel,BorderLayout.NORTH);

        // Wir lassen unseren Dialog anzeigen
        setVisible(true);

    }
    ActionListener exportActionListener=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };





}
