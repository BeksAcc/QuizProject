import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizMaker extends Application {
    public QuizMaker() {
    }

    public static void main(String[] var0) throws Exception{
        launch(var0);
    }

    int counter = 0;
    int points = 0;
    Pane pane = new Pane();
    List<String> questionsAndAnswers;
    List<String> answers = new ArrayList<>();
    List<String> answerOfUser = new ArrayList<>();

    public void start(Stage primaryStage) {
        Button btn = new Button("Choose file");
        btn.setTranslateX(260);
        btn.setTranslateY(210);
        pane.getChildren().add(btn);
        Scene scene1 = new Scene(pane, 600, 500);
        btn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File fileFolder = fileChooser.showOpenDialog(primaryStage);
            if (fileFolder!=null) {
                try (BufferedReader reader = new BufferedReader(new FileReader(fileFolder))) {
                    String line;
                    String fileName = fileFolder.getName();

                    String line2 = Files.readAllLines(Paths.get(fileName)).get(2);
                    String line5 = Files.readAllLines(Paths.get(fileName)).get(5);

                    String fileContains="";

                    if (line5.isEmpty()==true || line2.isEmpty()==true){
                        while ((line = reader.readLine()) != null)
                            fileContains+=line+"\n";
                        String[] splitedQ = fileContains.split("\n\\s*\n");
                        questionsAndAnswers = Arrays.asList(splitedQ);

                        for (int j = 0; j < questionsAndAnswers.size(); j++) {
                            String strIs = questionsAndAnswers.get(j);
                            String getAns[] = strIs.split("\\r?\\n");
                            answers.add(getAns[1]);
                            answerOfUser.add(null);
                        }


                        pane.getChildren().clear();

                        if (isTest(questionsAndAnswers.get(counter))){
                            Test(questionsAndAnswers.get(counter));
                        }
                        else {
                            Fillin(questionsAndAnswers.get(counter));
                        }
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("QuizRead: Error");
                        alert.setHeaderText("InvalidQuizFormatException");
                        alert.setContentText("The file selected does not fit the requirements for a standard Quiz text file format...");
                        alert.showAndWait().ifPresent(rs -> alert.close());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Choose File");
                    alert.setHeaderText("File don't chosen");
                    alert.setContentText("Please choose file with txt format");
                    alert.showAndWait().ifPresent(rs -> alert.close());
                }
        });

        primaryStage.setTitle("QuizViewer"); // Set the stage title
        primaryStage.setScene(scene1); // Place the scene in the stage
        primaryStage.show();

    }

    public boolean isTest (String question){
        String lines[] = question.split("\\r?\\n");
        if (lines.length==5){
            return true;
        }
        else {
            return false;
        }
    }

    public void Test(String QA){

        pane.getChildren().clear();

        String lines[]=QA.split("\\r?\\n");
        List<String> QandA = Arrays.asList(lines);

        String Question = QandA.get(0);

        TextArea areaOfQuest = new TextArea(Question);
        areaOfQuest.setPrefSize(600,200);
        areaOfQuest.setEditable(false);



        ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton(QandA.get(1));
        rb1.setToggleGroup(group);
        rb1.setTranslateX(100);
        rb1.setTranslateY(270);
        RadioButton rb2 = new RadioButton(QandA.get(2));
        rb2.setToggleGroup(group);
        rb2.setTranslateX(100);
        rb2.setTranslateY(300);
        RadioButton rb3 = new RadioButton(QandA.get(3));
        rb3.setToggleGroup(group);
        rb3.setTranslateX(100);
        rb3.setTranslateY(330);
        RadioButton rb4 = new RadioButton(QandA.get(4));
        rb4.setToggleGroup(group);
        rb4.setTranslateX(100);
        rb4.setTranslateY(360);

        if(answerOfUser.get(counter) != null) {
            if (answerOfUser.get(counter).equals(rb1.getText())) {
                rb1.setSelected(true);
            } else if (answerOfUser.get(counter).equals(rb2.getText())) {
                rb2.setSelected(true);
            } else if (answerOfUser.get(counter).equals(rb3.getText())) {
                rb3.setSelected(true);
            } else if (answerOfUser.get(counter).equals(rb4.getText())) {
                rb4.setSelected(true);
            } else {
                rb1.setSelected(true);
            }
        }
        else {
            rb1.setSelected(true);
        }


        Text status = new Text();
        status.setText("Status: "+(counter+1)+"/"+questionsAndAnswers.size()+" questions.");
        status.setTranslateX(100);
        status.setTranslateY(460);

        RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();

        Button check = new Button("Check Answers");
        check.setTranslateX(350);
        check.setTranslateY(440);
        check.setPrefSize(200,40);
        check.setOnAction(event -> {
            points=0;
            String toogleGroupValue = selectedRadioButton.getText();
            answerOfUser.add(counter,toogleGroupValue);
            for (int i = 0;i<answers.size();i++){
                if(answers.get(i).equals(answerOfUser.get(i))){
                    points++;
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("QuizViewer: Results");
            alert.setHeaderText("Number of correct answers: "+points+"/"+questionsAndAnswers.size());
            alert.setContentText("You may try again.");
            alert.showAndWait().ifPresent(rs -> alert.close());
        });

        Button left = new Button("<<");
        left.setTranslateY(200);
        left.setPrefSize(90,30);
        left.setOnAction(event -> {

            for (int j = 0; j < questionsAndAnswers.size(); j++) {
                System.out.println(answers.get(j)+"\n"+answerOfUser.get(j));
            }

            String toogleGroupValue = selectedRadioButton.getText();
            answerOfUser.add(counter,toogleGroupValue);
            if (counter!=0) {
                counter--;
                if (isTest(questionsAndAnswers.get(counter))) {
                    Test(questionsAndAnswers.get(counter));
                } else {
                    Fillin(questionsAndAnswers.get(counter));
                }
            }
        });


        Button right = new Button(">>");
        right.setTranslateY(200);
        right.setTranslateX(510);
        right.setPrefSize(90,30);
        right.setOnAction(event -> {
            String toogleGroupValue = selectedRadioButton.getText();
            answerOfUser.add(counter,toogleGroupValue);
            if (counter!=(questionsAndAnswers.size()-1)) {
                counter++;
                if (isTest(questionsAndAnswers.get(counter))) {
                    Test(questionsAndAnswers.get(counter));
                } else {
                    Fillin(questionsAndAnswers.get(counter));
                }
            }
        });

        Text startQ = new Text("");
        if (counter==0){
            startQ = new Text ();
            startQ.setText("Start of quiz!");
            startQ.setTranslateY(480);
            startQ.setTranslateX(100);
        }
        else if(counter==questionsAndAnswers.size()-1){
            startQ = new Text ();
            startQ.setText("End of quiz!");
            startQ.setTranslateY(480);
            startQ.setTranslateX(100);
        }

        pane.getChildren().addAll(left, right, areaOfQuest,rb1,rb2,rb3,rb4,status,startQ,check);




    }

    public void Fillin(String QA){
        pane.getChildren().clear();

        String lines[]=QA.split("\\r?\\n");
        List<String> QandA = Arrays.asList(lines);

        String Question = QandA.get(0).replace("{blank}", "_____");

        TextArea areaOfQuest = new TextArea(Question);
        areaOfQuest.setPrefSize(600,200);
        areaOfQuest.setEditable(false);

        TextField answer = new TextField();
        answer.setTranslateX(100);
        answer.setTranslateY(280);
        answer.setPrefSize(400,40);

        if(answerOfUser.get(counter) != null) {
            answer.setText(answerOfUser.get(counter));
        }
        else{
            answer.setText("");
        }

        Button check = new Button("Check Answers");
        check.setTranslateX(350);
        check.setTranslateY(440);
        check.setPrefSize(200,40);
        check.setOnAction(event -> {
            points=0;
            answerOfUser.add(counter,answer.getText());
            for (int i = 0;i<questionsAndAnswers.size();i++){
                if(answers.get(i).equals(answerOfUser.get(i))){
                    points++;
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("QuizViewer: Results");
            alert.setHeaderText("Number of correct answers: "+points+"/"+questionsAndAnswers.size());
            alert.setContentText("You may try again.");
            alert.showAndWait().ifPresent(rs -> alert.close());
        });

        Button left = new Button("<<");
        left.setTranslateY(200);
        left.setPrefSize(90,30);
        left.setOnAction(event -> {
            answerOfUser.add(counter,answer.getText());
            if (counter!=0) {
                counter--;
                if (isTest(questionsAndAnswers.get(counter))) {
                    Test(questionsAndAnswers.get(counter));
                } else {
                    Fillin(questionsAndAnswers.get(counter));
                }
            }
        });

        Button right = new Button(">>");
        right.setTranslateY(200);
        right.setTranslateX(510);
        right.setPrefSize(90,30);
        right.setOnAction(event -> {
            answerOfUser.add(counter,answer.getText());
            if (counter!=(questionsAndAnswers.size()-1)) {
                counter++;
                if (isTest(questionsAndAnswers.get(counter))) {
                    Test(questionsAndAnswers.get(counter));
                } else {
                    Fillin(questionsAndAnswers.get(counter));
                }
            }
        });




        Text status = new Text();
        status.setText("Status: "+(counter+1)+"/"+questionsAndAnswers.size()+" questions.");
        status.setTranslateX(100);
        status.setTranslateY(460);

        Text startQ = new Text("");
        if (counter==0){
            startQ = new Text ();
            startQ.setText("Start of quiz!");
            startQ.setTranslateY(480);
            startQ.setTranslateX(100);
        }
        else if(counter==questionsAndAnswers.size()-1){
            startQ = new Text ();
            startQ.setText("End of quiz!");
            startQ.setTranslateY(480);
            startQ.setTranslateX(100);
        }

        pane.getChildren().addAll(left, right, status,startQ,check,answer,areaOfQuest);
    }
}