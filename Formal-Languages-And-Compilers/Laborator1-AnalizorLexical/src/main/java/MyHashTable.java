import Domain.Atom;

public class MyHashTable {
    private int size;
    private Atom[] hashTable;

    public MyHashTable(int size) {
        this.size = size;
        this.hashTable = new Atom[size];
    }

    /**
     * add an element to hash table
     * @param value : value to add in the hash table
     */
    public void add(String value) {
        int probe = 0;
        boolean found = false;
        int hash = hash(value);
        while (probe < size && !found) {
            if (hashTable[hash] == null) {
                hashTable[hash] = new Atom(value, hash);
                found = true;
            }
            probe++;
            if (hash == size - 1) {
                hash = 0;
            } else {
                hash++;
            }
        }
    }

    /**
     * @param value : value to find in the hash table
     * @return true if the hash table contains value, false otherwise
     */
    public boolean contains(String value) {
        int hash = hash(value);
        boolean found = false;
        int hashInit = hash;
        boolean isNull = false;
        if (hashTable[hash] == null)
            return false;
        do {
            if (hashTable[hash] == null)
                isNull = true;
            else {
                if (hashTable[hash].getAtom().equals(value)) {
                    found = true;
                } else {
                    if (hash == size - 1) {
                        hash = 0;
                    } else {
                        hash++;
                    }
                }
            }
        } while (!found && (hash != hashInit) && !isNull);
        if (isNull)
            return false;
        return found;

    }

    /**
     * @param value : value to find in the hash table
     * @return the index from hash table where the value is stored
     */
    public int getIndexOf(String value) {
        int hash = hash(value);
        boolean found = false;
        while (!found) {
            if (hashTable[hash].getAtom().equals(value)) {
                found = true;
            } else {
                if (hash == size - 1) {
                    hash = 0;
                } else {
                    hash++;
                }
            }
        }
        return hash;
    }

    /**
     * @return value of the hash function : h(value) = value.hashcode() % size
     */
    private int hash(String value) {
        return value.hashCode() % size;
    }

    public String toString() {
        String res = "";
        for (int i = 0; i < size; i++) {
            if (hashTable[i] != null) {
                res += hashTable[i].getAtom() + " " + hashTable[i].getCode() + "\n";
            }
        }
        return res;
    }
}
