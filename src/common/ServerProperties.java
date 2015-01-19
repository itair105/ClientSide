package common;

/**
 * Created by user on 1/19/2015.
 */
public class ServerProperties {
    private String ip;
    private int port;

    public ServerProperties(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public ServerProperties() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
