package org.halimatussyadiyah.pmb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_DEFAULT)
@Entity
public class Dokumen {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String kartuKeluarga;
    private String akte;
    private String ijazah;
    private String pasFoto;
    private Boolean statusVerifikasi;
    @OneToOne
    @JoinColumn(name = "id_biodata", nullable = false)
    @JsonIgnore
    private Biodata biodata;
    }
