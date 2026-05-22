package co.edu.unbosque.chistesneco.dto;

import java.util.Objects;

public class JokeApiDTO {

    private String type;
    private String joke;
    private String setup;
    private String delivery;
    private boolean error;

    public JokeApiDTO() {}

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getJoke() { return joke; }
    public void setJoke(String joke) { this.joke = joke; }
    public String getSetup() { return setup; }
    public void setSetup(String setup) { this.setup = setup; }
    public String getDelivery() { return delivery; }
    public void setDelivery(String delivery) { this.delivery = delivery; }
    public boolean isError() { return error; }
    public void setError(boolean error) { this.error = error; }

    @Override
    public String toString() {
        return "JokeApiDTO [type=" + type + ", joke=" + joke + ", setup=" + setup + ", delivery=" + delivery + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(delivery, error, joke, setup, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        JokeApiDTO other = (JokeApiDTO) obj;
        return Objects.equals(delivery, other.delivery)
                && error == other.error
                && Objects.equals(joke, other.joke)
                && Objects.equals(setup, other.setup)
                && Objects.equals(type, other.type);
    }
}