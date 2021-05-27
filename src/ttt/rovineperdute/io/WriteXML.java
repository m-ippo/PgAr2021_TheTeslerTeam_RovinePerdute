package ttt.rovineperdute.io;

import java.io.File;
import java.util.ArrayList;
import ttt.rovineperdute.contents.graph.Node;
import ttt.rovineperdute.io.elements.Route;
import ttt.rovineperdute.io.elements.Routes;
import ttt.utils.xml.document.XMLDocument;
import ttt.utils.xml.io.XMLWriter;

public class WriteXML {

    private final File file;

    public WriteXML(File file) {
        this.file = file;
    }

    public void writeXML(ArrayList<Node> path1, ArrayList<Node> path2, Double cost1, Double cost2) {
        XMLWriter writer = new XMLWriter(file);
        XMLDocument doc = new XMLDocument(file);

        Routes root = new Routes();
        Route team_1 = new Route();
        Route team_2 = new Route();
        team_1.setCities(path1.size());
        team_2.setCities(path2.size());
        team_1.setCost(cost1);
        team_2.setCost(cost2);
        team_1.setTeam("Tonathiu");
        team_2.setTeam("Metztli");

        path1.stream().forEachOrdered(n -> {
            team_1.addSubElement(n.getCity());
        });

        path2.stream().forEachOrdered(n -> {
            team_2.addSubElement(n.getCity());
        });

        root.addSubElement(team_1);
        root.addSubElement(team_2);
        doc.addSubElement(root);
        writer.writeDocument(doc, true);
    }

}
