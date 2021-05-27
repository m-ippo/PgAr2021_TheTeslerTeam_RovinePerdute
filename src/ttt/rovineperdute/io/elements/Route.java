package ttt.rovineperdute.io.elements;

import ttt.utils.engines.enums.FieldType;
import ttt.utils.engines.interfaces.EngineField;
import ttt.utils.xml.document.XMLElement;
import ttt.utils.xml.engine.annotations.Element;
import ttt.utils.xml.engine.annotations.Tag;

@Element(Name = "route")
public class Route extends XMLElement {

    @EngineField(FieldType = FieldType.READ_AND_WRITE)
    @Tag(Name = "team", ValueType = String.class)
    private String team;

    @EngineField(FieldType = FieldType.READ_AND_WRITE)
    @Tag(Name = "cost", ValueType = double.class)
    private double cost;

    @EngineField(FieldType = FieldType.READ_AND_WRITE)
    @Tag(Name = "cities", ValueType = int.class)
    private int cities;

    public Route() {
        super("route");
    }

    public void setCities(Integer cities) {
        this.cities = cities;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

}
