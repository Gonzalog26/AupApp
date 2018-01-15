package eus.ehu.tta.aupapp.negocio;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eus.ehu.tta.aupapp.modelo.Test;
import eus.ehu.tta.aupapp.modelo.User;

/**
 * Created by tta on 10/01/18.
 */

public class ServidorNegocio implements InterfazNegocio {

    private static final String baseUrl = "http://u017633.ehu.eus:28080/AupaAppRest";
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


}