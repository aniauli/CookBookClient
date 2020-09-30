import java.io.*;
import java.net.Socket;

abstract class Provider {

    private Socket socket;
    protected DataOutputStream dataOutputStream;
    protected DataInputStream dataInputStream;

    public Provider() {
        connectToServerSocket("localhost", 8080);
        createDataInputAndOutputStream();
    }

    private void connectToServerSocket(String host, Integer port) {
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDataInputAndOutputStream() {
        try {
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String findItem(String itemToFind){
        sendFindToServer(itemToFind);
        return receiveFromServer();
    }

    protected String addItem(String itemToAdd){
        sendAddToServer(itemToAdd);
        return receiveFromServer();
    }

    protected String receiveFromServer(){
        String receivedMessage;
        try {
            receivedMessage = dataInputStream.readUTF();
            return receivedMessage;
        } catch (IOException e) {
            System.out.println("Can't receive from server");
            return "Server Error";
        }
    }

    protected abstract void sendFindToServer(String toSend);
    protected abstract void sendAddToServer(String toSend);
}
