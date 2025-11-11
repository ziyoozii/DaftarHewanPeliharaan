/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Model.Peliharaan;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class HomePageController implements Initializable {

    @FXML
    private Button btnTambah;
    @FXML
    private TextField txtNama;
    @FXML
    private TextField txtHewan;
    @FXML
    private TableView<Peliharaan> tblHewan;
    @FXML
    private DatePicker datePickerTanggal;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnHapus;
    @FXML
    private TableColumn<Peliharaan, String> tcNama;
    @FXML
    private TableColumn<Peliharaan, String> TcjHewan;
    @FXML
    private TableColumn<Peliharaan, String> TctAdopsi;

    /**
     * Initializes the controller class.
     */
    private ObservableList<Peliharaan> dataList = FXCollections.observableArrayList();
    private Peliharaan selectedItem;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        TcjHewan.setCellValueFactory(new PropertyValueFactory<>("jenisHewan"));
        TctAdopsi.setCellValueFactory(new PropertyValueFactory<>("tanggal"));

        // Set data list ke tabel
        tblHewan.setItems(dataList);

        // Klik baris di tabel untuk isi field
        tblHewan.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                selectedItem = newSel;
                txtNama.setText(newSel.getNama());
                txtHewan.setText(newSel.getJenisHewan());
            }
        });
    }    

    @FXML
    private void TambahData(ActionEvent event) {
        String nama = txtNama.getText();
        String jenis = txtHewan.getText();
        String tanggal = (datePickerTanggal.getValue() != null)
                ? datePickerTanggal.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                : "";

        if (nama.isEmpty() || jenis.isEmpty() || tanggal.isEmpty()) {
            showAlert("Input tidak lengkap", "Harap isi semua kolom!");
            return;
        }

        Peliharaan peliharaan = new Peliharaan(nama, jenis, tanggal);
        dataList.add(peliharaan);

        clearForm();
    }

    @FXML
    private void EditData(ActionEvent event) {
        Peliharaan selected = tblHewan.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Pilih Data", "Pilih data yang ingin diedit dulu!");
            return;
        }

        String namaBaru = txtNama.getText();
        String jenisBaru = txtHewan.getText();
        String tanggalBaru = (datePickerTanggal.getValue() != null)
                ? datePickerTanggal.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                : "";

        if (namaBaru.isEmpty() || jenisBaru.isEmpty() || tanggalBaru.isEmpty()) {
            showAlert("Input tidak lengkap", "Harap isi semua kolom sebelum edit!");
            return;
        }

        // Update data langsung di objek
        selected.setNama(namaBaru);
        selected.setJenisHewan(jenisBaru);
        selected.setTanggal(tanggalBaru);

        // Refresh tabel agar update langsung kelihatan
        tblHewan.refresh();

        clearForm();
    }
    @FXML
    private void HapusData(ActionEvent event) {
        Peliharaan selected = tblHewan.getSelectionModel().getSelectedItem();
        if (selected != null) {
            dataList.remove(selected);
            clearForm();
        } else {
            showAlert("Pilih Data", "Pilih data yang ingin dihapus!");
        }
    }
    private void clearForm() {
        txtNama.clear();
        txtHewan.clear();
        datePickerTanggal.setValue(null);
        tblHewan.getSelectionModel().clearSelection();
        selectedItem = null;
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
}
