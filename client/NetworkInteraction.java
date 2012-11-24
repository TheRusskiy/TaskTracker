import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 18.11.12
 * Time: 1:34
 * To change this template use File | Settings | File Templates.
 */
public class NetworkInteraction implements Serializable {
    private String text = "some text"; //arbitrary message
    private String login;
    private byte[] encryptedPassword;
    private TaskTree tree=null;

    public String getPassword() {
        return CipherDriver.decrypt(encryptedPassword, CipherDriver.getSharedKey());
    }

    public void setPassword(String password) {
        encryptedPassword=CipherDriver.encrypt(password, CipherDriver.getSharedKey());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getText() {
        return text;
    }

    public TaskTree getTree() {
        return tree;
    }

    public void setTree(TaskTree tree) {
        this.tree = tree;
    }

    public NetworkInteraction(String s) {
        text=s;
    }

    public NetworkInteraction() {
    }

    public boolean equals(Object interaction) {
        NetworkInteraction interaction1=(NetworkInteraction) interaction;
        if (interaction==null) return false;
        if (interaction1.getText().equals(this.getText())) return true;
        return false;
    }
}
