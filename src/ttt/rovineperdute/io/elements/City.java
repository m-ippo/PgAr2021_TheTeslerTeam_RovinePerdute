package ttt.rovineperdute.io.elements;

import ttt.utils.engines.enums.FieldType;
import ttt.utils.engines.interfaces.EngineField;
import ttt.utils.xml.document.XMLElement;
import ttt.utils.xml.engine.annotations.Element;
import ttt.utils.xml.engine.annotations.Tag;

@Element(Name = "city", IgnoreSubElementsOnWrite = true)
public class City extends XMLElement {

    @EngineField(FieldType = FieldType.READ)
    @Tag(Name = "x", ValueType = int.class)
    private int x;
    
    @EngineField(FieldType = FieldType.READ)                   // ***provare con short***
    @Tag(Name = "y", ValueType = int.class)
    private int y;
    
    @EngineField(FieldType = FieldType.READ)
    @Tag(Name = "h", ValueType = int.class)
    private int h;
    
    @EngineField(FieldType = FieldType.READ_AND_WRITE)
    @Tag(Name = "name", ValueType = String.class)
    private String name;
    
    @EngineField(FieldType = FieldType.READ_AND_WRITE)
    @Tag(Name = "id", ValueType = int.class)
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

    public String getCityName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
