package gm.zona_fit;

import gm.zona_fit.gui.ZonaFitForma;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

//comenta aqui
@SpringBootApplication


public class ZonaFitSwing {
    public static void main(String[] args) {
//        modo escuro
//        FlatDraculaLaf.setup();
        ConfigurableApplicationContext contextSpring = new SpringApplicationBuilder(ZonaFitSwing.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);

        SwingUtilities.invokeLater(()-> {
            ZonaFitForma zonaFitForma = contextSpring.getBean(ZonaFitForma.class);
            zonaFitForma.setVisible(true);
        });
    }
}
