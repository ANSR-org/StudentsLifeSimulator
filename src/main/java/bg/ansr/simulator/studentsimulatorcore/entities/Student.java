package bg.ansr.simulator.studentsimulatorcore.entities;


import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students", uniqueConstraints = {@UniqueConstraint(columnNames = {"email","username"})})
public class Student {

    private Long id;
    private String username;
    private String password;
    private String email;
    private University university;
    private Specialty specialty;
    private Hostel hostel;
    private Long energy;
    private Long money;
    private Long popularity;
    private Long incomePerHour;
    private LocalDateTime lastGivenIncome;
    private Lecture currentLecture;
    private Set<Schedule> schedules;
    private Set<Payment> payments;
    private Set<BlockingEvent> blockingEvents;
    private Set<StudentItem> items;
    private Set<Trade> trades;
    private Long points;

    public Student() {
        this.schedules = new HashSet<>();
        this.payments = new HashSet<>();
        this.blockingEvents = new HashSet<>();
        this.items = new HashSet<>();
        this.trades = new HashSet<>();
        this.points = 0L;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne
    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    @ManyToOne
    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    @ManyToOne
    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public Long getEnergy() {
        return energy;
    }

    public void setEnergy(Long energy) {
        this.energy = energy;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getPopularity() {
        return popularity;
    }

    public void setPopularity(Long popularity) {
        this.popularity = popularity;
    }

    public Long getIncomePerHour() {
        return this.incomePerHour;
    }

    public void setIncomePerHour(Long incomePerHour) {
        this.incomePerHour = incomePerHour;
    }

    @ManyToOne
    public Lecture getCurrentLecture() {
        return this.currentLecture;
    }

    public void setCurrentLecture(Lecture currentLecture) {
        this.currentLecture = currentLecture;
    }

    @OneToMany(mappedBy = "subscribedStudent", targetEntity = Schedule.class)
    public Set<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    @OneToMany(targetEntity = Payment.class, mappedBy = "student")
    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student", targetEntity = BlockingEvent.class)
    public Set<BlockingEvent> getBlockingEvents() {
        return blockingEvents;
    }

    public void setBlockingEvents(Set<BlockingEvent> blockingEvents) {
        this.blockingEvents = blockingEvents;
    }

    @OneToMany(mappedBy = "student", targetEntity = StudentItem.class)
    public Set<StudentItem> getItems() {
        return items;
    }

    public void setItems(Set<StudentItem> studentItems) {
        this.items = studentItems;
    }

    @OneToMany(mappedBy = "student", targetEntity = Trade.class)
    public Set<Trade> getTrades() {
        return trades;
    }

    public void setTrades(Set<Trade> trades) {
        this.trades = trades;
    }

    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    public LocalDateTime getLastGivenIncome() {
        return this.lastGivenIncome;
    }

    public void setLastGivenIncome(LocalDateTime lastGivenIncome) {
        this.lastGivenIncome = lastGivenIncome;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }
}
