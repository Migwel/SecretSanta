package dev.migwel.secretsanta;

import java.util.List;
import java.util.Objects;

public class Participant {

    private String name;
    private String email;
    private List<String> exclusionList;

    public Participant() {}

    public Participant(String name, String email, List<String> exclusionList) {
        this.name = name;
        this.email = email;
        this.exclusionList = exclusionList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getExclusionList() {
        return exclusionList;
    }

    public void setExclusionList(List<String> exclusionList) {
        this.exclusionList = exclusionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Participant{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
