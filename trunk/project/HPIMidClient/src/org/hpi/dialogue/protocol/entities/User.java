/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpi.dialogue.protocol.entities;

/**
 *
 * @author carrefour
 */
public class User {
    
    private String					nickname;
    private String					passphrase;

    public User(String nickname, String passphrase) {
        if (nickname == null || nickname.length() < 1 || passphrase == null || passphrase.length() < 1) {
            throw new IllegalArgumentException("Parameter cann't be null or empty.");
        }
        this.nickname = nickname;
        this.passphrase = passphrase;
    }

    // GETTERS AND SETTERS //
    public String getNickname() {
        return nickname;
    }
    public String getPassphrase() {
        return passphrase;
    }
}
