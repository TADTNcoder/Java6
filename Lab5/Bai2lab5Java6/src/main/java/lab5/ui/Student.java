package lab5.ui;

public class Student {
    private String id;
    private String name;
    private double mark;
    private boolean gender;

    public Student() {}

    public Student(String id, String name, double mark, boolean gender) {
        this.id = id;
        this.name = name;
        this.mark = mark;
        this.gender = gender;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getMark() { return mark; }
    public void setMark(double mark) { this.mark = mark; }

    public boolean isGender() { return gender; }
    public void setGender(boolean gender) { this.gender = gender; }
}