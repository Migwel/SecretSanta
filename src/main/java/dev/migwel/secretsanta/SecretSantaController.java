package dev.migwel.secretsanta;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class SecretSantaController {

    private final SecretSantaService secretSantaService;

    public SecretSantaController(SecretSantaService secretSantaService) {
        this.secretSantaService = secretSantaService;
    }

    @RequestMapping(method= RequestMethod.GET)
    public SecretSantaResponse getSecretSanta(SecretSantaRequest request) {
        var hamiltonCycle = secretSantaService.computeHamiltonCycle(request.getParticipants());
        List<SecretSanta> secretSantas = buildSecretSantas(hamiltonCycle);
        return new SecretSantaResponse(secretSantas);
    }

    private List<SecretSanta> buildSecretSantas(List<Participant> hamiltonCycle) {
        List<SecretSanta> secretSantas = new ArrayList<>();
        Participant previousParticipant = hamiltonCycle.get(hamiltonCycle.size() - 1);
        for (Participant participant : hamiltonCycle) {
            secretSantas.add(new SecretSanta(previousParticipant, participant));
            previousParticipant = participant;
        }
        return secretSantas;
    }
}
