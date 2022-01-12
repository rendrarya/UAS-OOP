import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.css.converter.InsetsConverter;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    Stage windowStage;
    TableView<kampus> table;
    TableView<kampus> tableView = new TableView<kampus>();
    TextField idInput, namaInput, nimInput, alamatInput, kodeInput;

    @Override
    public void start(Stage stage) {

        windowStage = stage;
        windowStage.setTitle("DataBase - kampus");
       
        TableColumn<kampus, String> columnID = new TableColumn<>("ID");
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<kampus, String> columnNama = new TableColumn<>("Nama");
        columnNama.setCellValueFactory(new PropertyValueFactory<>("Nama"));

        TableColumn<kampus, String> columnNim = new TableColumn<>("Nim");
        columnNim.setCellValueFactory(new PropertyValueFactory<>("NIM"));

        TableColumn<kampus, String> columnAlamat = new TableColumn<>("Alamat");
        columnAlamat.setCellValueFactory(new PropertyValueFactory<>("Alamat"));

        TableColumn<kampus, String> columnKode = new TableColumn<>("Kode");
        columnKode.setCellValueFactory(new PropertyValueFactory<>("Kode"));
        
        tableView.getColumns().add(columnID);
        tableView.getColumns().add(columnNama);
        tableView.getColumns().add(columnNim);
        tableView.getColumns().add(columnAlamat);
        tableView.getColumns().add(columnKode);

        idInput = new TextField();
        idInput.setPromptText("id");
        idInput.setMinWidth(10);

        namaInput = new TextField();
        namaInput.setPromptText("Nama");
        namaInput.setMinWidth(20);

        nimInput = new TextField();
        nimInput.setPromptText("NIM");
        nimInput.setMinWidth(50);

        alamatInput = new TextField();
        alamatInput.setPromptText("Alamat");
        alamatInput.setMinWidth(20);

        kodeInput = new TextField();
        kodeInput.setPromptText("Kode");
        kodeInput.setMinWidth(20);


        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editButtonClicked());
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateButtonClicked());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(idInput, namaInput, nimInput, alamatInput, kodeInput, editButton, updateButton, deleteButton);

        String url = "jdbc:mysql://localhost:3306/kampus";
        String user = "root";
        String pass = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet record = stmt.executeQuery("select*from data");

            while (record.next()) {
                tableView.getItems().add(new kampus(record.getInt("id"), record.getString("nama"), record.getString("nim"), record.getString("alamat"), record.getString("kode")));
            }
        }
        catch (SQLException e) {
            System.out.print("koneksi gagal");
        }
        

        VBox vbox = new VBox(tableView);
        vbox.getChildren().addAll(hBox);

        Scene scene = new Scene(vbox);

        stage.setScene(scene);
        stage.show();

    }




    //Update Button Clicked
    private void updateButtonClicked(){

        Database db = new Database();
                try {
                    Statement state = db.conn.createStatement();
                    String sql = "insert into data SET nama='%s', nim='%s', alamat='%s', kode='%s'";
                    sql = String.format(sql, namaInput.getText(), nimInput.getText(), alamatInput.getText(), kodeInput.getText());
                    state.execute(sql);
                    // idInput.clear();
                    namaInput.clear();
                    nimInput.clear();
                    alamatInput.clear();
                    kodeInput.clear();
                    loadData();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            
        };


    //Edit Button Clicked
    private void editButtonClicked(){

        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "update data set nama = '%s' ,  nim = '%s' , alamat = '%s' WHERE kode ='%s'";
            sql = String.format(sql, namaInput.getText(), nimInput.getText(), alamatInput.getText(), kodeInput.getText());
            state.execute(sql);
            namaInput.clear();
            nimInput.clear();
            alamatInput.clear();
            kodeInput.clear();
            loadData();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Delete button Clicked
    private void deleteButtonClicked(){

        Database db = new Database();
        try{
            Statement state = db.conn.createStatement();
            String sql = "delete from data where kode ='%s';";
            sql = String.format(sql, kodeInput.getText());
            state.execute(sql);
            kodeInput.clear();
            loadData();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    

    public static void main(String[] args) {
        launch();
    }

    private void loadData() {
        Statement stmt;
        try{
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from data");
            tableView.getItems().clear();
            while(rs.next()){
                tableView.getItems().add(new kampus(rs.getInt("id"), rs.getString("nama"),rs.getString("nim"),rs.getString("alamat"), rs.getString("kode")));
            }

            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
}