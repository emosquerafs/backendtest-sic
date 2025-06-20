package co.gov.sic.testsic.infrastructura.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "document_type")
public class DocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_type_id_gen")
    @SequenceGenerator(name = "document_type_id_gen", sequenceName = "document_type_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code", nullable = false, length = 5)
    private String code;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

}