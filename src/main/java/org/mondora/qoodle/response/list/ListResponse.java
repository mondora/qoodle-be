package org.mondora.qoodle.response.list;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.mondora.qoodle.response.utils.Json;

import java.util.ArrayList;
import java.util.List;


public class ListResponse {

    //public ListData list;
    public ArrayList<Qoodles> list;

    public ListResponse(String data) {
        Gson gson = new Gson();
        if(data != "ACCESSO VIETATO") {
            this.list = gson.fromJson(data, new TypeToken<List<Qoodles>>(){}.getType());
        }
    }


    @Override
    public String toString() {   return Json.toJson(this.list); }
}
