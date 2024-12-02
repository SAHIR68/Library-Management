package user.dto;

import java.util.Date;

public class UserDTO {
    private static Integer numberOfUsers = 0;

    private Integer id;
    private String name;
    private Date dateOfBirth;
    private String occupation;
    private Integer age;
    private String ssn;


    {
        numberOfUsers++;
    }

    public UserDTO() {
    }

    public UserDTO(String name, Date dateOfBirth, String occupation, String ssn) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.occupation = occupation;
        this.ssn = ssn;
    }

    public static Integer getNumberOfUsers() {
        return numberOfUsers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", occupation='" + occupation + '\'' +
                ", age=" + age +
                ", ssn='" + ssn + '\'' +
                '}';
    }
}
