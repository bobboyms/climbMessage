package br.com.climb.message;

interface Message {}

interface Topic extends Message {}

interface Rpc extends Message {}

class Messsage implements Topic {

}

class Call implements Topic {

}

public class Main {
    public static void main(String[] args) throws Exception {
        ClimbApplication.run(Main.class);
    }
}
