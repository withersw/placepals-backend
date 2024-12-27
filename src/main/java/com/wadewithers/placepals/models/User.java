package com.wadewithers.placepals.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

enum NoiseTolerance {
    QUIET, // Prefers silence
    MODERATE, // Tolerates normal household noise
    LOUD // Enjoys a noisy/lively environment
}

enum Cleanliness {
    MESSY, AVERAGE, NEAT
}

enum RoomListingType {
    LOOKING_FOR_ROOM, // The user is searching for a room
    RENTING_OUT_ROOM // The user has a room to rent
}

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "users")
//@Data
public class User {
    @Id
    @Column(name = "id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "age")
    private int age;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;

    @Column(name = "budget")
    private float budget;
    @Enumerated(EnumType.STRING)
    @Column(name = "noise_tolerance")
    private NoiseTolerance noise_tolerance; // Enum for noise tolerance levels: QUIET, MODERATE, LOUD
    @Column(name = "pet_friendly")
    private Boolean pet_friendly; // User is ok living with pets
    @Column(name = "outgoing")
    private Boolean outgoing; // Is user out going
    @Enumerated(EnumType.STRING)
    @Column(name = "cleanliness_level")
    private Cleanliness cleanliness_level; // Enum for cleanliness levels: MESSY, AVERAGE, NEAT
    @Column(name = "bio")
    private String bio;
    @Column(name = "profile_picture_url")
    private String profile_picture_url;
    @Enumerated(EnumType.STRING)
    @Column(name = "room_listing_type")
    private RoomListingType room_listing_type; // Enum for room preferences: LOOKING_FOR_ROOM, RENTING_OUT_ROOM
//    @ElementCollection
//    private List<String> interests; // List of hobbies/interests

    // Override toString to print meaningful user data
    @Override
    public String toString() {
        return "User{id='" + id + "', first_name='" + first_name + "', lastName='" + last_name +
                "', city='" + city + "', age=" + age +
                ", bio='" + bio + "', budget=" + budget + ", cleanlinessLevel='" + cleanliness_level +
                "', noiseTolerance='" + noise_tolerance + "', petFriendly=" + pet_friendly +
                ", outgoing=" + outgoing + ", profilePictureUrl='" + profile_picture_url +
                "', roomListingType='" + room_listing_type + "', state='" + state + "'}";
    }

    // GETTERS
    public long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.first_name;
    }

    public String getLastName() {
        return this.last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return this.age;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public float getBudget() {
        return this.budget;
    }

    public NoiseTolerance getNoiseTolerance() {
        return this.noise_tolerance;
    }

    public Boolean getPetFriendly() {
        return this.pet_friendly;
    }

    public Boolean getOutgoing() {
        return this.outgoing;
    }

    public Cleanliness getCleanlinessLevel() {
        return this.cleanliness_level;
    }

    public String getBio() {
        return this.bio;
    }

    public RoomListingType getRoomListingType() {
        return this.room_listing_type;
    }

    public String getProfilePictureUrl() {
        return this.profile_picture_url;
    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
    // SETTERS
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public void setNoise_tolerance(NoiseTolerance noise_tolerance) {
        this.noise_tolerance = noise_tolerance;
    }

    public void setPet_friendly(Boolean pet_friendly) {
        this.pet_friendly = pet_friendly;
    }

    public void setOutgoing(Boolean outgoing) {
        this.outgoing = outgoing;
    }

    public void setCleanliness_level(Cleanliness cleanliness_level) {
        this.cleanliness_level = cleanliness_level;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setProfilePictureUrl(String profile_picture_url) {
        this.profile_picture_url = profile_picture_url;
    }

    public void setRoom_listing_type(RoomListingType room_listing_type) {
        this.room_listing_type = room_listing_type;
    }
}
