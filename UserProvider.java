import java.io.IOException;

public class UserProvider extends Provider{

    @Override
    protected void sendFindToServer(String toSend) {
        try {
            dataOutputStream.writeUTF("Check if password is correct");
            String[] toSendSplitted = toSend.split(";");
            dataOutputStream.writeUTF(toSendSplitted[0]);
            dataOutputStream.writeUTF(toSendSplitted[1]);
        } catch (IOException e) {
            System.out.println("Can't send product to server");
        }
    }

    @Override
    protected void sendAddToServer(String toSend) {
        try {
            dataOutputStream.writeUTF("Add user");
            dataOutputStream.writeUTF(toSend);
        } catch (IOException e) {
            System.out.println("Can't send product to server");
        }
    }

    protected void sendCurrentUserToServer(String username) {
        try {
            dataOutputStream.writeUTF("Current user");
            dataOutputStream.writeUTF(username);
        } catch (IOException e) {
            System.out.println("Can't send product to server");
        }
    }

    protected String sendCheckIfExists(String username){
        try {
            dataOutputStream.writeUTF("Check if username is occupied");
            dataOutputStream.writeUTF(username);
        } catch (IOException e) {
            System.out.println("Can't send product to server");
        }
        return receiveFromServer();
    }
}
