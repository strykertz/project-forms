package br.com.dataprev.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FormAipEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sigla;
    private String nomeProjeto;
    private boolean trataDadoPessoal;
    private boolean dadoSensivel;
    private String contextoTratamentoDadoPessoal;
    private boolean tratamentoLargaEscala;
    private Integer volumeRegistroTitular;
    private boolean monitoramentoVideo;
    private String criticidade;
    private String analise;

}
