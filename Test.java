import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class Test extends Question {
    private String[] options = new String[4];
    private final int numOfOptions = 4;
    private ArrayList<Character> labels = new ArrayList();

    public Test() {
        byte var1 = 65;

        for(int var2 = 0; var2 < 4; ++var2) {
            this.labels.add((char)(var1 + var2));
        }

    }

    public String getOptionAt(int var1) {
        return this.options[var1];
    }

    public void setOptions(String[] var1) {
        System.arraycopy(var1, 0, this.options, 0, 3);
    }

    public String toString() {
        String var1 = this.getDescription() + "\n";
        List var2 = Arrays.asList(this.options);
        var2.set(3, this.getAnswer());
        Collections.shuffle(var2);
        int var3 = 1;

        String var5;
        for(Iterator var4 = var2.iterator(); var4.hasNext(); var1 = var1 + this.labels.get(var3 - 1) + ") " + var5 + (var3++ < 4 ? "\n" : "")) {
            var5 = (String)var4.next();
        }

        return var1;
    }
}
