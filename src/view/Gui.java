package view;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    public Gui() throws HeadlessException {

        setTitle("Projektdemo");
        setSize(800,600);

        // Hier erzeugen wir unsere JPanels
        VerbrauchsDiagrammPanel verbrauchsDiagrammPanel = new VerbrauchsDiagrammPanel();
        JPanel zaelerstandsDiagrammPanel = new JPanel();

        // Hier setzen wir die Hintergrundfarben für die JPanels
        verbrauchsDiagrammPanel.setBackground(Color.RED);
        zaelerstandsDiagrammPanel.setBackground(Color.BLUE);


        // Erzeugung eines JTabbedPane-Objektes
        JTabbedPane tabpane = new JTabbedPane
                (JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT );

        // Hier werden die JPanels als Registerkarten hinzugefügt
        tabpane.addTab("Verbrauchsdiagramm", verbrauchsDiagrammPanel);
        tabpane.addTab("Zählerstandsdiagramm", zaelerstandsDiagrammPanel);

        // JTabbedPane wird unserem Dialog hinzugefügt
        add(tabpane);

        // Wir lassen unseren Dialog anzeigen
        setVisible(true);

    }



}
