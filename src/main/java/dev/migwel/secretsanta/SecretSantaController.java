package dev.migwel.secretsanta;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class SecretSantaController {

    private final SecretSantaService secretSantaService;
    private final EmailSender emailSender;

    public SecretSantaController(SecretSantaService secretSantaService, EmailSender emailSender) {
        this.secretSantaService = secretSantaService;
        this.emailSender = emailSender;
    }

    @RequestMapping(method= RequestMethod.POST)
    @ResponseBody
    public SecretSantaResponse getSecretSanta(@RequestBody SecretSantaRequest request) {
        var hamiltonCycle = secretSantaService.computeHamiltonCycle(request.getParticipants());
        List<SecretSanta> secretSantas = buildSecretSantas(hamiltonCycle);
        if (request.isSendEmail()) {
            for (var secretSanta : secretSantas) {
                emailSender.send(secretSanta.getSecretSanta().getEmail(), secretSanta);
            }
        }
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
