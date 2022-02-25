package controller.GestStaff;

public class Gestor {
    int idGestor;
    String nome;
    int passwordGestor;

    public Gestor(int idGestor, String nome, String password) {
        this.idGestor = idGestor;
        this.nome = nome;
        this.passwordGestor = hashPass(password);
    }

    public boolean logIn(String password) {
        return this.passwordGestor == hashPass(password);
    }

    public int hashPass(String password) {
        int hash = 13;
        for (int i = 0; i < password.length(); i++) {
            hash = hash * 17 + password.charAt(i);
        }
        return hash;
    }
}
