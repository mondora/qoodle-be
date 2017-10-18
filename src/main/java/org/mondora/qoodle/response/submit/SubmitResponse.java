package org.mondora.qoodle.response.submit;

import org.mondora.qoodle.response.utils.Json;

public class SubmitResponse {


    public String message;

    public SubmitResponse(String m)
    {
            this.message = m;
    }

    @Override
    public String toString()
    {
        return this.message;
    }

}
