package dev.migwel.secretsanta;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SecretSantaService {

    public enum Status {
        Possible,
        Impossible,
        ;
    }

    public List<Participant> computeHamiltonCycle(List<Participant> participants) {
        Map<Participant, Map<Participant, Status>> giftsTable = buildGiftsTable(participants);
        ArrayList<Participant> hamiltonCycle = new ArrayList<>();
        hamiltonCycle.add(participants.get(0));
        if (!computeHamiltonCycle(giftsTable, hamiltonCycle, participants.get(0))) {
            throw new IllegalArgumentException("Could not compute a proper secret santa based on provided participants");
        }

        return hamiltonCycle;
    }

    private boolean computeHamiltonCycle(Map<Participant, Map<Participant, Status>> giftsTable, List<Participant> hamiltonCycle, Participant givingParticipant) {
        if(hamiltonCycle.size() == giftsTable.keySet().size() && giftsTable.get(givingParticipant).get(hamiltonCycle.get(0)) == Status.Possible) {
            //We are done
            return true;
        }

        List<Participant> possibleReceivingParticipants = getPossibleReceivingParticipants(giftsTable.get(givingParticipant), hamiltonCycle);
        while(possibleReceivingParticipants.size() > 0) {
            int receivingParticipantIdx = getRandomNumberInRange(0, possibleReceivingParticipants.size() - 1);
            Participant receivingParticipant = possibleReceivingParticipants.get(receivingParticipantIdx);
            hamiltonCycle.add(receivingParticipant);
            if (computeHamiltonCycle(giftsTable, hamiltonCycle, receivingParticipant)) {
                return true;
            }

            hamiltonCycle.remove(receivingParticipant);
            possibleReceivingParticipants.remove(receivingParticipantIdx);
        }

        return false;
    }

    private Map<Participant, Map<Participant,SecretSantaService.Status>> copyGiftTable(Map<Participant, Map<Participant,SecretSantaService.Status>> giftsTable) {
        Map<Participant, Map<Participant,SecretSantaService.Status>> giftsTableCopy = new HashMap<>();
        for (Map.Entry<Participant, Map<Participant,SecretSantaService.Status>> giftsTableEntry : giftsTable.entrySet()) {
            Map<Participant,SecretSantaService.Status> receivingMap = giftsTableEntry.getValue();
            Map<Participant,SecretSantaService.Status> receivingMapCopy = new HashMap<>();
            for (Map.Entry<Participant,SecretSantaService.Status> receivingEntry : receivingMap.entrySet()) {
                receivingMapCopy.put(receivingEntry.getKey(), receivingEntry.getValue());
            }

            giftsTableCopy.put(giftsTableEntry.getKey(), receivingMapCopy);
        }
        return giftsTableCopy;
    }

    private Map<Participant, Map<Participant, Status>> buildGiftsTable(List<Participant> participants) {
        Map<Participant, Map<Participant, Status>> giftsTable = new HashMap<>();
        for(Participant givingParticipant : participants) {
            Map<Participant, Status> statusMap = new HashMap<>();
            for(Participant receivingParticipant : participants) {
                if (givingParticipant.getExclusionList() != null && givingParticipant.getExclusionList().contains(receivingParticipant.getName())) {
                    statusMap.put(receivingParticipant, Status.Impossible);
                } else {
                    statusMap.put(receivingParticipant, Status.Possible);
                }
            }
            giftsTable.put(givingParticipant, statusMap);
        }
        return giftsTable;
    }

    private List<Participant> getPossibleReceivingParticipants(Map<Participant,SecretSantaService.Status> receivingParticipants, List<Participant> hamiltonCycle) {
        return receivingParticipants.entrySet()
                .stream()
                .filter(e -> e.getValue() !=SecretSantaService.Status.Impossible && !hamiltonCycle.contains(e.getKey()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private void displayGiftsTable(Map<Participant, Map<Participant,SecretSantaService.Status>> giftsTable, String comment) {
        System.out.println("                          *----- SHOWING GIFTS TABLE ("+ comment +")-----*");
        for (Map.Entry<Participant, Map<Participant,SecretSantaService.Status>> giftsTableEntry : giftsTable.entrySet()) {
            String givingParticipant = giftsTableEntry.getKey().getName();
            System.out.print(givingParticipant + ": ");
            Map<Participant,SecretSantaService.Status> receivingMap = giftsTableEntry.getValue();
            for (Map.Entry<Participant,SecretSantaService.Status> receivingEntry : receivingMap.entrySet()) {
                String receivingParticipant = receivingEntry.getKey().getName();
                SecretSantaService.Status receivingStatus = receivingEntry.getValue();
                System.out.print(receivingParticipant + "(" + receivingStatus.name() + ") ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();

    }

    private int getRandomNumberInRange(int min, int max) {

        if(min == max) {
            return min;
        }

        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
