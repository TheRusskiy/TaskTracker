import java.io.Serializable;
import java.util.List;

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
    private boolean saveToServer=false;
    private boolean loadFromServer=true;
    private boolean isCool=true;
    private boolean createNewUser=false;
    private boolean getAvailableTrees=false;
    private String treeName;
    private List<String> treeNames;

    public List<String> getTreeNames() {
        return treeNames;
    }

    public void setTreeNames(List<String> treeNames) {
        this.treeNames = treeNames;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public boolean isCreateNewUser() {
        return createNewUser;
    }

    public void createNewUser() {
        createNewUser = true;
    }

    public void setGetAvailableTrees() {
        getAvailableTrees = true;
    }
    public boolean isGetAvailableTrees() {
        return getAvailableTrees;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCool() {
        return isCool;
    }

    public void setCool() {
        isCool = true;
    }

    public void setNotCool() {
        isCool = false;
    }

    public boolean isSaveToServer() {
        return saveToServer;
    }

    public void setSaveToServer() {
        saveToServer = true;
        loadFromServer=false;
    }

    public boolean isLoadFromServer() {
        return loadFromServer;
    }

    public void setLoadFromServer() {
        loadFromServer = true;
        saveToServer=false;
    }

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
