class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Horse pippi = new Horse('a', "Pippi Longstocking", 0.5);
        Horse joel = new Horse('b', "Joel Longstocking", 0.7);
        Horse x = new Horse('c', "Moon", 0.2);
        Race r = new Race(100, pippi, joel, x);
        r.startRace();
    }
}