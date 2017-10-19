package org.mondora.qoodle.response.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.mondora.qoodle.QoodleElement;
import org.mondora.qoodle.response.utils.Json;

import java.util.ArrayList;
import java.util.List;

public class ElementResponse {

    public ArrayList<QoodleElement> elementList;

    public ElementResponse(String data) {
        Gson gson = new Gson();

        if (data != "ACCESSO VIETATO") {
            this.elementList = new ArrayList<>(gson.fromJson(data, new TypeToken<List<QoodleElement>>(){}.getType()));
        } else {
            this.elementList = new ArrayList<>();
        }
    }

        @Override
    public String toString() {
        return Json.toJson(this.elementList);
    }


}
