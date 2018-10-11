import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import textproc.TextProcessor;

public class BookReaderController extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		Scanner stopWords = new Scanner(new File("undantagsord.txt"));
		TextProcessor counter = new GeneralWordCounter(stopWords);
		Scanner s = new Scanner(new File("../lab3/nilsholg.txt"));
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning

		while (s.hasNext()) {
			String word = s.next().toLowerCase();
			counter.process(word);
		}
		s.close();

		ObservableList<Map.Entry<String, Integer>> words = FXCollections.observableArrayList(counter.getWords());
		ListView<Map.Entry<String, Integer>> listView = new ListView<Map.Entry<String, Integer>>(words);

		BorderPane root = new BorderPane();

		root.setCenter(listView);

		Scene scene = new Scene(root, 700, 500);
		primaryStage.setTitle("BookReader");
		primaryStage.setScene(scene);
		primaryStage.show();

		HBox hbox = new HBox();
		Button alphBtn = new Button("Alphabetic");
		Button freqBtn = new Button("Frequency");
		TextField textField = new TextField();
		Button searchBtn = new Button("Search");
		searchBtn.setDefaultButton(true);
		hbox.getChildren().addAll(alphBtn, freqBtn, textField, searchBtn);
		root.setBottom(hbox);

		Comparator<Map.Entry<String, Integer>> comparatorKey = new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o1.getKey().compareToIgnoreCase(o2.getKey());
			}
		};

		Comparator<Map.Entry<String, Integer>> comparatorValue = new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		};

		alphBtn.setOnAction((ActionEvent event) -> {
			Collections.sort(words, comparatorKey);
		});

		freqBtn.setOnAction((ActionEvent event) -> {
			Collections.sort(words, comparatorValue.reversed());
		});

		searchBtn.setOnAction((ActionEvent event) -> {
			String word = textField.getText();
			for (int i = 0; i < words.size(); i++) {
				if (words.get(i).getKey().equalsIgnoreCase(word)) {
					listView.scrollTo(i);
					listView.getSelectionModel().select(i);
				}
			}

		});

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
