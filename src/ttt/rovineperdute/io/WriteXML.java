package ttt.rovineperdute.io;

import java.io.File;
import ttt.rovineperdute.io.elements.Route;
import ttt.rovineperdute.io.elements.Routes;
import ttt.rovineperdute.trackresearch.TrackFinder;
import ttt.utils.xml.document.XMLDocument;
import ttt.utils.xml.io.XMLWriter;

public class WriteXML {

    private final File file;

    public WriteXML(File file) {
        this.file = file;
    }

    public void writeXML(TrackFinder finder_tonatiuh, TrackFinder finder_metztlih) {
        XMLWriter writer = new XMLWriter(file);
        XMLDocument doc = new XMLDocument(file);

        Routes root = new Routes();
        Route team_1 = new Route();
        Route team_2 = new Route();
        team_1.setCities(finder_tonatiuh.getTrack().size());
        team_2.setCities(finder_metztlih.getTrack().size());
        team_1.setCost(finder_tonatiuh.getFinalDistance());
        team_2.setCost(finder_metztlih.getFinalDistance());
        team_1.setTeam("Tonathiu");
        team_2.setTeam("Metztli");

        finder_tonatiuh.getTrack().stream().forEachOrdered(n -> {
            team_1.addSubElement(n.getCity());
        });

        finder_metztlih.getTrack().stream().forEachOrdered(n -> {
            team_2.addSubElement(n.getCity());
        });

        root.addSubElement(team_1);
        root.addSubElement(team_2);
        doc.addSubElement(root);
        writer.writeDocument(doc, true);
    }

}
