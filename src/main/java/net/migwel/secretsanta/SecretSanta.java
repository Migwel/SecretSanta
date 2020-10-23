package net.migwel.secretsanta;

public class SecretSanta {

    private final Participant secretSanta;
    private final Participant receiver;

    public SecretSanta(Participant secretSanta, Participant receiver) {
        this.secretSanta = secretSanta;
        this.receiver = receiver;
    }

    public Participant getSecretSanta() {
        return secretSanta;
    }

    public Participant getReceiver() {
        return receiver;
    }

    @Override
    public String toString() {
        return "SecretSanta{" +
                "secretSanta=" + secretSanta +
                ", receiver=" + receiver +
                '}';
    }
}
