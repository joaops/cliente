package br.com.joaops.cliente;

import br.com.joaops.cliente.config.ApplicationConfig;
import br.com.joaops.cliente.config.MapperConfig;
import br.com.joaops.cliente.config.StompSessionConfig;
import br.com.joaops.cliente.controller.HomeLayoutController;
import br.com.joaops.cliente.controller.RootLayoutController;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.messaging.simp.stomp.StompSession;

/**
 *
 * @author João Paulo
 */
public class Main extends Application {
    
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    private AnnotationConfigApplicationContext context;
    
    @Override
    public void init() throws Exception {
        LOGGER.debug("Configurando o Spring.");
        context = new AnnotationConfigApplicationContext();
        context.register(ApplicationConfig.class);
        context.register(MapperConfig.class);
        context.register(StompSessionConfig.class);
        context.refresh();
    }
    
    @Override
    public void stop() throws Exception {
        LOGGER.debug("Finalizando Aplicação.");
        try {
            StompSession stompSession = context.getBean(StompSession.class);
            if (stompSession != null) {
                if (stompSession.isConnected()) {
                    LOGGER.debug("Desconectando da Sessão.");
                    stompSession.disconnect();
                } else {
                    LOGGER.debug("A Sessão não está conectada.");
                }
            } else {
                LOGGER.debug("O Bean da Sessão é Nulo.");
            }
        } catch (BeansException e) {
            LOGGER.error("ERRO ao Desconectar Sessão.");
        }
        context.close();
        System.exit(0); // Forçar Saída
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/favicon.png")));
        this.primaryStage.setTitle("Cliente");
        showRootLayout();
        showHomeLayout();
    }
    
    /**
     * Inicializa o root layout (layout base).
     */
    public void showRootLayout() {
        try {
            // Carrega o root layout do arquivo fxml.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootLayout.fxml"));
            loader.setControllerFactory(context::getBean);
            rootLayout = (BorderPane) loader.load();
            // Mostra a scene (cena) contendo o root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            RootLayoutController controller = loader.getController();
            controller.setMain(this);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("ERRO: " + e.toString());
        }
    }
    
    public void showHomeLayout() {
        try {
            // Carrega o person overview.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomeLayout.fxml"));
            loader.setControllerFactory(context::getBean);
            AnchorPane pane = (AnchorPane) loader.load();
            // Define o pane dentro do root layout.
            rootLayout.setCenter(pane);
            // Fornecer uma instância de acesso ao Main.
            HomeLayoutController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            System.err.println("ERRO: " + e.toString());
        }
    }
    
    public static void main(String[] args) {
        LOGGER.info("Iniciando Aplicação");
        launch(args);
    }
    
}