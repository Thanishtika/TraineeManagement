package Model;

public class Trainee {
    private int id;
    private String name, email, department;
    private double stipend;

    public Trainee(int id, String name, String email, String department, double stipend) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.stipend = stipend;
    }

    public Trainee() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getStipend() {
        return stipend;
    }

    public void setStipend(double stipend) {
        this.stipend = stipend;
    }
}
