/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.cliente.controller;

import br.com.joaops.cliente.Main;
import br.com.joaops.cliente.dto.PessoaDto;
import br.com.joaops.cliente.repository.PessoaDtoRepository;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

/**
 * 
 * @author João Paulo
 */
@Controller
public class PessoaLayoutController implements Initializable {
    
    private static final Logger LOGGER = LogManager.getLogger(PessoaLayoutController.class);
    
    private Main main;
    
    @FXML
    private TableView<PessoaDto> tableViewPessoa;
    
    @FXML
    private TableColumn<PessoaDto, Long> tableColumnId;
    
    @FXML
    private TableColumn<PessoaDto, String> tableColumnNome;
    
    @FXML
    private TableColumn<PessoaDto, Date> tableColumnNascimento;
    
    @Autowired
    private PessoaDtoRepository pessoaRepository;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTableViewPessoa();
    }
    
    public void setMain(Main main) {
        this.main = main;
    }
    
    public void carregarTableViewPessoa() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnNascimento.setCellValueFactory(new PropertyValueFactory<>("nascimento"));
        tableColumnNascimento.setCellFactory(new PessoaLayoutController.ColumnFormatter<>(new SimpleDateFormat("dd/MM/yyyy")));
        ObservableList<PessoaDto> observableList = FXCollections.observableArrayList(pessoaRepository.findAll());
        tableViewPessoa.setItems(observableList);
    }
    
    private class ColumnFormatter<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
        
        private final SimpleDateFormat format;
        
        public ColumnFormatter(SimpleDateFormat format) {
            super();
            this.format = format;
        }
        
        @Override
        public TableCell<S, T> call(TableColumn<S, T> arg0) {
            return new TableCell<S, T>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        Date d = (Date) item;
                        String val = format.format(d);
                        setGraphic(new Label(val));
                    }
                }
            };
        }
    }
    
}