package ttt.rovineperdute.io.elements;

import ttt.utils.engines.enums.FieldType;
import ttt.utils.engines.interfaces.EngineField;
import ttt.utils.xml.document.XMLElement;
import ttt.utils.xml.engine.annotations.Element;
import ttt.utils.xml.engine.annotations.Tag;

@Element(Name = "link")
public class Link extends XMLElement {

    @Tag(Name = "to", ValueType = int.class)
    @EngineField(FieldType = FieldType.READ_AND_WRITE)
    private int id_to; // ***provare con short***

    public Link() {
        super("link");
    }
}
