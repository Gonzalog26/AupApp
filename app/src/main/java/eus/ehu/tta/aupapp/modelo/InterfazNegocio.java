package eus.ehu.tta.aupapp.modelo;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import eus.ehu.tta.aupapp.modelo.Event;
import eus.ehu.tta.aupapp.modelo.Test;

/**
 * Created by tta on 10/01/18.
 */

public interface InterfazNegocio {
        public List<Test> getTests();
        public List<Event> getEventos(int fechaInicial, int fechaFinal, String login) throws JSONException,IOException;
}
