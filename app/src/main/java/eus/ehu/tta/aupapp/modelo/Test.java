package eus.ehu.tta.aupapp.modelo;


;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tta on 10/01/18.
 */

public class Test {

    private String enunciado;
    private String UrlVideo;
    private List<String> respuestas;
    private int respuestCorrecta;


    public Test(){
        this.enunciado = null;
        this.UrlVideo = null;
        respuestas = new ArrayList<>();
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getUrlVideo() {
        return UrlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        UrlVideo = urlVideo;
    }

    public List<String> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<String> respuestas) {
        this.respuestas = respuestas;
    }

    public int getRespuestCorrecta() {
        return respuestCorrecta;
    }

    public void setRespuestCorrecta(int respuestCorrecta) {
        this.respuestCorrecta = respuestCorrecta;
    }
}
