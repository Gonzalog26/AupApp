package eus.ehu.tta.aupapp.negocio;

import java.util.ArrayList;
import java.util.List;

import eus.ehu.tta.aupapp.R;
import eus.ehu.tta.aupapp.modelo.Test;

/**
 * Created by tta on 10/01/18.
 */

public class GeneradorTest implements InterfazTest {


    public GeneradorTest(){}

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

}
