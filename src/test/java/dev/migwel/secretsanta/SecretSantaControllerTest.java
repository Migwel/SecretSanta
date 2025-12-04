package dev.migwel.secretsanta;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SecretSantaControllerTest {

    private final SecretSantaController controller = new SecretSantaController(new SecretSantaService(), new EmailSender());

    @Test
    void testGetValidSecretSanta() throws IOException {
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("validsecretsantarequest.json");
        SecretSantaRequest request = new ObjectMapper().readValue(inStream, SecretSantaRequest.class);
        SecretSantaResponse response = controller.getSecretSanta(request);
        List<SecretSanta> secretSantas = response.getSecretSantas();
        assertEquals(request.getParticipants().size(), secretSantas.size());

        Set<Participant> givingParticipant = new HashSet<>();
        Set<Participant> receivingParticipant = new HashSet<>();
        Participant previousReceiver = secretSantas.get(secretSantas.size() - 1).getReceiver();
        for(SecretSanta secretSanta : secretSantas) {
            assertEquals(previousReceiver, secretSanta.getSecretSanta());
            assertFalse(givingParticipant.contains(secretSanta.getSecretSanta()));
            assertFalse(receivingParticipant.contains(secretSanta.getReceiver()));
            givingParticipant.add(secretSanta.getSecretSanta());
            receivingParticipant.add(secretSanta.getReceiver());
            previousReceiver = secretSanta.getReceiver();
        }
    }

    @Test
    void testGetInvalidSecretSanta() throws IOException {
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("invalidsecretsantarequest.json");
        SecretSantaRequest request = new ObjectMapper().readValue(inStream, SecretSantaRequest.class);
        assertThrows(IllegalArgumentException.class, () -> controller.getSecretSanta(request));
    }

}