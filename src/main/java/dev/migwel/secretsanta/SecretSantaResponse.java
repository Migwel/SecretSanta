package dev.migwel.secretsanta;

import java.util.List;

public class SecretSantaResponse {
    private final List<SecretSanta> secretSantas;

    public SecretSantaResponse(List<SecretSanta> secretSantas) {
        this.secretSantas = secretSantas;
    }

    public List<SecretSanta> getSecretSantas() {
        return secretSantas;
    }
}
