import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 18.11.12
 * Time: 1:34
 * To change this template use File | Settings | File Templates.
 */
public class NetworkInteraction implements Serializable {
    public String getText() {
        return text;
    }

    String text = "some text";

    public NetworkInteraction(String s) {
        text=s;
    }

    public boolean equals(Object interaction) {
        NetworkInteraction interaction1=(NetworkInteraction) interaction;
        if (interaction==null) return false;
        if (interaction1.getText().equals(this.getText())) return true;
        return false;
    }
}
