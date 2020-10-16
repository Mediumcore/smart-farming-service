package com.sdpm.smart.farming.devicemgr.vo;


/**
 * @author shirukai
 */
public class CertificateVO {
    /**
     * 明文
     */
    private Object plaintext;

    private String ciphertext;

    public Object getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(Object plaintext) {
        this.plaintext = plaintext;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }
}
