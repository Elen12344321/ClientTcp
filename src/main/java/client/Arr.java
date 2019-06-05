package client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Arr {

    public List<String> items;

    public Arr(){}

    public Arr(List<String> items){
        this.items = items;
    }

    public Arr(String[] items){
        this.items = new ArrayList<>(Arrays.asList(items));
    }
}
