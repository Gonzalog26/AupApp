package eus.ehu.tta.aupapp.negocio;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import eus.ehu.tta.aupapp.modelo.Event;
import eus.ehu.tta.aupapp.modelo.Test;
import eus.ehu.tta.aupapp.modelo.User;

/**
 * Created by tta on 10/01/18.
 */

public class ServidorNegocio implements InterfazNegocio {

    private static final String baseUrl = "http://u017633.ehu.eus:28080/AupaAppRest";
    //private static final String baseUrl = "http://192.168.0.163:8080/AupaAppRest";

    private static ServidorNegocio servidorNegocio = null;
    private ClienteRest clienteRest;

    public ServidorNegocio(){
        clienteRest = new ClienteRest(baseUrl);
    }

    public static ServidorNegocio getInstancia(){
        if(servidorNegocio==null){
            servidorNegocio = new ServidorNegocio();
        }
        return servidorNegocio;
    }

    public List<Test> getTests(){

        List<Test> tests = new ArrayList<>();

        Test test = new Test();
        test.setEnunciado("Pregunta 1: Barkatu, ____ liburutegira?");
        test.getRespuestas().add("Kaixo");
        test.getRespuestas().add("Nola heldu naiteke");
        test.getRespuestas().add("Zenbat balio du");
        test.setRespuestCorrecta(1);
        test.setUrlVideo("https://dl.dropboxusercontent.com/s/90up4kpbyia2hwz/1.mp4?dl=0");
        tests.add(test);

        test = new Test();
        test.setEnunciado("Pregunta 2: Lotara noa, ____");
        test.getRespuestas().add("Gabon");
        test.getRespuestas().add("Kaixo");
        test.getRespuestas().add("Zelan zaude");
        test.setRespuestCorrecta(0);
        test.setUrlVideo("https://dl.dropboxusercontent.com/s/o3dqfr9lzjwr8s3/2.mp4?dl=0");
        tests.add(test);

        test = new Test();
        test.setEnunciado("Pregunta 3: Itzartu berri naiz ____");
        test.getRespuestas().add("Zelan zaude");
        test.getRespuestas().add("Egun on");
        test.getRespuestas().add("Zenbat balio du?");
        test.setRespuestCorrecta(1);
        test.setUrlVideo("https://dl.dropboxusercontent.com/s/4e3pftb45zbi9tn/3.mp4?dl=0");
        tests.add(test);

        test = new Test();
        test.setEnunciado("Pregunta 4: Banoa, _____!");
        test.getRespuestas().add("Agur");
        test.getRespuestas().add("Kaixo");
        test.getRespuestas().add("Nola heldu naiteke?");
        test.setRespuestCorrecta(0);
        test.setUrlVideo("https://dl.dropboxusercontent.com/s/yk6co6tklge7isi/4.mp4?dl=0");
        tests.add(test);

        test = new Test();
        test.setEnunciado("Pregunta 5: Kaixo, ____? Ni ondo.");
        test.getRespuestas().add("Zelan zaude");
        test.getRespuestas().add("Zenbat balio du?");
        test.getRespuestas().add("Nola heldu naiteke?");
        test.setRespuestCorrecta(0);
        test.setUrlVideo("https://dl.dropboxusercontent.com/s/r8toin22z1mk2gp/5.mp4?dl=0");
        tests.add(test);

        test = new Test();
        test.setEnunciado("Pregunta 6: Pintxo honek, _____!");
        test.getRespuestas().add("Zenbat balio du?");
        test.getRespuestas().add("Nola hel naiteke?");
        test.getRespuestas().add("Zer moduz?");
        test.setRespuestCorrecta(0);
        test.setUrlVideo("https://dl.dropboxusercontent.com/s/2qev9l7jibxjg9a/6.mp4?dl=0");
        tests.add(test);

        return tests;

    }

    public String registro(String nombre, String papellido, String sapellido, String password) throws JSONException, IOException {

        String login = null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombre",nombre);
        jsonObject.put("apellido1",papellido);
        jsonObject.put("apellido2",sapellido);
        jsonObject.put("passwd",password);

        login = clienteRest.postJson(jsonObject,"rest/App/addUser");


        return login;

    }

    public int addEventos(String nombre, String descripcion, int fecha, int hora, String foto, String ubicacion, String login, InputStream is) throws JSONException, IOException {


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombre",nombre);
        jsonObject.put("fecha",fecha);
        jsonObject.put("hora",hora);
        jsonObject.put("ubicacion",ubicacion);
        jsonObject.put("descripcion",descripcion);
        jsonObject.put("login",login);
        jsonObject.put("foto",foto);

        clienteRest.postFile("rest/App/uploadFile",is,foto);

        return clienteRest.postJson2(jsonObject,"rest/App/addEvent");


    }

    public User autenticathion(String login, String password) throws IOException,JSONException{

        User user = new User();
        String requestCode = clienteRest.getString(String.format("rest/App/requestLogin?login=%s&passwd=%s",login,password));

        if(requestCode.compareToIgnoreCase("OK")==0){

                JSONObject jsonObject = clienteRest.getJson(String.format("rest/App/requestUser?login=%s",login));
                user.setNombre(jsonObject.getString("nombre"));
                user.setPapellido(jsonObject.getString("apellido1"));
                user.setSapellido(jsonObject.getString("apellido2"));

        }
        else{
            user.setNombre(null);
        }

        return user;

    }

    public List<Event> getEventos(int fechaInicial, int fechaFinal,String login)throws JSONException,IOException{

        List<Event> events = new ArrayList<>();
        Event event;
        JSONArray jsonArray = clienteRest.getJsonArray("rest/App/requestEvents?fechaInicial="+fechaInicial+"&fechaFinal="+fechaFinal+"&login="+login);


        for(int i=0;i<jsonArray.length();i++){

            event= new Event();
            JSONObject item = jsonArray.getJSONObject(i);
            event.setNombre(item.getString("nombre"));
            event.setDescripcion(item.getString("descripcion"));
            event.setUbicacion(item.getString("ubicacion"));
            event.setFecha(item.getInt("fecha"));
            event.setHora(item.getInt("hora"));
            event.setFoto(item.getString("foto"));

            events.add(event);
        }

        return events;

    }


}
