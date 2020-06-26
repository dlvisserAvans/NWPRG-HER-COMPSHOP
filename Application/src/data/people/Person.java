package data.people;

public abstract class Person {

    private int PersonID;

    public Person() {
        this.PersonID = generatePersonID();
    }

    public int getPersonID() {
        return PersonID;
    }

    public void setPersonID(int personID) {
        PersonID = personID;
    }

    public int generatePersonID(){
        return (int) (Math.random() * 5000);
    }
}
