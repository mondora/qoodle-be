package org.mondora.qoodle.response.template;

import com.google.gson.reflect.TypeToken;
import org.mondora.qoodle.QoodleElement;
import org.mondora.qoodle.response.utils.Json;

import java.util.ArrayList;
import java.util.List;

public class ElementResponse {

    public ArrayList<QoodleElement> elementList;

    public ElementResponse(String data) {

        if (data != "ACCESSO VIETATO") {
            this.elementList = new ArrayList<>(Json.fromJson(data, new TypeToken<List<QoodleElement>>() {}.getType()));
        } else {
            this.elementList = new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        return Json.toJson(this.elementList);
    }

}
