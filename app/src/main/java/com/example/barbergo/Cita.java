package com.example.barbergo;

import java.util.HashMap;
import java.util.Map;

public class Cita {

    public String citaId;
    public String clienteId;
    public long date;
    public String estilistaId;

    public Cita(String citaId, String clienteId, long date, String estilistaId){
        this.citaId = citaId;
        this.date = date;
        this.clienteId = clienteId;
        this.estilistaId =estilistaId;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("citaId", citaId);
        result.put("date", date);
        result.put("clienteId", clienteId);
        result.put("estilistaId", estilistaId);
        return result;
    }
}
