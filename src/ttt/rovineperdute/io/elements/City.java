package ttt.rovineperdute.io.elements;

import ttt.utils.engines.enums.FieldType;
import ttt.utils.engines.interfaces.EngineField;
import ttt.utils.xml.document.XMLElement;
import ttt.utils.xml.engine.annotations.Element;
import ttt.utils.xml.engine.annotations.Tag;

@Element(Name = "city")
public class City extends XMLElement {

    @Tag(Name = "x", ValueType = int.class)
    @EngineField(FieldType = FieldType.READ_AND_WRITE)
    private int x;
    @Tag(Name = "y", ValueType = int.class)
    @EngineField(FieldType = FieldType.READ_AND_WRITE)
    private int y;
    @Tag(Name = "h", ValueType = int.class)
    @EngineField(FieldType = FieldType.READ_AND_WRITE)
    private int h;
    @Tag(Name = "name", ValueType = String.class)
    @EngineField(FieldType = FieldType.READ_AND_WRITE)
    private String name;
    @Tag(Name = "id", ValueType = int.class)
    @EngineField(FieldType = FieldType.READ_AND_WRITE)
    private int id;

    public City() {
        super("city");
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getH() {
        return h;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
