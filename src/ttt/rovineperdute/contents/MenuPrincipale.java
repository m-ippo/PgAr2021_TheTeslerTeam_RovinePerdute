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
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class MenuPrincipale {

    private Menu<Void> main;
    private final static StructureModule module = new StructureModule(Map.class, Rules.ONE)
                                .addModule(new StructureModule(City.class, Rules.ONE_TO_INFINITE)
                                        .addModule(new StructureModule(Link.class, Rules.ZERO_TO_INFINITE)));
    private boolean already_put = false;
    private static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };

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
                            main.reset();
                            already_put = false;
                            init();
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

    /**
     * Ritorna il file tra quelli disonibili nella cartella.
     * @param directory La cartella in cui scegliere il file.
     * @return File scelto dalla cartella.
     */
    private File selectFile(File directory){
        GeneralFormatter.incrementIndents();
        Menu<File> menu = new Menu<File>("Seleziona il file.") {};
        menu.removeOption(1);
        File[] files = directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".xml");
            }
        });

        Arrays.stream(files).sorted(new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Long.compare(o1.length(), o2.length());
            }
        }).forEachOrdered(file -> {
            menu.addOption(file.getName(), () -> {
                return file;
            });
        });

        File to_ret = menu.showAndWait();
        GeneralFormatter.decrementIndents();
        return to_ret;
    }

    /**
     * Ritorna il lettore per leggere il file XML.
     * @param file File da leggere.
     * @return Lettore.
     */
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

    /**
     * Ritorna il nodo di partenza e di fine.
     * @param reader Lettore.
     * @return Coppia con i nodi di partenza e fine.
     */
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

    /**
     * Metodo che cerca i percorsi migliori per i veicoli e crea il file di output.
     * @param first_and_last Nodo di partenza e di fine.
     * @param reader Lettore.
     */
    private void startProcess(Pair<Node, Node> first_and_last, ReadXML reader){
        TrackFinder finder_tonatiuh  = new TrackFinder(first_and_last.getKey(), first_and_last.getValue(), reader, (to, from) -> {
            return Math.sqrt(Math.pow(from.getCity().getX() - to.getCity().getX(), 2) + Math.pow(from.getCity().getY() - to.getCity().getY(), 2));
        });
        TrackFinder finder_metztlih  = new TrackFinder(first_and_last.getKey(), first_and_last.getValue(), reader, (to, from) -> {
            return Math.abs(to.getCity().getH() - from.getCity().getH());
        });

        AtomicBoolean t_finito = new AtomicBoolean(false);
        AtomicBoolean m_finito = new AtomicBoolean(false);

        Thread thread_t = new Thread(){
            @Override
            public void run(){

                finder_tonatiuh.findTrack();
                t_finito.set(true);
            }
        };
        Thread thread_m = new Thread(){
            @Override
            public void run(){

                finder_metztlih.findTrack();
                m_finito.set(true);
            }
        };

        GeneralFormatter.printOut("Calcolando i percorsi ", false, false);

        Thread dots = new Thread(){
            @Override
            public void run(){
                while (!t_finito.get() || !m_finito.get()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    System.out.print(". ");
                }
                System.out.println();
            }
        };

        thread_t.start();
        thread_m.start();
        dots.start();

        try {
            thread_t.join();
            thread_m.join();
            dots.join();

            WriteXML writer = new WriteXML(selectOutputFile());

            writer.writeXML(finder_tonatiuh, finder_metztlih);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ritorna il file scelto per salvare i percorsi migliori.
     * @return File di output.
     */
    private File selectOutputFile(){
        Optional<String> str = ConsoleInput.getInstance().readString("Inserisci il nome del file di output : ",
                false, null, (string) -> {
            char[] to_char = string.toCharArray();
            for(int i = 0; i < string.length(); i++){
                for(int j = 0; j < to_char.length; j++){
                    if(string.charAt(i) == ILLEGAL_CHARACTERS[j]){
                        throw new IllegalArgumentException("Il nome non è valido.");
                    }
                }
            }
        });
        if(str.get().endsWith(".xml")){
            return new File(str.get());
        };
        return new File(str.get() + ".xml");
    }

}
