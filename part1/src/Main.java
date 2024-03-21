class Main {
    public static void main(String[] args) {
        Horse zappyHorse = new Horse('a', "Zappy-Horse", 0.46);

        Horse dataHorse = new Horse('a',"Data-Horse", 0.5);

        Horse serverHorse = new Horse('a',"Server-Horse", 0.5);
        zappyHorse.setSymbol('♕');
        dataHorse.setSymbol('♞');
        serverHorse.setSymbol('♘');

        Race r = new Race(15);
        r.addHorse(zappyHorse, 1);
        r.addHorse(dataHorse, 2);
        r.addHorse(serverHorse, 3);

        r.startRace();
    }
}