package org.halimatussyadiyah.pmb.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.halimatussyadiyah.pmb.enumeration.Gender;
import org.halimatussyadiyah.pmb.enumeration.StatusKawin;
import org.springframework.data.relational.core.sql.In;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_DEFAULT)
@Entity
@Table(
        name = "biodata",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_biodata_email",
                        columnNames = "email"
                )
        }
)
public class Biodata {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "nama")
    private String nama;
    private String tempatLahir;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date tanggalLahir;
    private Gender jk;
    private String agama;
    private String alamat;
    private String email;
    private StatusKawin statusKawin;
    private String hobi;
    private Integer anakKe;
    private Integer jmlSaudara;
    private Boolean statusVerifikasi = false;
    private Date createdAt;

    @OneToOne(mappedBy = "biodata",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Card card;
    @OneToOne(mappedBy = "biodata",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Dokumen dokumen;
}
