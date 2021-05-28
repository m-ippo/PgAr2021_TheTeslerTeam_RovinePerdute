package ttt.rovineperdute.contents;

import ttt.rovineperdute.contents.graph.Node;
import ttt.rovineperdute.io.ReadXML;
import ttt.rovineperdute.io.WriteXML;
import ttt.rovineperdute.io.elements.City;
import ttt.rovineperdute.io.elements.Link;
import ttt.rovineperdute.io.elements.Map;
import ttt.rovineperdute.trackresearch.TrackFinder;
import ttt.utils.console.input.ConsoleInput;
import ttt.utils.console.input.interfaces.Validator;
import ttt.utils.console.menu.Menu;
import ttt.utils.console.menu.utils.Pair;
import ttt.utils.console.output.GeneralFormatter;
import ttt.utils.xml.document.structure.Structure;
import ttt.utils.xml.document.structure.StructureModule;
import ttt.utils.xml.document.structure.exception.InvalidXMLFormat;
import ttt.utils.xml.document.structure.exception.UninitializedMandatoryProperty;
import ttt.utils.xml.document.structure.rules.Rules;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Optional;

public class MenuPrincipale {

    private Menu<Void> main;
    private final static StructureModule module = new StructureModule(Map.class, Rules.ONE)
                                .addModule(new StructureModule(City.class, Rules.ONE_TO_INFINITE)
                                        .addModule(new StructureModule(Link.class, Rules.ZERO_TO_INFINITE)));
    private boolean already_put = false;

    public MenuPrincipale(){
        main = new Menu<Void>("Andiamo alle rovine perdute!!!") {};
        init();
    }

    private void init(){
        main.addOption("Carica il file.", () -> {
            Optional<String> directory = ConsoleInput.getInstance().readString("Inserisci la cartella dove cercare il file : ",
                    false, null, new Validator<String>() {
                        @Override
                        public void validate(String value) throws IllegalArgumentException {
                            File temp = new File(value);
                            if(!temp.exists() || !temp.isDirectory()){
                                throw new IllegalArgumentException("Il percorso inserito non è valido.");
                            }
                        }
                    });
            File f = selectFile(new File(directory.get()));
            ReadXML reader = readFileXML(f);
            if(reader != null){
                reader.putCityInGraph();
                if(!already_put) {
                    main.addOption("Seleziona i nodi di partenza e fine", () -> {
                        Pair<Node, Node> first_and_last = getStartAndEndNode(reader);
                        main.removeOption(main.optionLookup("Stampa il file finale") + 1);
                        main.addOption("Stampa il file finale", () -> {
                            startProcess(first_and_last, reader);
                            return null;
                        });
                        return null;
                    });
                    already_put = true;

                }
            }

            return null;
        });
        main.paintMenu();
    }

    private File selectFile(File directory){
        GeneralFormatter.incrementIndents();
        Menu<File> menu = new Menu<File>("Seleziona il file.") {};
        menu.removeOption(1);
        File[] files = directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".xml");
            }
        });
        for(File f : files){
            menu.addOption(f.getName(), () -> {
                return f;
            });
        }
        File to_ret = menu.showAndWait();
        GeneralFormatter.decrementIndents();
        return to_ret;
    }

    private ReadXML readFileXML(File file){
        ReadXML reader = new ReadXML(file);
        Structure st = new Structure();
        st.setStructureModel(module);
        try {
            st.verify(reader.getDocument());
        } catch (UninitializedMandatoryProperty | InvalidXMLFormat exception) {
            GeneralFormatter.printOut("Il file inserito non ha la struttura corretta.", true, true);
            return null;
        }
        return reader;
    }

    private Pair<Node, Node> getStartAndEndNode(ReadXML reader){
        GeneralFormatter.incrementIndents();
        Menu<Pair<Node, Node>> node_menu = new Menu<Pair<Node, Node>>("Seleziona nodi di partenza e arrivo.") {};
        node_menu.removeOption(1);
        node_menu.addOption(reader.getStart().getCity().getCityName().toUpperCase() + " - " + reader.getEnd().getCity().getCityName().toUpperCase(), () -> {
            return new Pair<>(reader.getStart(), reader.getEnd());
        });
        node_menu.addOption("Inserisci i codice ID", () -> {
            Optional<Integer> first = ConsoleInput.getInstance().readInteger("Inserisci il codice della città di partenza : ", false, null,
                    value -> {
                        if(!(value >= 0 && value < reader.getNodes().size())){
                            throw new IllegalArgumentException("Il codice inserito non è tra quelli validi...");
                        }
                    });
            Optional<Integer> last = ConsoleInput.getInstance().readInteger("Inserisci il codice della città di arrivo : ", false, null,
                    value -> {
                        if(!(value >= 0 && value < reader.getNodes().size())){
                            throw new IllegalArgumentException("Il codice inserito non è tra quelli validi...");
                        } else if(value.equals(first.get())){
                            throw new IllegalArgumentException("Il codice inserito è lo stesso di partenza...");
                        }
                    });
            return new Pair<>(reader.getNodes().get(first.get()), reader.getNodes().get(last.get()));
        });
        Pair<Node, Node> to_ret = node_menu.showAndWait();
        GeneralFormatter.decrementIndents();
        return to_ret;
    }

    private void startProcess(Pair<Node, Node> first_and_last, ReadXML reader){
        TrackFinder finder_tonatiuh  = new TrackFinder(first_and_last.getKey(), first_and_last.getValue(), reader, (to, from) -> {
            return Math.sqrt(Math.pow(from.getCity().getX() - to.getCity().getX(), 2) + Math.pow(from.getCity().getY() - to.getCity().getY(), 2));
        });
        finder_tonatiuh.findTrack();
        TrackFinder finder_metztlih  = new TrackFinder(first_and_last.getKey(), first_and_last.getValue(), reader, (to, from) -> {
            return Math.abs(to.getCity().getH() - from.getCity().getH());
        });
        finder_metztlih.findTrack();
        WriteXML writer = new WriteXML(new File("output.xml"));
        writer.writeXML(finder_tonatiuh, finder_metztlih);
    }

}
