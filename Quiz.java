import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

class Quiz {
    private String name;
    private ArrayList<Question> questions = new ArrayList();

    public Quiz() {
    }

    public ArrayList<Question> getQuestions() {
        return this.questions;
    }

    public void setName(String var1) {
        this.name = var1;
    }

    public String getName() {
        return this.name;
    }

    public void addQuestion(Question var1) {
        this.questions.add(var1);
    }

    public static Quiz loadFromFile(String var0) throws InvalidQuizFormatException {
        Scanner var1;
        try {
            var1 = new Scanner(new File(var0));
        } catch (FileNotFoundException var3) {
            System.out.println("Such a file does not exist!");
            return null;
        }

        Quiz var2 = readFromFile(var1);
        var2.setName(var0.substring(0, var0.indexOf(46)));
        return var2;
    }

    private static Quiz readFromFile(Scanner var0) throws InvalidQuizFormatException {
        Quiz var1 = new Quiz();

        try {
            while(var0.hasNext()) {
                String var4 = var0.nextLine();
                String var5 = var0.nextLine();
                FillIn var2;
                if (var0.hasNext()) {
                    String var6 = var0.nextLine();
                    if (var6.equals("")) {
                        var2 = new FillIn();
                        var2.setDescription(var4);
                        var2.setAnswer(var5);
                        var1.addQuestion(var2);
                    } else {
                        Test var3 = new Test();
                        var3.setDescription(var4);
                        var3.setAnswer(var5);
                        String[] var7 = new String[]{var6, var0.nextLine(), var0.nextLine()};
                        var3.setOptions(var7);
                        var1.addQuestion(var3);
                        if (var0.hasNext()) {
                            var0.nextLine();
                        }
                    }
                } else {
                    var2 = new FillIn();
                    var2.setDescription(var4);
                    var2.setAnswer(var5);
                    var1.addQuestion(var2);
                }
            }

            return var1;
        } catch (Exception var8) {
            throw new InvalidQuizFormatException(var8.getMessage());
        }
    }

    public String toString() {
        String var1 = "";
        var1 = var1 + "------------------\n";
        int var2 = 1;

        for(Iterator var3 = this.questions.iterator(); var3.hasNext(); ++var2) {
            Question var4 = (Question)var3.next();
            var1 = var1 + var2 + ". " + var4.toString() + "\n\n";
        }

        return var1;
    }

    public void start() {
        System.out.println("========================================================\n");
        System.out.printf("WELCOME TO \"%s\" QUIZ!\n", this.getName());
        System.out.println("________________________________________________________\n");
        int var1 = this.questions.size();
        int var2 = 0;
        int var3 = 1;
        Collections.shuffle(this.questions);
        Scanner var4 = new Scanner(System.in);

        for(Iterator var5 = this.questions.iterator(); var5.hasNext(); System.out.println("________________________________________________________\n")) {
            Question var6 = (Question)var5.next();
            System.out.println(var3++ + ". " + var6);
            System.out.println("---------------------------");
            if (var6 instanceof FillIn) {
                System.out.print("Type your answer: ");
                if (var4.nextLine().toLowerCase().equals(var6.getAnswer().toLowerCase())) {
                    System.out.println("Correct!");
                    ++var2;
                } else {
                    System.out.println("Incorrect!");
                }
            } else if (var6 instanceof Test) {
                System.out.print("Enter the correct choice: ");

                int var7;
                for(var7 = var4.nextLine().charAt(0) - 65; var7 < 0 || var7 >= this.questions.size(); var7 = var4.nextLine().charAt(0) - 65) {
                    System.out.print("Invalid choice! Try again (Ex: A, B, ...): ");
                }

                if (((Test)var6).getOptionAt(var7).equals(var6.getAnswer())) {
                    System.out.println("Correct!");
                    ++var2;
                } else {
                    System.out.println("Incorrect!");
                }
            }
        }

        System.out.printf("Correct Answers: %d/%d (%.1f%%)\n", var2, var1, (float)var2 / (float)var1 * 100.0F);
    }
}

