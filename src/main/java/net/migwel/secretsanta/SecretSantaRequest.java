package net.migwel.secretsanta;

import java.util.List;

public class SecretSantaRequest {

    private List<Participant> participants;

    private boolean sendEmail;

    public SecretSantaRequest() {
    }

    public SecretSantaRequest(List<Participant> participants, boolean sendEmail) {
        this.participants = participants;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }
}
