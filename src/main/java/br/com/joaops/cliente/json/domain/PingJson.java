package br.com.joaops.cliente.json.domain;

/**
 *
 * @author João Paulo
 */
public class PingJson {
    
    private String ping;
    
    public PingJson() {
        this.ping = "";
    }
    
    public PingJson(String content) {
        this.ping = content;
    }
    
    public String getPing() {
        return ping;
    }
    
    public void setPing(String ping) {
        this.ping = ping;
    }
    
}