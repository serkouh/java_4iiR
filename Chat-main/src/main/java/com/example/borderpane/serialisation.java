package com.example.borderpane;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Serialisation {

    private File fichier;

    public Serialisation(File fichier) {
        this.fichier = fichier;
    }

    public void write(Manager m) throws IOException, ClassNotFoundException {
        Manager mj;
        ObjectInputStream entree = null;
        ObjectOutputStream sortie = null;
        boolean flag = false;
        File temporaire = new File("temp.txt");
        sortie = new ObjectOutputStream(new FileOutputStream(temporaire));
        try {
            entree = new ObjectInputStream(new FileInputStream(fichier));
            mj = (Manager) entree.readObject();
            while (mj != null) {
                if (m.id.equals(mj.id)) {
                    sortie.writeObject(m);
                    flag = true;
                } else {
                    sortie.writeObject(mj);
                }
                mj = (Manager) entree.readObject();
            }
        } catch (FileNotFoundException e) {
        } catch (EOFException e) {
            entree.close();
        } catch (ClassNotFoundException e) {
        }
        if (!flag) {
            sortie.writeObject(m);
        }
        sortie.close();
        fichier.delete();
        temporaire.renameTo(fichier);
    }

    public Set<Manager> read() throws IOException {
        Manager mj;
        Set<Manager> s = new HashSet<Manager>();
        ObjectInputStream entree = new ObjectInputStream(new FileInputStream(fichier));
        try {
            mj = (Manager) entree.readObject();
            while (mj != null) {
                s.add(mj);
                mj = (Manager) entree.readObject();
            }
        } catch (EOFException e) {
            entree.close();
        } catch (ClassNotFoundException e) {
        }
        return s;
    }
}
