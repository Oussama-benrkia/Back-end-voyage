package org.backend.voyage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.backend.voyage.model.Enum.Transport;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Voyage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Name;
    private String Description;
    @Enumerated(EnumType.STRING)
    private Transport transport;
    private Long nbr_per;
    @Temporal(TemporalType.DATE)
    private Date date_debut;
    @Temporal(TemporalType.DATE)
    private Date date_fin;
    private double prix;
    @ManyToOne(fetch = FetchType.EAGER)
    private Hebergement hebergement;
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "voyages_activity_MAPPING", joinColumns = @JoinColumn(name = "voyage_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"))
    private List<Activity> activity=null;
    private String image;
    @CreationTimestamp
    private LocalDateTime Create_at;
    private String ville;
    private boolean etat;
    @OneToMany(mappedBy = "voyage", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
}
