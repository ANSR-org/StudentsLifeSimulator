package bg.ansr.simulator.studentsimulatorcore.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Item {

    private Long id;
    private String name;
    private Double basePrice;
    private Set<StudentItem> studentItems;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    @OneToMany(mappedBy = "item", targetEntity = StudentItem.class)
    public Set<StudentItem> getStudentItems() {
        return studentItems;
    }

    public void setStudentItems(Set<StudentItem> studentItems) {
        this.studentItems = studentItems;
    }
}
