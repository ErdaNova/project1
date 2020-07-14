package com.example.myapplication.ui;

public class Person {
    private String Name;
    private String Number;
    private Long Photo_id;
    private Long Person_id;

    public Person() {}
    public Person(String _Name, String _Number, Long _Photo_id, Long _Person_id)
    {
        this.Name = _Name;
        this.Number = _Number;
        this.Photo_id = _Photo_id;
        this.Person_id = _Person_id;
    }

    public Person(String _Name, String _Number)
    {
        this.Name = _Name;
        this.Number = _Number;
    }

    public Person(Person person)
    {
        this.Name = person.Name;
        this.Number = person.Number;
        this.Photo_id = person.Photo_id;
        this.Person_id = person.Person_id;
    }

    public Long getPhoto_id() { return Photo_id; }

    public void setPhoto_id(Long photo_id) { this.Photo_id = photo_id; }

    public Long getPerson_id() { return Person_id; }

    public void setPerson_id(Long person_id) { this.Person_id = person_id; }

    public String getName()
    {
        return Name;
    }

    public void setName(String name) { this.Name = name; }

    public String getNumber()
    {
        return Number;
    }

    public void setNumber(String number) { this.Number = number; }
}
